package com.example.LevelUp.service;

import com.example.LevelUp.model.Producto;
import com.example.LevelUp.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Optional<Producto> findById(Integer id) {
        return productoRepository.findById(id);
    }

    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto update(Integer id, Producto data) {
        return productoRepository.findById(id).map(prod -> {
            prod.setNombre(data.getNombre());
            prod.setDescripcion(data.getDescripcion());
            prod.setImagenUrl(data.getImagenUrl());
            prod.setPrecioBruto(data.getPrecioBruto());
            prod.setStock(data.getStock());
            prod.setIdProd(data.getIdProd());
            prod.setPrecioTotal(data.getPrecioTotal());
            prod.setValorImpuestos(data.getValorImpuestos());
            return productoRepository.save(prod);
        }).orElse(null);
    }

    public void delete(Integer id) {
        productoRepository.deleteById(id);
    }
}
