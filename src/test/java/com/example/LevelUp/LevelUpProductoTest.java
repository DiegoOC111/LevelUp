package com.example.LevelUp;


import com.example.LevelUp.model.DetalleBoleta;
import com.example.LevelUp.model.Producto;
import com.example.LevelUp.repository.DetalleBoletaRepository;
import com.example.LevelUp.repository.ProductoRepository;
import com.example.LevelUp.service.DetalleBoletaService;
import com.example.LevelUp.service.ProductoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class LevelUpProductoTest {

    @Autowired
    ProductoRepository productoRepository;

    @MockitoBean
    ProductoService productoService;

    @Test
    @DisplayName("findall test")
    void testDetalleBoletaServiceMock() {
        List<Producto> productos = productoRepository.findAll();
        assertNotNull(productos);
        //se coloca la cantidad de boletas que se espera que se obtengan
        assertEquals(1,productos.size());
    }
}
