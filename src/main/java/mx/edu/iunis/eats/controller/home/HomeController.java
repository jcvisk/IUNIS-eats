package mx.edu.iunis.eats.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String showHome(Model model, Authentication authentication) {
        if (authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_CAFETERIA_STAFF"))) {
            return "redirect:/admin";
        }

        model.addAttribute("appName", "IUNIS Eats");
        return "home";
    }
}
