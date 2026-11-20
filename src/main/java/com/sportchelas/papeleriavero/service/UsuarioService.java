package com.sportchelas.papeleriavero.service;

import com.sportchelas.papeleriavero.config.PasswordUtil;
import com.sportchelas.papeleriavero.model.Usuario;
import com.sportchelas.papeleriavero.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario autenticar(String username, String passwordPlano) {
        if (username == null || username.isBlank() || passwordPlano == null || passwordPlano.isBlank()) {
            throw new IllegalArgumentException("Debes ingresar usuario y contraseña.");
        }

        Usuario usuario = usuarioRepository.findByUsername(username.trim());
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario o contraseña incorrectos.");
        }

        String hashIngresado = PasswordUtil.cifrarSHA256(passwordPlano);
        if (!usuario.getPassword().equals(hashIngresado)) {
            throw new IllegalArgumentException("Usuario o contraseña incorrectos.");
        }

        return usuario;
    }

    public Usuario guardarUsuario(Usuario usuario) {
        if (usuario.getNombreCompleto() == null || usuario.getNombreCompleto().isBlank()) {
            throw new IllegalArgumentException("El nombre completo es obligatorio.");
        }
        if (usuario.getUsername() == null || usuario.getUsername().isBlank()) {
            throw new IllegalArgumentException("El nombre de usuario es obligatorio.");
        }

        // Si es edición (ya tiene ID), conservamos contraseña anterior si la dejaron vacía
        if (usuario.getId() != null) {
            Usuario existente = usuarioRepository.findById(usuario.getId())
                    .orElseThrow(() -> new IllegalArgumentException("El usuario a editar no existe."));

            Usuario duplicado = usuarioRepository.findByUsername(usuario.getUsername().trim());
            if (duplicado != null && !duplicado.getId().equals(usuario.getId())) {
                throw new IllegalArgumentException("Ese nombre de usuario ya está ocupado.");
            }

            if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
                usuario.setPassword(existente.getPassword());
            } else {
                usuario.setPassword(PasswordUtil.cifrarSHA256(usuario.getPassword()));
            }
        } else {
            if (usuarioRepository.findByUsername(usuario.getUsername().trim()) != null) {
                throw new IllegalArgumentException("El usuario '" + usuario.getUsername() + "' ya existe.");
            }
            if (usuario.getPassword() == null || usuario.getPassword().length() < 4) {
                throw new IllegalArgumentException("La contraseña debe tener al menos 4 caracteres.");
            }
            usuario.setPassword(PasswordUtil.cifrarSHA256(usuario.getPassword()));
        }

        usuario.setUsername(usuario.getUsername().trim());
        return usuarioRepository.save(usuario);
    }

    public void eliminarUsuario(Long id, Long idSesionActual) {
        if (id.equals(idSesionActual)) {
            throw new IllegalArgumentException("No puedes eliminar tu propio usuario con sesión activa.");
        }
        usuarioRepository.deleteById(id);
    }
}