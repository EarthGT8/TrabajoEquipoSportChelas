package com.sportchelas.papeleriavero.controller;

import com.sportchelas.papeleriavero.service.ProveedorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return "proveedores"; // Busca el archivo proveedores.html
    }
}