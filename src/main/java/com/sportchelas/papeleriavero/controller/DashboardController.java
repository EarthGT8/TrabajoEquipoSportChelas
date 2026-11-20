package com.sportchelas.papeleriavero.controller;

import com.sportchelas.papeleriavero.model.Producto;
import com.sportchelas.papeleriavero.model.Venta;
import com.sportchelas.papeleriavero.repository.ProductoRepository;
import com.sportchelas.papeleriavero.repository.ProveedorRepository;
import com.sportchelas.papeleriavero.repository.UsuarioRepository;
import com.sportchelas.papeleriavero.service.VentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    private final ProductoRepository productoRepository;
    private final ProveedorRepository proveedorRepository;
    private final UsuarioRepository usuarioRepository;
    private final VentaService ventaService;

    public DashboardController(ProductoRepository productoRepository,
                               ProveedorRepository proveedorRepository,
                               UsuarioRepository usuarioRepository,
                               VentaService ventaService) {
        this.productoRepository = productoRepository;
        this.proveedorRepository = proveedorRepository;
        this.usuarioRepository = usuarioRepository;
        this.ventaService = ventaService;
    }

    @GetMapping("/dashboard")
    public String mostrarDashboard(Model model) {
        List<Producto> productos = productoRepository.findAll();
        List<Producto> stockBajo = productos.stream()
                .filter(p -> p.getStock() != null && p.getStock() <= (p.getStockMinimo() != null ? p.getStockMinimo() : 5))
                .toList();
        List<Venta> ventas = ventaService.obtenerHistorialVentas();

        model.addAttribute("totalCaja", ventaService.calcularTotalCaja());
        model.addAttribute("totalVentas", ventas.size());
        model.addAttribute("totalProductos", productos.size());
        model.addAttribute("totalProveedores", proveedorRepository.count());
        model.addAttribute("totalUsuarios", usuarioRepository.count());
        model.addAttribute("productosStockBajo", stockBajo);
        model.addAttribute("ultimasVentas", ventas.stream().limit(5).toList());
        return "dashboard";
    }

    @GetMapping("/reportes")
    public String mostrarReportes(Model model) {
        List<Producto> productos = productoRepository.findAll();
        List<Producto> stockBajo = productos.stream()
                .filter(p -> p.getStock() != null && p.getStock() <= (p.getStockMinimo() != null ? p.getStockMinimo() : 5))
                .toList();
        List<Venta> ventas = ventaService.obtenerHistorialVentas();

        double valorInventario = productos.stream()
                .mapToDouble(p -> (p.getPrecio() != null && p.getStock() != null) ? p.getPrecio() * p.getStock() : 0.0)
                .sum();

        model.addAttribute("historialVentas", ventas);
        model.addAttribute("totalCaja", ventaService.calcularTotalCaja());
        model.addAttribute("totalVentas", ventas.size());
        model.addAttribute("valorInventario", valorInventario);
        model.addAttribute("productosStockBajo", stockBajo);
        return "reportes";
    }
}