package com.example.LevelUp.service;

import com.example.LevelUp.controller.DTO.BoletaPOST;
import com.example.LevelUp.model.Boleta;
import com.example.LevelUp.repository.BoletaRepository;
import com.example.LevelUp.repository.TipoDespachoRepository;
import com.example.LevelUp.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoletaService {

    private final TipoDespachoRepository tipoDespachoRepository;
    private final UsuarioRepository usuarioRepository;
    private final BoletaRepository boletaRepository;

    public BoletaService(TipoDespachoRepository tipoDespachoRepository, BoletaRepository boletaRepository, UsuarioRepository usuarioRepository) {
        this.tipoDespachoRepository = tipoDespachoRepository;
        this.usuarioRepository = usuarioRepository;
        this.boletaRepository = boletaRepository;
    }

    public List<Boleta> findAll() {
        return boletaRepository.findAll();
    }

    public Optional<Boleta> findById(Integer id) {
        return boletaRepository.findById(id);
    }

    public Boleta save(BoletaPOST boleta) {

        Boleta aux = new Boleta();
        aux.setIdDespacho(tipoDespachoRepository.getReferenceById(boleta.idDespacho));
        aux.setTotal(boleta.total);
        aux.setUsuarioIdUsuario(usuarioRepository.getReferenceById(boleta.usuarioIdUsuario));
        aux.setTotalBruto(boleta.totalBruto);
        aux.setTotalImpuestos(boleta.totalImpuestos);

        return boletaRepository.save(aux);
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
