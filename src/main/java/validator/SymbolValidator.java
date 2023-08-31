package validator;

import dto.SymbolDTO;
import manager.SymbolManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SymbolValidator implements Validator {

    private SymbolManager symbolManager;

    @Override
    public boolean supports(Class<?> clazz) {
        return SymbolDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SymbolDTO symbolDTO = (SymbolDTO) target;

        if (symbolManager.findBySymbol(symbolDTO.getSymbol().charAt(0)) != null) {
            errors.rejectValue("symbol", "DUPLICATE", "Such the symbol already exists");
        }
    }

    @Autowired
    public void setSymbolManager(SymbolManager symbolManager) {
        this.symbolManager = symbolManager;
    }
}
