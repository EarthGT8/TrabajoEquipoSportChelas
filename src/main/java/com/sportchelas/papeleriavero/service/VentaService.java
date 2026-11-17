package com.sportchelas.papeleriavero.service;

import com.sportchelas.papeleriavero.model.DetalleVenta;
import com.sportchelas.papeleriavero.model.Venta;
import com.sportchelas.papeleriavero.repository.VentaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;

    // Inyectamos el repositorio para poder guardar en la BD
    public VentaService(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    // Este es tu primer Caso de Uso real: Registrar una venta
    public Venta procesarVenta(Venta nuevaVenta) {
        // 1. Ponemos la fecha y hora actual
        nuevaVenta.setFechaHora(LocalDateTime.now());
        
        // 2. Calculamos el total sumando el subtotal de cada detalle
        double totalCalculado = 0.0;
        if (nuevaVenta.getDetalles() != null) {
            for (DetalleVenta detalle : nuevaVenta.getDetalles()) {
                totalCalculado += detalle.getSubtotal();
                // Le decimos a cada detalle a qué venta pertenece
                detalle.setVenta(nuevaVenta);
            }
        }
        nuevaVenta.setTotal(totalCalculado);

        // 3. Guardamos la venta en la Base de Datos (esto guarda también los detalles automáticamente)
        return ventaRepository.save(nuevaVenta);
    }
}