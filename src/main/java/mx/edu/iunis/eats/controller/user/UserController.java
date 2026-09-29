package mx.edu.iunis.eats.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller()
public class UserController {

    @GetMapping("/user")
    public String display(Model model) {
        model.addAttribute("appName", "IUNIS Eats");
        return "users/display";
    }
}
