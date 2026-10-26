package mx.edu.iunis.eats.controller;

import mx.edu.iunis.eats.domain.DetallePedido;
import mx.edu.iunis.eats.domain.Platillo;
import mx.edu.iunis.eats.repository.DetallePedidoRepository;
import mx.edu.iunis.eats.service.PlatilloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/reportes")
public class AdminReporteController {

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private PlatilloService platilloService;

    @GetMapping
    public String reportes(Model model) {
        model.addAttribute("pageTitle", "Reportes");
        model.addAttribute("breadcrumb", "Análisis / Reportes de demanda");
        model.addAttribute("pantallaActual", "reportes");

        // 1. Obtener todos los detalles de pedidos
        List<DetallePedido> detalles = detallePedidoRepository.findAll();

        // 2. Agrupar las cantidades por ID de platillo
        Map<Long, Integer> ventasPorPlatillo = new HashMap<>();
        for (DetallePedido d : detalles) {
            if (d.getIdPlatillo() != null) {
                ventasPorPlatillo.put(d.getIdPlatillo(),
                        ventasPorPlatillo.getOrDefault(d.getIdPlatillo(), 0) + d.getCantidad());
            }
        }

        // 3. Crear la lista para la vista
        List<Map<String, Object>> topPlatillos = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : ventasPorPlatillo.entrySet()) {
            Platillo p = platilloService.obtenerPorId(entry.getKey()).orElse(null);
            if (p != null) {
                Map<String, Object> item = new HashMap<>();
                item.put("nombre", p.getNombre());
                item.put("categoria", p.getCategoria());
                item.put("unidades", entry.getValue());
                topPlatillos.add(item);
            }
        }

        // 4. Ordenar de mayor a menor y calcular porcentajes
        topPlatillos.sort((a, b) -> (Integer) b.get("unidades") - (Integer) a.get("unidades"));
        int maxVentas = topPlatillos.isEmpty() ? 1 : (Integer) topPlatillos.get(0).get("unidades");

        for (int i = 0; i < topPlatillos.size(); i++) {
            Map<String, Object> item = topPlatillos.get(i);
            int unidades = (Integer) item.get("unidades");
            item.put("porcentaje", (int) (((double) unidades / maxVentas) * 100));
            item.put("destacado", i == 0); // El primero se pinta de ámbar
        }

        // Tomar solo el Top 5
        List<Map<String, Object>> top5 = topPlatillos.stream().limit(5).collect(Collectors.toList());

        model.addAttribute("topPlatillos", top5);
        return "admin/reportes";
    }
}