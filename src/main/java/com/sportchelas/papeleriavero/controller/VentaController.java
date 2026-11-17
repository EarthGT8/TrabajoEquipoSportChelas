package com.sportchelas.papeleriavero.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pos") // Esta será la ruta en el navegador (localhost:8080/pos)
public class VentaController {

    @GetMapping
    public String mostrarCaja(Model model) {
        // Aquí después mandaremos la lista de productos disponibles al HTML
        return "pos"; // Esto le dice a Spring que busque un archivo llamado pos.html
    }
}