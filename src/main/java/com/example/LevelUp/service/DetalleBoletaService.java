package com.example.LevelUp.service;

import com.example.LevelUp.model.DetalleBoleta;
import com.example.LevelUp.repository.DetalleBoletaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleBoletaService {

    private final DetalleBoletaRepository detalleBoletaRepository;

    public DetalleBoletaService(DetalleBoletaRepository detalleBoletaRepository) {
        this.detalleBoletaRepository = detalleBoletaRepository;
    }

    public List<DetalleBoleta> findAll() {
        return detalleBoletaRepository.findAll();
    }

    public Optional<DetalleBoleta> findById(Integer id) {
        return detalleBoletaRepository.findById(id);
    }

    public DetalleBoleta save(DetalleBoleta detalleBoleta) {
        return detalleBoletaRepository.save(detalleBoleta);
    }

    public DetalleBoleta update(Integer id, DetalleBoleta detalles) {
        return detalleBoletaRepository.findById(id).map(det -> {
            det.setIdBoleta(detalles.getIdBoleta());
            det.setIdProducto(detalles.getIdProducto());
            return detalleBoletaRepository.save(det);
        }).orElse(null);
    }

    public void delete(Integer id) {
        detalleBoletaRepository.deleteById(id);
    }
}
