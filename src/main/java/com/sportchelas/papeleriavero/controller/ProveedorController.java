package com.sportchelas.papeleriavero.controller;

import com.sportchelas.papeleriavero.model.Proveedor;
import com.sportchelas.papeleriavero.service.ProveedorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/proveedores")
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @GetMapping
    public String mostrarProveedores(Model model) {
        model.addAttribute("listaProveedores", proveedorService.obtenerTodos());
        model.addAttribute("nuevoProveedor", new Proveedor());
        model.addAttribute("modoEdicion", false);
        return "proveedores";
    }

    @GetMapping("/editar/{id}")
    public String cargarEdicion(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Proveedor proveedor = proveedorService.buscarPorId(id)
                    .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado."));
            model.addAttribute("listaProveedores", proveedorService.obtenerTodos());
            model.addAttribute("nuevoProveedor", proveedor);
            model.addAttribute("modoEdicion", true);
            return "proveedores";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
            return "redirect:/proveedores";
        } finally {
            System.out.println("[AUDITORIA PROVEEDORES] Solicitud de edición ID: " + id);
        }
    }

    @PostMapping("/guardar")
    public String guardarProveedor(@ModelAttribute("nuevoProveedor") Proveedor proveedor,
                                   RedirectAttributes redirectAttributes) {
        boolean esEdicion = proveedor.getId() != null;
        try {
            proveedorService.guardarProveedor(proveedor);
            redirectAttributes.addFlashAttribute("mensajeExito",
                    esEdicion ? "Proveedor actualizado correctamente." : "Proveedor registrado correctamente.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error inesperado al guardar el proveedor.");
        } finally {
            System.out.println("[AUDITORIA PROVEEDORES] Operación " + (esEdicion ? "ACTUALIZACION" : "ALTA") + " finalizada.");
        }
        return "redirect:/proveedores";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProveedor(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            proveedorService.eliminarProveedor(id);
            redirectAttributes.addFlashAttribute("mensajeExito", "Proveedor eliminado del directorio.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error inesperado al eliminar el proveedor.");
        } finally {
            System.out.println("[AUDITORIA PROVEEDORES] Intento de eliminación ID: " + id);
        }
        return "redirect:/proveedores";
    }
}