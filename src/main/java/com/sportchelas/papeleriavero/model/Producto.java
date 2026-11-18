package com.sportchelas.papeleriavero.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(unique = true)
    private String codigoBarras;

    // Precio de venta al público (el que lee la Caja de Ángel)
    @Column(nullable = false)
    private Double precio;

    private Double precioCompra;

    @Column(nullable = false)
    private Integer stock;

    private Integer stockMinimo = 5;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}