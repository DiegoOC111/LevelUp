package com.example.LevelUp.service;

import com.example.LevelUp.controller.DTO.DetalleBoletaPOST;
import com.example.LevelUp.model.*;
import com.example.LevelUp.repository.BoletaRepository;
import com.example.LevelUp.repository.DetalleBoletaRepository;
import com.example.LevelUp.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleBoletaService {
    private  final ProductoRepository productoRepository;
    private final DetalleBoletaRepository detalleBoletaRepository;
    private final BoletaRepository boletaRepository;

    public DetalleBoletaService(ProductoRepository productoRepository, DetalleBoletaRepository detalleBoletaRepository, BoletaRepository boletaRepository) {
        this.productoRepository = productoRepository;
        this.detalleBoletaRepository = detalleBoletaRepository;
        this.boletaRepository = boletaRepository;
    }

    public List<DetalleBoleta> findAll() {
        return detalleBoletaRepository.findAll();
    }

    public Optional<DetalleBoleta> findById(Integer id) {
        return detalleBoletaRepository.findById(id);
    }

    public DetalleBoleta save(DetalleBoletaPOST detalleBoleta) {

        Producto u = productoRepository.findById(detalleBoleta.idProducto)
                .orElseThrow(() -> new RuntimeException("El usuario no existe: " + detalleBoleta.idBoleta));

        Boleta p = boletaRepository.findById(detalleBoleta.idBoleta)
                .orElseThrow(() -> new RuntimeException("la boleta no existe: " + detalleBoleta.idProducto));
        DetalleBoleta d = new DetalleBoleta();
        d.setIdBoleta(p);
        d.setIdProducto(u);
        return detalleBoletaRepository.save(d);
    }

    public DetalleBoleta update(Integer id, DetalleBoletaPOST detalleBoleta) {
        return detalleBoletaRepository.findById(id).map(det -> {
            Producto u = productoRepository.findById(detalleBoleta.idProducto)
                    .orElseThrow(() -> new RuntimeException("El usuario no existe: " + detalleBoleta.idBoleta));

            Boleta p = boletaRepository.findById(detalleBoleta.idBoleta)
                    .orElseThrow(() -> new RuntimeException("la boleta no existe: " + detalleBoleta.idProducto));
            DetalleBoleta d = new DetalleBoleta();
            d.setIdBoleta(p);
            d.setIdProducto(u);
            return detalleBoletaRepository.save(det);
        }).orElse(null);
    }

    public void delete(Integer id) {
        detalleBoletaRepository.deleteById(id);
    }
}
