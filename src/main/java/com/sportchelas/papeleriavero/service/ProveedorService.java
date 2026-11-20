package com.sportchelas.papeleriavero.service;

import com.sportchelas.papeleriavero.model.Proveedor;
import com.sportchelas.papeleriavero.repository.ProveedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    public List<Proveedor> obtenerTodos() {
        return proveedorRepository.findAll();
    }

    public Optional<Proveedor> buscarPorId(Long id) {
        return proveedorRepository.findById(id);
    }

    public Proveedor guardarProveedor(Proveedor proveedor) {
        if (proveedor.getEmpresa() == null || proveedor.getEmpresa().isBlank()) {
            throw new IllegalArgumentException("El nombre de la empresa es obligatorio.");
        }
        if (proveedor.getNombreContacto() == null || proveedor.getNombreContacto().isBlank()) {
            throw new IllegalArgumentException("El nombre del contacto es obligatorio.");
        }
        if (proveedor.getTelefono() != null && !proveedor.getTelefono().isBlank()
                && proveedor.getTelefono().trim().length() < 7) {
            throw new IllegalArgumentException("El teléfono debe tener al menos 7 dígitos.");
        }

        proveedor.setEmpresa(proveedor.getEmpresa().trim());
        proveedor.setNombreContacto(proveedor.getNombreContacto().trim());
        return proveedorRepository.save(proveedor);
    }

    public void eliminarProveedor(Long id) {
        if (!proveedorRepository.existsById(id)) {
            throw new IllegalArgumentException("El proveedor que intentas eliminar no existe.");
        }
        proveedorRepository.deleteById(id);
    }
}