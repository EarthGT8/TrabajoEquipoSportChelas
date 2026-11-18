package com.sportchelas.papeleriavero.controller;

import com.sportchelas.papeleriavero.model.Categoria;
import com.sportchelas.papeleriavero.model.Producto;
import com.sportchelas.papeleriavero.service.CategoriaService;
import com.sportchelas.papeleriavero.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/inventario")
public class ProductoController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    public ProductoController(ProductoService productoService, CategoriaService categoriaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String mostrarInventario(Model model) {
        model.addAttribute("listaProductos", productoService.obtenerTodos());
        model.addAttribute("listaCategorias", categoriaService.obtenerTodas());
        model.addAttribute("nuevoProducto", new Producto());
        model.addAttribute("nuevaCategoria", new Categoria());
        return "inventario";
    }

    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute("nuevoProducto") Producto producto,
                                  @RequestParam(required = false) Long categoriaId) {
        if (categoriaId != null) {
            categoriaService.buscarPorId(categoriaId).ifPresent(producto::setCategoria);
        }
        productoService.guardarProducto(producto);
        return "redirect:/inventario";
    }

    @PostMapping("/categorias/guardar")
    public String guardarCategoria(@ModelAttribute("nuevaCategoria") Categoria categoria) {
        categoriaService.guardarCategoria(categoria);
        return "redirect:/inventario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return "redirect:/inventario";
    }
}