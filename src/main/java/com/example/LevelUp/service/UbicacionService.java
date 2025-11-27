package com.example.LevelUp.service;

import com.example.LevelUp.controller.DTO.UbicacionPOST;
import com.example.LevelUp.model.Ubicacion;
import com.example.LevelUp.model.Usuario;
import com.example.LevelUp.repository.UbicacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UbicacionService {
    private final UsuarioService usuarioService;

    private final UbicacionRepository ubicacionRepository;

    public UbicacionService(UsuarioService usuarioService, UbicacionRepository ubicacionRepository) {
        this.usuarioService = usuarioService;
        this.ubicacionRepository = ubicacionRepository;
    }

    public List<Ubicacion> findAll() {
        return ubicacionRepository.findAll();
    }

    public Optional<Ubicacion> findById(Integer id) {
        return ubicacionRepository.findById(id);
    }

    public Ubicacion save(UbicacionPOST ubicacion) {

        if (ubicacion.calle == null || ubicacion.calle.trim().isEmpty()) {
            throw new IllegalArgumentException("La calle no puede estar vacía");
        }

        if (ubicacion.numero == null || ubicacion.numero <= 0) {
            throw new IllegalArgumentException("El número de calle es inválido");
        }

        if (ubicacion.comuna == null || ubicacion.comuna.trim().isEmpty()) {
            throw new IllegalArgumentException("La comuna no puede estar vacía");
        }

        if (ubicacion.region == null || ubicacion.region.trim().isEmpty()) {
            throw new IllegalArgumentException("La región no puede estar vacía");
        }

        if (ubicacion.idUsuario == null) {
            throw new IllegalArgumentException("El usuario es obligatorio");
        }

        Optional<Usuario> userOpt = usuarioService.findById(ubicacion.idUsuario);

        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("El usuario no existe");
        }

        Usuario usuario = userOpt.get();

        Ubicacion u = new Ubicacion();
        u.setCalle(ubicacion.calle);
        u.setNumero(ubicacion.numero);
        u.setComuna(ubicacion.comuna);
        u.setRegion(ubicacion.region);
        u.setIdUsuario(usuario);

        return ubicacionRepository.save(u);
    }

    public Ubicacion update(Integer id, UbicacionPOST ubicacion) {
        return ubicacionRepository.findById(id).map(u -> {
            if (ubicacion.calle == null || ubicacion.calle.trim().isEmpty()) {
                throw new IllegalArgumentException("La calle no puede estar vacía");
            }

            if (ubicacion.numero == null || ubicacion.numero <= 0) {
                throw new IllegalArgumentException("El número de calle es inválido");
            }

            if (ubicacion.comuna == null || ubicacion.comuna.trim().isEmpty()) {
                throw new IllegalArgumentException("La comuna no puede estar vacía");
            }

            if (ubicacion.region == null || ubicacion.region.trim().isEmpty()) {
                throw new IllegalArgumentException("La región no puede estar vacía");
            }

            if (ubicacion.idUsuario == null) {
                throw new IllegalArgumentException("El usuario es obligatorio");
            }

            Optional<Usuario> userOpt = usuarioService.findById(ubicacion.idUsuario);

            if (userOpt.isEmpty()) {
                throw new IllegalArgumentException("El usuario no existe");
            }

            Usuario usuario = userOpt.get();
            u.setCalle(ubicacion.calle);
            u.setNumero(ubicacion.numero);
            u.setComuna(ubicacion.comuna);
            u.setRegion(ubicacion.region);
            u.setIdUsuario(usuario);
            return ubicacionRepository.save(u);
        }).orElse(null);
    }

    public void delete(Integer id) {
        ubicacionRepository.deleteById(id);
    }
}
