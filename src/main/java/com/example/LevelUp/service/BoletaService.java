package com.example.LevelUp.service;

import com.example.LevelUp.controller.DTO.BoletaPOST;
import com.example.LevelUp.model.Boleta;
import com.example.LevelUp.model.TipoDespacho;
import com.example.LevelUp.model.Usuario;
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

        // Validar que el usuario exista
        Usuario u = usuarioRepository.findById(boleta.usuarioIdUsuario)
                .orElseThrow(() -> new RuntimeException("El usuario no existe: " + boleta.usuarioIdUsuario));

        // Validar que el tipo de despacho exista
        TipoDespacho tipoDespacho = tipoDespachoRepository.findById(boleta.idDespacho)
                .orElseThrow(() -> new RuntimeException("El tipo de despacho no existe: " + boleta.idDespacho));

        Boleta aux = new Boleta();
        aux.setUsuarioIdUsuario(u);
        aux.setIdDespacho(tipoDespacho);
        aux.setTotal(boleta.total);
        aux.setTotalBruto(boleta.totalBruto);
        aux.setTotalImpuestos(boleta.totalImpuestos);

        return boletaRepository.save(aux);
    }

    public Boleta update(Integer id, BoletaPOST boleta1) {
        return boletaRepository.findById(id).map(boleta -> {

            Usuario u = usuarioRepository.findById(boleta1.usuarioIdUsuario)
                    .orElseThrow(() -> new RuntimeException("El usuario no existe: " + boleta1.usuarioIdUsuario));

            TipoDespacho tipoDespacho = tipoDespachoRepository.findById(boleta1.idDespacho)
                    .orElseThrow(() -> new RuntimeException("El tipo de despacho no existe: " + boleta1.idDespacho));

            boleta.setUsuarioIdUsuario(u);
            boleta.setIdDespacho(tipoDespacho);
            boleta.setTotal(boleta1.total);
            boleta.setTotalBruto(boleta1.totalBruto);
            boleta.setTotalImpuestos(boleta1.totalImpuestos);

            return boletaRepository.save(boleta);

        }).orElse(null);

    }

    public void delete(Integer id) {
        boletaRepository.deleteById(id);
    }
}
