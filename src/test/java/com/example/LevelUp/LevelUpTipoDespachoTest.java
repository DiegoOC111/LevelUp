package com.example.LevelUp;


import com.example.LevelUp.model.Producto;
import com.example.LevelUp.model.TipoDespacho;
import com.example.LevelUp.repository.ProductoRepository;
import com.example.LevelUp.repository.TipoDespachoRepository;
import com.example.LevelUp.service.ProductoService;
import com.example.LevelUp.service.TipoDespachoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class LevelUpTipoDespachoTest {

    @Autowired
    TipoDespachoRepository tipoDespachoRepository;

    @MockitoBean
    TipoDespachoService tipoDespachoService;

    @Test
    @DisplayName("findall test")
    void testTipoDespachoServiceMock() {
        List<TipoDespacho> tipoDespachos = tipoDespachoRepository.findAll();
        assertNotNull(tipoDespachos);
        //se coloca la cantidad de boletas que se espera que se obtengan
        assertEquals(1,tipoDespachos.size());
    }
}
