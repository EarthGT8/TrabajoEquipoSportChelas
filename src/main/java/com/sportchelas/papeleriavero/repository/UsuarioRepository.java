package com.sportchelas.papeleriavero.repository;

import com.sportchelas.papeleriavero.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Spring Boot crea automáticamente la consulta SQL con solo nombrar el método así:
    Usuario findByUsername(String username);
}