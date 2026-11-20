package com.sportchelas.papeleriavero.config;

import com.sportchelas.papeleriavero.model.Usuario;
import com.sportchelas.papeleriavero.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsuarioInitializer {

    @Bean
    CommandLineRunner inicializarUsuarios(UsuarioRepository usuarioRepository) {
        return args -> {
            if (usuarioRepository.findByUsername("admin") == null) {
                Usuario admin = new Usuario();
                admin.setNombreCompleto("Verónica Administrador");
                admin.setUsername("admin");
                admin.setPassword(PasswordUtil.cifrarSHA256("admin123"));
                admin.setRol("ADMIN");
                usuarioRepository.save(admin);
            }

            if (usuarioRepository.findByUsername("cajero") == null) {
                Usuario cajero = new Usuario();
                cajero.setNombreCompleto("Cajero Turno Matutino");
                cajero.setUsername("cajero");
                cajero.setPassword(PasswordUtil.cifrarSHA256("cajero123"));
                cajero.setRol("CAJERO");
                usuarioRepository.save(cajero);
            }
        };
    }
}