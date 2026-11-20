package com.sportchelas.papeleriavero.controller;

import com.sportchelas.papeleriavero.model.Usuario;
import com.sportchelas.papeleriavero.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/")
    public String inicio(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario != null) {
            return "ADMIN".equalsIgnoreCase(usuario.getRol()) ? "redirect:/dashboard" : "redirect:/pos";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String mostrarLogin(@RequestParam(required = false) String error,
                               @RequestParam(required = false) String logout,
                               Model model) {
        if ("sesion".equals(error)) {
            model.addAttribute("mensajeError", "Debes iniciar sesión para acceder al sistema.");
        }
        if (logout != null) {
            model.addAttribute("mensajeExito", "Sesión cerrada correctamente.");
        }
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String username,
                                @RequestParam String password,
                                HttpSession session,
                                Model model) {
        boolean accesoExitoso = false;
        try {
            Usuario usuario = usuarioService.autenticar(username, password);
            session.setAttribute("usuarioLogueado", usuario);
            accesoExitoso = true;
            return "ADMIN".equalsIgnoreCase(usuario.getRol()) ? "redirect:/dashboard" : "redirect:/pos";
        } catch (IllegalArgumentException e) {
            model.addAttribute("mensajeError", e.getMessage());
            model.addAttribute("usernameIngresado", username);
            return "login";
        } catch (Exception e) {
            model.addAttribute("mensajeError", "Error inesperado al iniciar sesión.");
            return "login";
        } finally {
            System.out.println("[AUDITORIA LOGIN] Usuario: " + username + " | Acceso: " + (accesoExitoso ? "CONCEDIDO" : "DENEGADO"));
        }
    }

    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }
}