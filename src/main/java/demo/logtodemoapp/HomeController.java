package demo.logtodemoapp;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {
    @GetMapping({"/", "/home"})
    public String home(Principal principal) {
        return principal != null ? "redirect:/user" : "home";
    }
}