package com.sportchelas.papeleriavero.controller;

import com.sportchelas.papeleriavero.model.Proveedor;
import com.sportchelas.papeleriavero.service.ProveedorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return "proveedores";
    }

    @PostMapping("/guardar")
    public String guardarProveedor(@ModelAttribute("nuevoProveedor") Proveedor proveedor) {
        proveedorService.guardarProveedor(proveedor);
        return "redirect:/proveedores";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProveedor(@PathVariable Long id) {
        proveedorService.eliminarProveedor(id);
        return "redirect:/proveedores";
    }
}