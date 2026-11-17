package com.sportchelas.papeleriavero.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "detalle_ventas")
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación: Muchos detalles pertenecen a un Producto
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    // Relación: Muchos detalles pertenecen a una misma Venta
    @ManyToOne
    @JoinColumn(name = "venta_id")
    private Venta venta;

    private Integer cantidad;
    private Double subtotal;
}