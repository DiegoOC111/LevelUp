package com.example.LevelUp.service;

import com.example.LevelUp.model.TipoProducto;
import com.example.LevelUp.repository.TipoProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoProductoService {

    private final TipoProductoRepository tipoProductoRepository;

    public TipoProductoService(TipoProductoRepository tipoProductoRepository) {
        this.tipoProductoRepository = tipoProductoRepository;
    }

    public List<TipoProducto> findAll() {
        return tipoProductoRepository.findAll();
    }

    public Optional<TipoProducto> findById(Integer id) {
        return tipoProductoRepository.findById(id);
    }

    public TipoProducto save(TipoProducto tipoProducto) {
        if (tipoProducto.getNombre() == null || tipoProducto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del tipo de producto no puede estar vacío");
        }
        return tipoProductoRepository.save(tipoProducto);
    }

    public TipoProducto update(Integer id, TipoProducto data) {
        if (data.getNombre() == null || data.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del tipo de producto no puede estar vacío");
        }
        return tipoProductoRepository.findById(id).map(tp -> {
            tp.setNombre(data.getNombre());
            return tipoProductoRepository.save(tp);
        }).orElse(null);
    }

    public void delete(Integer id) {
        tipoProductoRepository.deleteById(id);
    }
}
