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
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío");
        }
        if (producto.getImagenUrl() == null || producto.getImagenUrl().trim().isEmpty()) {
            throw new IllegalArgumentException("El url del producto no puede estar vacío");
        }
        if (producto.getDescripcion() == null || producto.getDescripcion().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripcion del producto no puede estar vacío");
        }
        if (producto.getPrecioBruto() == null || producto.getPrecioBruto() == 0) {
            throw new IllegalArgumentException("El precio bruto del producto no puede estar vacío o cero");
        }
         if (producto.getPrecioTotal() == null || producto.getPrecioBruto() == 0) {
            throw new IllegalArgumentException("El precio total del producto no puede estar vacío o cero");
        }
         if (producto.getValorImpuestos() == null || producto.getValorImpuestos() == 0) {
            throw new IllegalArgumentException("El valor de los impuestos  del producto no puede estar vacío o cero");
        }
       
        return productoRepository.save(producto);
    }

    public Producto update(Integer id, Producto data) {
        if (data.getNombre() == null || data.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío");
        }
        if (data.getImagenUrl() == null || data.getImagenUrl().trim().isEmpty()) {
            throw new IllegalArgumentException("El url del producto no puede estar vacío");
        }
        if (data.getDescripcion() == null || data.getDescripcion().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripcion del producto no puede estar vacío");
        }
        if (data.getPrecioBruto() == null || data.getPrecioBruto() == 0) {
            throw new IllegalArgumentException("El precio bruto del producto no puede estar vacío o cero");
        }
         if (data.getPrecioTotal() == null || data.getPrecioBruto() == 0) {
            throw new IllegalArgumentException("El precio total del producto no puede estar vacío o cero");
        }
         if (data.getValorImpuestos() == null || data.getValorImpuestos() == 0) {
            throw new IllegalArgumentException("El valor de los impuestos  del producto no puede estar vacío o cero");
        }
       

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
