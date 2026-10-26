package mx.edu.iunis.eats.controller;

import mx.edu.iunis.eats.domain.Pedido;
import mx.edu.iunis.eats.repository.PedidoRepository;
import mx.edu.iunis.eats.service.PlatilloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MenuController {

    @Autowired
    private PlatilloService platilloService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping("/menu")
    public String showMenu(Model model) {
        model.addAttribute("appName", "IUNIS Eats");
        model.addAttribute("platillos", platilloService.obtenerTodos());
        return "estudiante/menu";
    }

    @GetMapping("/mis-pedidos")
    public String misPedidos(Model model, Authentication authentication) {
        // 1. Obtener el número de control del estudiante logueado
        String username = authentication.getName();
        Long idEstudiante = 0L;
        try {
            idEstudiante = Long.parseLong(username);
        } catch (NumberFormatException e) {
            // En caso de que el usuario no sea un número, se queda en 0
        }

        Long finalIdEstudiante = idEstudiante;

        // 2. Filtrar los pedidos para que solo aparezcan los de este estudiante
        List<Pedido> misPedidos = pedidoRepository.findAll().stream()
                .filter(p -> p.getIdUsuario() != null && p.getIdUsuario().equals(finalIdEstudiante))
                .toList();

        model.addAttribute("pageTitle", "Mis pedidos");
        model.addAttribute("pedidos", misPedidos);
        model.addAttribute("mostrarBanner", false);

        return "estudiante/mis-pedidos";
    }
}