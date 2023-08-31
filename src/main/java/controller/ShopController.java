package controller;

import bean.*;
import manager.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import util.Constants;
import util.ShopUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ShopController {

    private UserManager userManager;
    private UserLayoutPatternMapManager userLayoutPatternMapManager;
    private UserSymbolMapManager userSymbolMapManager;
    private SymbolManager symbolManager;
    private LayoutPatternManager layoutPatternManager;
    private UserOrderManager userOrderManager;
    private OrderDetailManager orderDetailManager;
    private OrderStatusManager orderStatusManager;

    @GetMapping("/shop")
    public String shop(Authentication authentication, Model model) {
        User user = userManager.findByLogin(authentication.getName());
        List<LayoutPattern> userLayoutPatterns = userLayoutPatternMapManager.findUserLayoutPatterns(user);
        List<Symbol> userSymbols = userSymbolMapManager.findUserSymbols(user);

        List<Symbol> allSymbols = symbolManager.findAll();
        List<LayoutPattern> allLayoutPatterns = layoutPatternManager.findAll();

        List<Symbol> shopSymbols = new ArrayList<>();
        List<LayoutPattern> shopLayoutPatterns = new ArrayList<>();

        for (Symbol symbol : allSymbols) {
            if (!userSymbols.contains(symbol)) {
                shopSymbols.add(symbol);
            }
        }

        for (LayoutPattern layoutPattern : allLayoutPatterns) {
            if (!userLayoutPatterns.contains(layoutPattern)) {
                shopLayoutPatterns.add(layoutPattern);
            }
        }

        model.addAttribute("user", user);
        model.addAttribute("shopSymbols", shopSymbols);
        model.addAttribute("shopLayoutPatterns", shopLayoutPatterns);
        model.addAttribute("userSymbols", userSymbols);
        model.addAttribute("userLayoutPatterns", userLayoutPatterns);

        return "shop";
    }

    @GetMapping("/cart")
    public String cart(Model model, Authentication authentication) {
        User user = userManager.findByLogin(authentication.getName());
        UserOrder activeUserOrder = userOrderManager.findActiveUserOrder(user.getId());
        List<OrderDetail> orderDetails;
        List<Symbol> orderSymbols;
        List<LayoutPattern> orderLayoutPatterns;
        int totalSum;

        if (activeUserOrder == null) {
            model.addAttribute("cartExists", false);
            return "cart";
        }

        orderDetails = orderDetailManager.findByUserOrder(activeUserOrder.getId());
        orderSymbols = symbolManager.findByOrderDetails(orderDetails);
        orderLayoutPatterns = layoutPatternManager.findByOrderDetails(orderDetails);
        totalSum = ShopUtil.countTotalSum(orderSymbols, orderLayoutPatterns);

        model.addAttribute("user", user);
        model.addAttribute("orderSymbols", orderSymbols);
        model.addAttribute("orderLayoutPatterns", orderLayoutPatterns);
        model.addAttribute("totalSum", totalSum);
        model.addAttribute("cartExists", true);

        return "cart";
    }

    @PostMapping("/shop/add-symbol-to-cart")
    public String addSymbolToCart(@RequestParam("symbolId") int symbolId, Authentication authentication) {
        Symbol symbol = symbolManager.findSymbol(symbolId);

        addItemToCart(symbol, Constants.OrderDetailTables.SYMBOL, authentication);

        return "redirect:/shop";
    }

    @PostMapping("/shop/add-layout-pattern-to-cart")
    public String addLayoutPatternToCart(@RequestParam("layoutPatternId") int layoutPatternId, Authentication authentication) {
        LayoutPattern layoutPattern = layoutPatternManager.find(layoutPatternId);

        addItemToCart(layoutPattern, Constants.OrderDetailTables.LAYOUT_PATTERN, authentication);

        return "redirect:/shop";
    }

    @PostMapping("/cart/delete-symbol")
    public String cartDeleteSymbol(@RequestParam("symbolId") int symbolId, Authentication authentication) {

        deleteItemFromCart(symbolId, Constants.OrderDetailTables.SYMBOL, authentication);

        return "redirect:/cart";
    }

    @PostMapping("/cart/delete-layout-pattern")
    public String cartDeleteLayoutPattern(@RequestParam("layoutPatternId") int layoutPatternId, Authentication authentication) {

        deleteItemFromCart(layoutPatternId, Constants.OrderDetailTables.LAYOUT_PATTERN, authentication);

        return "redirect:/cart";
    }

    @PostMapping("/cart/buy")
    public String cartBuy(@RequestParam("totalSum") int totalSum, Authentication authentication, RedirectAttributes redirectAttributes) {
        User user = userManager.findByLogin(authentication.getName());
        UserOrder activeUserOrder;
        List<OrderDetail> orderDetails;
        List<Symbol> symbols;
        List<LayoutPattern> layoutPatterns;

        if (totalSum > user.getCoin()) {
            redirectAttributes.addFlashAttribute("lackOfCoinError", "You do not have enough money");
            return "redirect:/cart";
        }

        activeUserOrder = userOrderManager.findActiveUserOrder(user.getId());
        orderDetails = orderDetailManager.findByUserOrder(activeUserOrder.getId());
        symbols = symbolManager.findByOrderDetails(orderDetails);
        layoutPatterns = layoutPatternManager.findByOrderDetails(orderDetails);

        userSymbolMapManager.create(user, symbols).forEach(map -> userSymbolMapManager.save(map));
        userLayoutPatternMapManager.create(user, layoutPatterns).forEach(map -> userLayoutPatternMapManager.save(map));

        user.setCoin(user.getCoin() - totalSum);
        userManager.update(user);
        activeUserOrder.setOrderStatus(orderStatusManager.findByStatus("FINISHED"));
        activeUserOrder.setModifiedDate(LocalDate.now());
        userOrderManager.updateUserOrder(activeUserOrder);

        redirectAttributes.addFlashAttribute("succeed", true);

        return "redirect:/cart";
    }

    private void deleteItemFromCart(int itemId, int tableId, Authentication authentication) {
        User user = userManager.findByLogin(authentication.getName());
        UserOrder activeUserOrder = userOrderManager.findActiveUserOrder(user.getId());

        OrderDetail requiredOrderDetail = orderDetailManager.find(tableId, itemId, activeUserOrder.getId());

        orderDetailManager.delete(requiredOrderDetail);
    }

    private void addItemToCart(Item item, int tableId, Authentication authentication) {
        User user = userManager.findByLogin(authentication.getName());
        UserOrder activeUserOrder = userOrderManager.findActiveUserOrder(user.getId());
        OrderDetail orderDetail;

        if (activeUserOrder == null) {
            activeUserOrder = userOrderManager.create(orderStatusManager.findByStatus("ACTIVE"), user); // the name of active status in order_status table
            userOrderManager.saveUserOrder(activeUserOrder);
        }

        orderDetail = orderDetailManager.find(tableId, item.getId(), activeUserOrder.getId());
        if (orderDetail == null) {
            orderDetail = orderDetailManager.create(tableId, item, activeUserOrder);

            orderDetailManager.save(orderDetail);
        }
    }

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    @Autowired
    public void setUserSymbolMapManager(UserSymbolMapManager userSymbolMapManager) {
        this.userSymbolMapManager = userSymbolMapManager;
    }

    @Autowired
    public void setUserLayoutPatternMapManager(UserLayoutPatternMapManager userLayoutPatternMapManager) {
        this.userLayoutPatternMapManager = userLayoutPatternMapManager;
    }

    @Autowired
    public void setLayoutPatternManager(LayoutPatternManager layoutPatternManager) {
        this.layoutPatternManager = layoutPatternManager;
    }

    @Autowired
    public void setSymbolManager(SymbolManager symbolManager) {
        this.symbolManager = symbolManager;
    }

    @Autowired
    public void setUserOrderManager(UserOrderManager userOrderManager) {
        this.userOrderManager = userOrderManager;
    }

    @Autowired
    public void setOrderDetailManager(OrderDetailManager orderDetailManager) {
        this.orderDetailManager = orderDetailManager;
    }

    @Autowired
    public void setOrderStatusManager(OrderStatusManager orderStatusManager) {
        this.orderStatusManager = orderStatusManager;
    }
}