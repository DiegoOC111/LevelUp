package com.example.LevelUp;


import com.example.LevelUp.model.Boleta;
import com.example.LevelUp.repository.BoletaRepository;
import com.example.LevelUp.service.BoletaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class LevelUpBoletaTest {

    @Autowired
    BoletaRepository boletaRepository;

    @MockitoBean
    BoletaService boletaService;

    @Test
    @DisplayName("findall test")
    void testBoletaServiceMock() {
        List<Boleta> boleta = boletaRepository.findAll();
        assertNotNull(boleta);
        //se coloca la cantidad de boletas que se espera que se obtengan
        assertEquals(1,boleta.size());
    }

    @Test
    @DisplayName("rectificar monto bruto")
    void testFindPriceBruto() {
        Boleta prueba = boletaRepository.findById(1).get();
        assertNotNull(prueba);
        //se coloca la cantidad bruta de la boleta
        assertEquals(200000, prueba.getTotalBruto());
    }

    @Test
    @DisplayName("rectificar total de boleta")
    void testFindPrice() {
        Boleta prueba = boletaRepository.findById(1).get();
        assertNotNull(prueba);
        //se coloca la cantidad total de la boleta
        assertEquals(1000000, prueba.getTotalBruto());
    }

}
