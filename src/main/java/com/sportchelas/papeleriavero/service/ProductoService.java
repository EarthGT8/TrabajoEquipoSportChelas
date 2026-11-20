package com.sportchelas.papeleriavero.service;

import com.sportchelas.papeleriavero.model.Producto;
import com.sportchelas.papeleriavero.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }

    public Producto guardarProducto(Producto producto) {
        if (producto.getNombre() == null || producto.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio.");
        }
        if (producto.getCodigoBarras() == null || producto.getCodigoBarras().isBlank()) {
            throw new IllegalArgumentException("El código de barras es obligatorio.");
        }
        if (producto.getPrecio() == null || producto.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio de venta debe ser mayor a $0.");
        }
        if (producto.getPrecioCompra() != null && producto.getPrecioCompra() < 0) {
            throw new IllegalArgumentException("El precio de compra no puede ser negativo.");
        }
        if (producto.getStock() == null || producto.getStock() < 0) {
            throw new IllegalArgumentException("El stock actual no puede ser negativo.");
        }
        if (producto.getStockMinimo() == null) {
            producto.setStockMinimo(5);
        } else if (producto.getStockMinimo() < 0) {
            throw new IllegalArgumentException("El stock mínimo no puede ser negativo.");
        }

        String codigoLimpio = producto.getCodigoBarras().trim();
        Optional<Producto> duplicado = productoRepository.findByCodigoBarras(codigoLimpio);
        if (duplicado.isPresent()) {
            if (producto.getId() == null || !duplicado.get().getId().equals(producto.getId())) {
                throw new IllegalArgumentException("El código de barras '" + codigoLimpio + "' ya pertenece a otro producto.");
            }
        }

        producto.setNombre(producto.getNombre().trim());
        producto.setCodigoBarras(codigoLimpio);
        return productoRepository.save(producto);
    }

    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new IllegalArgumentException("El producto que intentas eliminar no existe.");
        }
        productoRepository.deleteById(id);
    }

    public void descontarStock(Long productoId, int cantidadVendida) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado."));

        if (producto.getStock() < cantidadVendida) {
            throw new IllegalArgumentException("Stock insuficiente para: " + producto.getNombre());
        }

        producto.setStock(producto.getStock() - cantidadVendida);
        productoRepository.save(producto);
    }
}