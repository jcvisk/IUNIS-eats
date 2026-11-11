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
            @RequestParam Map<String, String> parametros) {

        List<ComboPlatillo> componentes = new ArrayList<>();

        for (Map.Entry<String, String> entrada : parametros.entrySet()) {

            String clave = entrada.getKey();

            if (clave.startsWith("cantidades[") && clave.endsWith("]")) {

                String idTexto = clave.substring(11, clave.length() - 1);
                Long idPlatillo = Long.parseLong(idTexto);
                Integer cantidad = Integer.parseInt(entrada.getValue());

                if (cantidad > 0) {
                    componentes.add(
                            new ComboPlatillo(null, idPlatillo, cantidad)
                    );
                }
            }
        }

        comboService.guardar(combo, componentes);

        return "redirect:/admin/combos";
    }

}