package mx.edu.iunis.eats.controller;

import mx.edu.iunis.eats.domain.DetallePedido;
import mx.edu.iunis.eats.domain.Pedido;
import mx.edu.iunis.eats.domain.Platillo;
import mx.edu.iunis.eats.repository.DetallePedidoRepository;
import mx.edu.iunis.eats.repository.PedidoRepository;
import mx.edu.iunis.eats.service.PlatilloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/pedidos")
public class AdminPedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private PlatilloService platilloService;

    @GetMapping
    public String listarPedidos(@RequestParam(name = "idPedido", required = false) Long idPedido, Model model) {
        model.addAttribute("pageTitle", "Pedidos");
        model.addAttribute("breadcrumb", "Pedidos / Recepción de comandas");
        model.addAttribute("pantallaActual", "pedidos");

        List<Pedido> pedidos = pedidoRepository.findAllByOrderByFechaHoraDesc();
        model.addAttribute("pedidos", pedidos);

        // Si se envió un ID, buscamos ese pedido. Si no, cargamos el más reciente.
        Pedido seleccionado = null;
        if (idPedido != null) {
            seleccionado = pedidoRepository.findById(idPedido).orElse(null);
        } else if (!pedidos.isEmpty()) {
            seleccionado = pedidos.get(0); // El más reciente
        }
        model.addAttribute("pedidoSeleccionado", seleccionado);

        // Si hay un pedido seleccionado, buscamos sus detalles reales
        if (seleccionado != null) {
            List<DetallePedido> detalles = detallePedidoRepository.findByIdPedido(seleccionado.getId());
            List<Map<String, Object>> detallesView = new ArrayList<>();

            for (DetallePedido d : detalles) {
                Platillo platillo = platilloService.obtenerPorId(d.getIdPlatillo()).orElse(null);
                String nombre = (platillo != null) ? platillo.getNombre() : "Platillo Desconocido";

                Map<String, Object> item = new HashMap<>();
                item.put("nombre", nombre);
                item.put("cantidad", d.getCantidad());
                BigDecimal subtotal = d.getPrecioAplicado().multiply(new BigDecimal(d.getCantidad()));
                item.put("subtotal", subtotal);
                detallesView.add(item);
            }
            model.addAttribute("detalles", detallesView);
        }

        return "admin/pedidos";
    }

    @PostMapping("/cambiar-estado/{id}")
    public String cambiarEstado(@PathVariable Long id, @RequestParam String nuevoEstado) {
        pedidoRepository.findById(id).ifPresent(pedido -> {
            pedido.setEstado(nuevoEstado);
            pedidoRepository.save(pedido);
        });
        return "redirect:/admin/pedidos?idPedido=" + id; // Mantiene el pedido seleccionado
    }
}