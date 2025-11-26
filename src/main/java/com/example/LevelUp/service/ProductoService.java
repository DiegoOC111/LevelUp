package com.example.LevelUp.service;

import com.example.LevelUp.controller.DTO.PostPROD;
import com.example.LevelUp.model.Producto;
import com.example.LevelUp.model.TipoProducto;
import com.example.LevelUp.repository.ProductoRepository;
import jakarta.validation.constraints.Null;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    private final TipoProductoService tipoProductoService;


    private final ProductoRepository productoRepository;

    public ProductoService(TipoProductoService tipoProductoService, ProductoRepository productoRepository) {
        this.tipoProductoService = tipoProductoService;
        this.productoRepository = productoRepository;
    }

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Optional<Producto> findById(Integer id) {
        return productoRepository.findById(id);
    }

    public Producto save(PostPROD producto) {
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

        Optional<TipoProducto> tip =  tipoProductoService.findById(producto.getId_tipo());
        if (tip.isEmpty()) {            // no existe el TipoProducto
            throw new IllegalArgumentException("El tipo producto no existe");
        }
        TipoProducto tipoProducto = tip.get();
        Producto p = new Producto();
        p.setIdProd(tipoProducto);
        p.setDescripcion(producto.getDescripcion());
        p.setPrecioBruto(producto.getPrecioBruto());
        p.setPrecioTotal(producto.getPrecioTotal());
        p.setValorImpuestos(producto.getValorImpuestos());
        p.setImagenUrl(producto.getImagenUrl());
        p.setNombre(producto.getNombre());
        p.setStock(producto.getStock());
        return productoRepository.save(p);
    }

    public Producto update(Integer id, PostPROD data) {
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
        Optional<TipoProducto> tip =  tipoProductoService.findById(data.getId_tipo());
        if (tip.isEmpty()) {            // no existe el TipoProducto
            throw new IllegalArgumentException("El tipo producto no existe");
        }
        TipoProducto tipoProducto = tip.get();
        return productoRepository.findById(id).map(prod -> {
            prod.setNombre(data.getNombre());
            prod.setDescripcion(data.getDescripcion());
            prod.setImagenUrl(data.getImagenUrl());
            prod.setPrecioBruto(data.getPrecioBruto());
            prod.setStock(data.getStock());
            prod.setIdProd(tipoProducto);
            prod.setPrecioTotal(data.getPrecioTotal());
            prod.setValorImpuestos(data.getValorImpuestos());
            return productoRepository.save(prod);
        }).orElse(null);
    }

    public void delete(Integer id) {
        productoRepository.deleteById(id);
    }
}
