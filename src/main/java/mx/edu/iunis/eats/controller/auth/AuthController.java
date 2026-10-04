package mx.edu.iunis.eats.controller.auth;

import mx.edu.iunis.eats.domain.User;
import mx.edu.iunis.eats.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showlogin(Model model) {
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user,
                           Model model,
                           RedirectAttributes redirectAttributes) {

        if (user.getUserName() == null || user.getUserName().isBlank()
                || user.getPassword() == null || user.getPassword().isBlank()) {

            model.addAttribute("formError",
                    "Completa el nombre de usuario y la contraseña.");
            return "auth/register";
        }
        if (userService.userNameExists(user.getUserName())) {
            model.addAttribute("formError",
                    "El nombre de usuario ya existe");
            return "auth/register";
        }

        user.setRole(1); // STUDENT
        userService.createUser(user);

        redirectAttributes.addFlashAttribute(
                "notice", "Cuenta creada correctamente. Ya puedes iniciar sesión.");

        return "redirect:/login";
    }
}
