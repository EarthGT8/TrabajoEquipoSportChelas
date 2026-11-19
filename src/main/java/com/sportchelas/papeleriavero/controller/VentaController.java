package com.sportchelas.papeleriavero.controller;

import com.sportchelas.papeleriavero.model.Venta;
import com.sportchelas.papeleriavero.repository.ProductoRepository;
import com.sportchelas.papeleriavero.service.VentaService;
import com.sportchelas.papeleriavero.service.VentaService.ItemCarrito;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pos")
public class VentaController {

    private final ProductoRepository productoRepository;
    private final VentaService ventaService;

    public VentaController(ProductoRepository productoRepository, VentaService ventaService) {
        this.productoRepository = productoRepository;
        this.ventaService = ventaService;
    }

    @GetMapping
    public String mostrarCaja(Model model) {
        model.addAttribute("listaProductos", productoRepository.findAll());
        model.addAttribute("historialVentas", ventaService.obtenerHistorialVentas());
        model.addAttribute("totalCaja", ventaService.calcularTotalCaja());
        return "pos";
    }

    @PostMapping("/cobrar")
    @ResponseBody
    public ResponseEntity<?> cobrarVenta(@RequestBody List<ItemCarrito> items) {
    try {
            Venta ventaGuardada = ventaService.procesarVenta(items);
            return ResponseEntity.ok(Map.of(
                    "ventaId", ventaGuardada.getId(),
                    "total", ventaGuardada.getTotal(),
                    "mensaje", "Venta #" + ventaGuardada.getId() + " registrada con éxito."
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}