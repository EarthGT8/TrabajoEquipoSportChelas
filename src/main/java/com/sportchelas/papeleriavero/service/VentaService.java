package com.sportchelas.papeleriavero.service;

import com.sportchelas.papeleriavero.model.DetalleVenta;
import com.sportchelas.papeleriavero.model.Producto;
import com.sportchelas.papeleriavero.model.Venta;
import com.sportchelas.papeleriavero.repository.ProductoRepository;
import com.sportchelas.papeleriavero.repository.VentaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class VentaService {

    public record ItemCarrito(Long productoId, Integer cantidad) {}

    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;

    public VentaService(VentaRepository ventaRepository, ProductoRepository productoRepository) {
        this.ventaRepository = ventaRepository;
        this.productoRepository = productoRepository;
    }

    @Transactional
    public Venta procesarVenta(List<ItemCarrito> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("El carrito está vacío.");
        }

        Venta nuevaVenta = new Venta();
        nuevaVenta.setFechaHora(LocalDateTime.now());

        List<DetalleVenta> detalles = new ArrayList<>();
        double totalCalculado = 0.0;

        for (ItemCarrito item : items) {
            if (item.cantidad() == null || item.cantidad() <= 0) {
                throw new IllegalArgumentException("Cantidad inválida.");
            }

            Producto producto = productoRepository.findById(item.productoId())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado (ID: " + item.productoId() + ")"));

            if (producto.getStock() < item.cantidad()) {
                throw new IllegalArgumentException("Stock insuficiente para: " + producto.getNombre()
                        + " (Disponibles: " + producto.getStock() + ")");
            }

            // Descontar stock real en el inventario
            producto.setStock(producto.getStock() - item.cantidad());
            productoRepository.save(producto);

            DetalleVenta detalle = new DetalleVenta();
            detalle.setProducto(producto);
            detalle.setVenta(nuevaVenta);
            detalle.setCantidad(item.cantidad());
            double subtotal = producto.getPrecio() * item.cantidad();
            detalle.setSubtotal(subtotal);

            detalles.add(detalle);
            totalCalculado += subtotal;
        }

        nuevaVenta.setDetalles(detalles);
        nuevaVenta.setTotal(totalCalculado);

        return ventaRepository.save(nuevaVenta);
    }

    public List<Venta> obtenerHistorialVentas() {
        return ventaRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public double calcularTotalCaja() {
        return ventaRepository.findAll()
                .stream()
                .mapToDouble(v -> v.getTotal() != null ? v.getTotal() : 0.0)
                .sum();
    }
}