package com.sportchelas.papeleriavero.repository;

import com.sportchelas.papeleriavero.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Solo con extender JpaRepository, ya tenemos metodos como:
    // save(), findAll(), findById(), deleteById()
}