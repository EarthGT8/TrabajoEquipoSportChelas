package com.sportchelas.papeleriavero.repository;

import com.sportchelas.papeleriavero.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
}