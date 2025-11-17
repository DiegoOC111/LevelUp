package com.example.LevelUp;


import com.example.LevelUp.model.TipoProducto;
import com.example.LevelUp.model.Ubicacion;
import com.example.LevelUp.repository.TipoProductoRepository;
import com.example.LevelUp.repository.UbicacionRepository;
import com.example.LevelUp.service.TipoProductoService;
import com.example.LevelUp.service.UbicacionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class LevelUpUbicacionTest {

    @Autowired
    UbicacionRepository ubicacionRepository;

    @MockitoBean
    UbicacionService ubicacionService;

    @Test
    @DisplayName("findall test")
    void testDetalleBoletaServiceMock() {
        List<Ubicacion> ubicaciones = ubicacionRepository.findAll();
        assertNotNull(ubicaciones);
        //se coloca la cantidad de boletas que se espera que se obtengan
        assertEquals(1,ubicaciones.size());
    }
}
