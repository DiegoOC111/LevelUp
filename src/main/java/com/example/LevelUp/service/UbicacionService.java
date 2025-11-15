package com.example.LevelUp.service;

import com.example.LevelUp.model.Ubicacion;
import com.example.LevelUp.repository.UbicacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UbicacionService {

    private final UbicacionRepository ubicacionRepository;

    public UbicacionService(UbicacionRepository ubicacionRepository) {
        this.ubicacionRepository = ubicacionRepository;
    }

    public List<Ubicacion> findAll() {
        return ubicacionRepository.findAll();
    }

    public Optional<Ubicacion> findById(Integer id) {
        return ubicacionRepository.findById(id);
    }

    public Ubicacion save(Ubicacion ubicacion) {
        return ubicacionRepository.save(ubicacion);
    }

    public Ubicacion update(Integer id, Ubicacion data) {
        return ubicacionRepository.findById(id).map(u -> {
            u.setCalle(data.getCalle());
            u.setNumero(data.getNumero());
            u.setComuna(data.getComuna());
            u.setRegion(data.getRegion());
            u.setIdUsuario(data.getIdUsuario());
            return ubicacionRepository.save(u);
        }).orElse(null);
    }

    public void delete(Integer id) {
        ubicacionRepository.deleteById(id);
    }
}
