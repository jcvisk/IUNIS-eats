package mx.edu.iunis.eats.controller;

import mx.edu.iunis.eats.service.ComboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import mx.edu.iunis.eats.domain.Combo;
import mx.edu.iunis.eats.service.PlatilloService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import mx.edu.iunis.eats.domain.ComboPlatillo;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/admin/combos")
public class AdminComboController {

    @Autowired
    private ComboService comboService;
    @Autowired
    private PlatilloService platilloService;

    // 1. LISTAR COMBOS
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pageTitle", "Combos");
        model.addAttribute("breadcrumb", "Catálogo / Combos");
        model.addAttribute("pantallaActual", "combos");
        model.addAttribute("combos", comboService.listarTodos());

        return "admin/combos";
    }

    // 2. MOSTRAR FORMULARIO NUEVO COMBO
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("pageTitle", "Nuevo combo");
        model.addAttribute("breadcrumb", "Catálogo / Combos / Nuevo");
        model.addAttribute("pantallaActual", "combos");
        model.addAttribute("combo", new Combo());

        model.addAttribute("platillos", platilloService.obtenerTodos());

        return "admin/formulario-combo";
    }

    // 3. MOSTRAR FORMULARIO PARA EDITAR COMBO
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {

        Combo combo = comboService.obtenerPorId(id).orElse(null);

        if (combo == null) {
            return "redirect:/admin/combos";
        }

        model.addAttribute("pageTitle", "Editar combo");
        model.addAttribute("breadcrumb", "Catálogo / Combos / Editar");
        model.addAttribute("pantallaActual", "combos");
        model.addAttribute("combo", combo);
        model.addAttribute("platillos", platilloService.obtenerTodos());

        List<ComboPlatillo> componentes =
                comboService.obtenerComponentes(id);

        Map<Long, Integer> cantidades = new java.util.HashMap<>();

        for (ComboPlatillo componente : componentes) {
            cantidades.put(
                    componente.getIdPlatillo(),
                    componente.getCantidad()
            );
        }

        model.addAttribute("cantidades", cantidades);

        return "admin/formulario-combo";
    }

    // 4. GUARDAR COMBO Y SUS COMPONENTES
    @PostMapping("/guardar")
    public String guardar(
            @ModelAttribute Combo combo,
            @RequestParam Map<String, String> parametros,
            Model model) {

        List<ComboPlatillo> componentes = new ArrayList<>();
        String error = null;

        for (Map.Entry<String, String> entrada : parametros.entrySet()) {

            String clave = entrada.getKey();

            if (clave.startsWith("cantidades[") && clave.endsWith("]")) {

                String idTexto = clave.substring(11, clave.length() - 1);
                Long idPlatillo;
                Integer cantidad;

                try {
                    idPlatillo = Long.parseLong(idTexto);
                } catch (NumberFormatException e) {
                    error = "El identificador de un platillo no es válido.";
                    break;
                }

                if (platilloService.obtenerPorId(idPlatillo).isEmpty()) {
                    error = "Uno de los platillos seleccionados no existe.";
                    break;
                }

                String valor = entrada.getValue();

                if (valor == null || valor.isBlank()) {
                    cantidad = 0;
                } else {
                    try {
                        cantidad = Integer.parseInt(valor);
                    } catch (NumberFormatException e) {
                        error = "La cantidad debe ser un número entero válido.";
                        break;
                    }
                }

                if (cantidad < 0) {
                    error = "La cantidad no puede ser negativa.";
                    break;
                }

                if (cantidad > 0) {
                    componentes.add(
                            new ComboPlatillo(null, idPlatillo, cantidad)
                    );
                }
            }
        }

        if (error != null) {

            Map<Long, String> cantidadesFormulario = new java.util.HashMap<>();

            for (Map.Entry<String, String> entrada : parametros.entrySet()) {

                String clave = entrada.getKey();

                if (clave.startsWith("cantidades[") && clave.endsWith("]")) {
                    try {
                        Long id = Long.parseLong(
                                clave.substring(11, clave.length() - 1)
                        );

                        cantidadesFormulario.put(id, entrada.getValue());

                    } catch (NumberFormatException e) {
                        // El identificador inválido ya fue detectado.
                    }
                }
            }

            model.addAttribute("pageTitle", "Formulario de combo");
            model.addAttribute("breadcrumb", "Catálogo / Combos");
            model.addAttribute("pantallaActual", "combos");
            model.addAttribute("platillos", platilloService.obtenerTodos());
            model.addAttribute("cantidades", cantidadesFormulario);
            model.addAttribute("error", error);

            return "admin/formulario-combo";
        }

        try {
            comboService.guardar(combo, componentes);
            return "redirect:/admin/combos";

        } catch (IllegalArgumentException e) {
            Map<Long, String> cantidadesFormulario = new java.util.HashMap<>();

            for (Map.Entry<String, String> entrada : parametros.entrySet()) {

                String clave = entrada.getKey();

                if (clave.startsWith("cantidades[") && clave.endsWith("]")) {
                    try {
                        Long id = Long.parseLong(
                                clave.substring(11, clave.length() - 1)
                        );

                        cantidadesFormulario.put(id, entrada.getValue());

                    } catch (NumberFormatException ignored) {
                        // El identificador inválido ya fue detectado.
                    }
                }
            }

            model.addAttribute("pageTitle", "Formulario de combo");
            model.addAttribute("breadcrumb", "Catálogo / Combos");
            model.addAttribute("pantallaActual", "combos");
            model.addAttribute("platillos", platilloService.obtenerTodos());
            model.addAttribute("cantidades", cantidadesFormulario);
            model.addAttribute("error", e.getMessage());

            return "admin/formulario-combo";
        }
    }

}