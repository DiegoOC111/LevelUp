package com.example.LevelUp;


import com.example.LevelUp.model.Ubicacion;
import com.example.LevelUp.model.Usuario;
import com.example.LevelUp.repository.UbicacionRepository;
import com.example.LevelUp.repository.UsuarioRepository;
import com.example.LevelUp.service.UbicacionService;
import com.example.LevelUp.service.UsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class LevelUpUsuarioTest {

    @Autowired
    UsuarioRepository usuarioRepository;

    @MockitoBean
    UsuarioService usuarioService;

    @Test
    @DisplayName("findall test")
    void testUbicacionServiceMock() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        assertNotNull(usuarios);
        //se coloca la cantidad de boletas que se espera que se obtengan
        assertEquals(1,usuarios.size());
    }
}
