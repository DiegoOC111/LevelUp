package com.example.LevelUp.service;

import com.example.LevelUp.model.Boleta;
import com.example.LevelUp.repository.BoletaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoletaService {

    private final BoletaRepository boletaRepository;

    public BoletaService(BoletaRepository boletaRepository) {
        this.boletaRepository = boletaRepository;
    }

    public List<Boleta> findAll() {
        return boletaRepository.findAll();
    }

    public Optional<Boleta> findById(Integer id) {
        return boletaRepository.findById(id);
    }

    public Boleta save(Boleta boleta) {
        return boletaRepository.save(boleta);
    }

    public Boleta update(Integer id, Boleta boletaDetails) {
        return boletaRepository.findById(id).map(boleta -> {
            boleta.setTotalBruto(boletaDetails.getTotalBruto());
            boleta.setTotalImpuestos(boletaDetails.getTotalImpuestos());
            boleta.setTotal(boletaDetails.getTotal());
            boleta.setUsuarioIdUsuario(boletaDetails.getUsuarioIdUsuario());
            boleta.setIdDespacho(boletaDetails.getIdDespacho());
            return boletaRepository.save(boleta);
        }).orElse(null);
    }

    public void delete(Integer id) {
        boletaRepository.deleteById(id);
    }
}
