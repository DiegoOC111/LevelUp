package com.example.LevelUp.service;

import com.example.LevelUp.model.TipoDespacho;
import com.example.LevelUp.repository.TipoDespachoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoDespachoService {

    private final TipoDespachoRepository tipoDespachoRepository;

    public TipoDespachoService(TipoDespachoRepository tipoDespachoRepository) {
        this.tipoDespachoRepository = tipoDespachoRepository;
    }

    public List<TipoDespacho> findAll() {
        return tipoDespachoRepository.findAll();
    }

    public Optional<TipoDespacho> findById(Integer id) {
        return tipoDespachoRepository.findById(id);
    }

    public TipoDespacho save(TipoDespacho tipoDespacho) {
        return tipoDespachoRepository.save(tipoDespacho);
    }

    public TipoDespacho update(Integer id, TipoDespacho data) {
        return tipoDespachoRepository.findById(id).map(td -> {
            td.setNombreDespacho(data.getNombreDespacho());
            return tipoDespachoRepository.save(td);
        }).orElse(null);
    }

    public void delete(Integer id) {
        tipoDespachoRepository.deleteById(id);
    }
}
