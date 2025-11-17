package com.example.LevelUp;


import com.example.LevelUp.model.TipoDespacho;
import com.example.LevelUp.model.TipoProducto;
import com.example.LevelUp.repository.TipoDespachoRepository;
import com.example.LevelUp.repository.TipoProductoRepository;
import com.example.LevelUp.service.TipoDespachoService;
import com.example.LevelUp.service.TipoProductoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class LevelUpTipoProductoTest {

    @Autowired
    TipoProductoRepository tipoProductoRepository;

    @MockitoBean
    TipoProductoService tipoProductoService;

    @Test
    @DisplayName("findall test")
    void testTipoProductoServiceMock() {
        List<TipoProducto> tipoProductos = tipoProductoRepository.findAll();
        assertNotNull(tipoProductos);
        //se coloca la cantidad de boletas que se espera que se obtengan
        assertEquals(1,tipoProductos.size());
    }
}
