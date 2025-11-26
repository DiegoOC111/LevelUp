package com.example.LevelUp.security;

import com.example.LevelUp.model.Usuario;
import com.example.LevelUp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UsuarioRepository userRespository;
    
    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario user = userRespository.findByCorreo(correo);
        if (user == null) throw new UsernameNotFoundException("Usuario no encontrado");
        return new  org.springframework.security.core.userdetails.User(
                user.getCorreo(),
                user.getContrasena(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRol()))
        );
    }
}
