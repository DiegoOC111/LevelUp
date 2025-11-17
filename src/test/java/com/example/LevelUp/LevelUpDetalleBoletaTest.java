package com.example.LevelUp;


import com.example.LevelUp.model.Boleta;
import com.example.LevelUp.model.DetalleBoleta;
import com.example.LevelUp.repository.BoletaRepository;
import com.example.LevelUp.repository.DetalleBoletaRepository;
import com.example.LevelUp.service.BoletaService;
import com.example.LevelUp.service.DetalleBoletaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class LevelUpDetalleBoletaTest {

    @Autowired
    DetalleBoletaRepository detalleBoletaRepository;

    @MockitoBean
    DetalleBoletaService detalleBoletaService;

    @Test
    @DisplayName("findall test")
    void testDetalleBoletaServiceMock() {
        List<DetalleBoleta> detalleBoleta = detalleBoletaRepository.findAll();
        assertNotNull(detalleBoleta);
        //se coloca la cantidad de boletas que se espera que se obtengan
        assertEquals(1,detalleBoleta.size());
    }
}
