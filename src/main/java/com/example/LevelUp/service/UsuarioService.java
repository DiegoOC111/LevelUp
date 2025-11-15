package com.example.LevelUp.service;

import com.example.LevelUp.model.Usuario;
import com.example.LevelUp.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Integer id) {
        return usuarioRepository.findById(id);
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario update(Integer id, Usuario data) {
        return usuarioRepository.findById(id).map(u -> {
            u.setCorreo(data.getCorreo());
            u.setContrasena(data.getContrasena());
            u.setRol(data.getRol());
            return usuarioRepository.save(u);
        }).orElse(null);
    }

    public void delete(Integer id) {
        usuarioRepository.deleteById(id);
    }
}
