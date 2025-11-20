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


    //Metodo create
    public Usuario save(Usuario usuario) {
        if (usuario.getCorreo() == null || usuario.getCorreo().trim().isEmpty() ||
            usuario.getContrasena() == null || usuario.getContrasena().isEmpty()){
            throw new IllegalArgumentException("debe ingresar un correo y contraseña");
        }

        List<Usuario> todosLosUsuarios = usuarioRepository.findAll();
        boolean correoExistente = todosLosUsuarios.stream().anyMatch(u -> u.getCorreo().equals(usuario.getCorreo()));

        if (correoExistente) {
            throw new IllegalStateException("ya existe el usuario" + usuario.getCorreo());
        }

        if (!isValidEmail(usuario.getCorreo())) {
            throw new IllegalArgumentException("formato de correo incorrecto");
        }

        return usuarioRepository.save(usuario);
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    //Metodo update
    public Usuario update(Integer id, Usuario data) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID de usuario inválido");
        }
        return usuarioRepository.findById(id).map(usuarioExistente -> {

            if (data.getCorreo() == null || data.getCorreo().trim().isEmpty()) {
                throw new IllegalArgumentException("El correo no puede estar vacío");
            }
            if (!usuarioExistente.getCorreo().equals(data.getCorreo())) {
                List<Usuario> todosLosUsuarios = usuarioRepository.findAll();

                boolean correoYaEnUso = todosLosUsuarios.stream()
                        .anyMatch(u -> u.getCorreo().equalsIgnoreCase(data.getCorreo()) &&
                                u.getId() != id);

                if (correoYaEnUso) {
                    throw new IllegalStateException("El nuevo correo ya está en uso por otro usuario.");
                }
            }
            if (!isValidEmail(data.getCorreo())) {
                throw new IllegalArgumentException("El formato del correo electrónico es inválido.");
            }
            usuarioExistente.setCorreo(data.getCorreo());
            usuarioExistente.setContrasena(data.getContrasena());
            usuarioExistente.setRol(data.getRol());

            return usuarioRepository.save(usuarioExistente);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    //metodo delete
    public void delete(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID de usuario inválido");
        }
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }

        usuarioRepository.deleteById(id);
    }
}
