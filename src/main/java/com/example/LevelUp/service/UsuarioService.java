package com.example.LevelUp.service;

import com.example.LevelUp.model.Usuario;
import com.example.LevelUp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private PasswordEncoder encoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired

    private JwtService jwtService;
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
    public Usuario save(String correo, String contrasena, String rol) {
        validateUsuarioFields(correo, contrasena, rol);
        checkCorreoAvailable(correo);

        Usuario user = new Usuario();
        user.setCorreo(correo);
        user.setContrasena(encoder.encode(contrasena));
        user.setRol(rol.toUpperCase());

        return usuarioRepository.save(user);
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public Usuario update(String correo, String nuevaContrasena, String nuevoRol) {
        Usuario existing = getByCorreo(correo);

        if (nuevaContrasena != null && !nuevaContrasena.trim().isEmpty()) {
            existing.setContrasena(encoder.encode(nuevaContrasena));
        }

        if (nuevoRol != null && !nuevoRol.trim().isEmpty()) {
            String r = nuevoRol.toUpperCase();
            if (!r.equals("USER") && !r.equals("ADMIN")) {
                throw new IllegalArgumentException("Rol inválido. Debe ser USER o ADMIN");
            }
            existing.setRol(r);
        }

        return usuarioRepository.save(existing);
    }
    public Usuario getByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }
    private void validateUsuarioFields(String correo, String contrasena, String rol) {
        if (correo == null || correo.trim().isEmpty())
            throw new IllegalArgumentException("El correo es obligatorio.");

        if (contrasena == null || contrasena.trim().isEmpty())
            throw new IllegalArgumentException("La contraseña es obligatoria.");

        if (rol == null || rol.trim().isEmpty())
            throw new IllegalArgumentException("El rol es obligatorio.");

        String normalizedRole = rol.toUpperCase();
        if (!normalizedRole.equals("USER") && !normalizedRole.equals("ADMIN"))
            throw new IllegalArgumentException("El rol debe ser 'USER' o 'ADMIN'.");
    }

    private void checkCorreoAvailable(String correo) {
        if (usuarioRepository.findByCorreo(correo) != null) {
            throw new IllegalArgumentException("El usuario ya existe.");
        }
    }
    public String login(String correo, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(correo, password)
            );
            // Si pasa, generamos JWT
            return jwtService.generateToken(correo);
        } catch (Exception e) {
            return null; // credenciales incorrectas
        }
    }

    public String getRol(String correo) {
        Usuario user = usuarioRepository.findByCorreo(correo);
        if (user == null) return null;
        return user.getRol();
    }
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