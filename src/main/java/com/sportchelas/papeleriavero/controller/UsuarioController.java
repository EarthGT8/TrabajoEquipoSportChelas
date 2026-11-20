package com.sportchelas.papeleriavero.controller;

import com.sportchelas.papeleriavero.model.Usuario;
import com.sportchelas.papeleriavero.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String mostrarUsuarios(Model model) {
        model.addAttribute("listaUsuarios", usuarioService.obtenerTodos());
        model.addAttribute("nuevoUsuario", new Usuario());
        model.addAttribute("modoEdicion", false);
        return "usuarios";
    }

    @GetMapping("/editar/{id}")
    public String cargarEdicion(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Usuario usuario = usuarioService.buscarPorId(id)
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));
            usuario.setPassword(""); // No exponemos el hash en el input
            model.addAttribute("listaUsuarios", usuarioService.obtenerTodos());
            model.addAttribute("nuevoUsuario", usuario);
            model.addAttribute("modoEdicion", true);
            return "usuarios";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
            return "redirect:/usuarios";
        } finally {
            System.out.println("[AUDITORIA USUARIOS] Solicitud de edición ID: " + id);
        }
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute("nuevoUsuario") Usuario usuario,
                                 RedirectAttributes redirectAttributes) {
        boolean esEdicion = usuario.getId() != null;
        try {
            usuarioService.guardarUsuario(usuario);
            redirectAttributes.addFlashAttribute("mensajeExito",
                    esEdicion ? "Usuario actualizado correctamente." : "Usuario creado con contraseña cifrada SHA-256.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error inesperado al guardar el usuario.");
        } finally {
            System.out.println("[AUDITORIA USUARIOS] Operación " + (esEdicion ? "ACTUALIZACION" : "ALTA") + " finalizada.");
        }
        return "redirect:/usuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        try {
            Usuario logueado = (Usuario) session.getAttribute("usuarioLogueado");
            Long idActual = (logueado != null) ? logueado.getId() : -1L;
            usuarioService.eliminarUsuario(id, idActual);
            redirectAttributes.addFlashAttribute("mensajeExito", "Usuario eliminado correctamente.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error inesperado al eliminar el usuario.");
        } finally {
            System.out.println("[AUDITORIA USUARIOS] Intento de eliminación ID: " + id);
        }
        return "redirect:/usuarios";
    }
}