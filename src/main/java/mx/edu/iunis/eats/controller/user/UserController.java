package mx.edu.iunis.eats.controller.user;

import mx.edu.iunis.eats.domain.User;
import mx.edu.iunis.eats.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping({"/users", "/user"})
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("appName", "IUNIS Eats");
        model.addAttribute("users", userService.listUsers());
        return "users/display";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("appName", "IUNIS Eats");
        model.addAttribute("user", new User());
        model.addAttribute("editing", false);
        return "users/form";
    }

    @PostMapping
    public String create(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes) {
        if (!isValid(user) || user.getPassword() == null || user.getPassword().isBlank()) {
            model.addAttribute("appName", "IUNIS Eats");
            model.addAttribute("editing", false);
            model.addAttribute("formError", "Completa el nombre de usuario, la contraseña y el rol.");
            return "users/form";
        }

        userService.createUser(user);
        redirectAttributes.addFlashAttribute("notice", "Usuario creado correctamente.");
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        User user = userService.findUserById(id).orElse(null);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "No se encontró el usuario solicitado.");
            return "redirect:/users";
        }

        // Nunca enviar la contraseña existente al navegador.
        user.setPassword(null);
        model.addAttribute("appName", "IUNIS Eats");
        model.addAttribute("user", user);
        model.addAttribute("editing", true);
        return "users/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute User user, Model model,
                         RedirectAttributes redirectAttributes) {
        if (!isValid(user) || userService.findUserById(id).isEmpty()) {
            model.addAttribute("appName", "IUNIS Eats");
            model.addAttribute("editing", true);
            model.addAttribute("formError", "Completa el nombre de usuario y el rol.");
            user.setId(id);
            return "users/form";
        }

        try {
            userService.updateUser(id, user);
        } catch (IllegalArgumentException exception) {
            redirectAttributes.addFlashAttribute("error", "No se encontró el usuario solicitado.");
            return "redirect:/users";
        }
        redirectAttributes.addFlashAttribute("notice", "Usuario actualizado correctamente.");
        return "redirect:/users";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (userService.findUserById(id).isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "No se encontró el usuario solicitado.");
            return "redirect:/users";
        }
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("notice", "Usuario eliminado correctamente.");
        return "redirect:/users";
    }

    private boolean isValid(User user) {
        return user.getUserName() != null && !user.getUserName().isBlank()
                && user.getRole() != null;
    }
}
