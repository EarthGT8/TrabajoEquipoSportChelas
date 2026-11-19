package com.sportchelas.papeleriavero.service;

import com.sportchelas.papeleriavero.model.Proveedor;
import com.sportchelas.papeleriavero.repository.ProveedorRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    public List<Proveedor> obtenerTodos() {
        return proveedorRepository.findAll();
    }

    public Proveedor guardarProveedor(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    public void eliminarProveedor(Long id) {
        proveedorRepository.deleteById(id);
    }
}