package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HubController {

    @GetMapping("/")
    public String greetingMessage() {
        return "hello";
    }
}
