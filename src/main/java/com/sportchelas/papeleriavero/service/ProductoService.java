package com.sportchelas.papeleriavero.service;

import com.sportchelas.papeleriavero.model.Producto;
import com.sportchelas.papeleriavero.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public Producto guardarProducto(Producto producto) {
        if (producto.getStockMinimo() == null) {
            producto.setStockMinimo(5);
        }
        return productoRepository.save(producto);
    }

    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

    // Método listo para cuando Ángel lo llame desde su módulo de Ventas
    public void descontarStock(Long productoId, int cantidadVendida) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (producto.getStock() < cantidadVendida) {
            throw new RuntimeException("Stock insuficiente para: " + producto.getNombre());
        }

        producto.setStock(producto.getStock() - cantidadVendida);
        productoRepository.save(producto);
    }
}