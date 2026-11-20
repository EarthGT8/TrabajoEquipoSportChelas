package com.sportchelas.papeleriavero.controller;

import com.sportchelas.papeleriavero.model.Categoria;
import com.sportchelas.papeleriavero.model.Producto;
import com.sportchelas.papeleriavero.service.CategoriaService;
import com.sportchelas.papeleriavero.service.ProductoService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addAttribute("modoEdicion", false);
        return "inventario";
    }

    @GetMapping("/editar/{id}")
    public String cargarEdicion(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Producto producto = productoService.buscarPorId(id)
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado."));
            model.addAttribute("listaProductos", productoService.obtenerTodos());
            model.addAttribute("listaCategorias", categoriaService.obtenerTodas());
            model.addAttribute("nuevoProducto", producto);
            model.addAttribute("nuevaCategoria", new Categoria());
            model.addAttribute("modoEdicion", true);
            return "inventario";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
            return "redirect:/inventario";
        } finally {
            System.out.println("[AUDITORIA INVENTARIO] Solicitud de edición para producto ID: " + id);
        }
    }

    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute("nuevoProducto") Producto producto,
                                  @RequestParam(required = false) Long categoriaId,
                                  RedirectAttributes redirectAttributes) {
        String accion = (producto.getId() != null) ? "ACTUALIZACION" : "ALTA";
        try {
            if (categoriaId != null) {
                categoriaService.buscarPorId(categoriaId).ifPresent(producto::setCategoria);
            } else {
                producto.setCategoria(null);
            }
            productoService.guardarProducto(producto);
            redirectAttributes.addFlashAttribute("mensajeExito",
                    producto.getId() != null && "ACTUALIZACION".equals(accion)
                            ? "Producto actualizado correctamente."
                            : "Producto registrado correctamente.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error de integridad: el código de barras ya existe.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error inesperado al guardar el producto.");
        } finally {
            System.out.println("[AUDITORIA INVENTARIO] Operación " + accion + " finalizada.");
        }
        return "redirect:/inventario";
    }

    @PostMapping("/categorias/guardar")
    public String guardarCategoria(@ModelAttribute("nuevaCategoria") Categoria categoria,
                                   RedirectAttributes redirectAttributes) {
        try {
            if (categoria.getNombre() == null || categoria.getNombre().isBlank()) {
                throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío.");
            }
            categoria.setNombre(categoria.getNombre().trim());
            categoriaService.guardarCategoria(categoria);
            redirectAttributes.addFlashAttribute("mensajeExito", "Categoría '" + categoria.getNombre() + "' agregada.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Esa categoría ya existe en el catálogo.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error inesperado al guardar la categoría.");
        } finally {
            System.out.println("[AUDITORIA INVENTARIO] Intento de registro de categoría finalizado.");
        }
        return "redirect:/inventario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            productoService.eliminarProducto(id);
            redirectAttributes.addFlashAttribute("mensajeExito", "Producto eliminado del inventario.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("mensajeError",
                    "No se puede eliminar este producto porque ya forma parte de tickets de venta registrados.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error inesperado al eliminar el producto.");
        } finally {
            System.out.println("[AUDITORIA INVENTARIO] Intento de eliminación del producto ID: " + id);
        }
        return "redirect:/inventario";
    }
}