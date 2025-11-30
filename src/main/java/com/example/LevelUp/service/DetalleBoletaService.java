package com.example.LevelUp.service;

import com.example.LevelUp.controller.DTO.DetalleBoletaPOST;
import com.example.LevelUp.model.*;
import com.example.LevelUp.repository.BoletaRepository;
import com.example.LevelUp.repository.DetalleBoletaRepository;
import com.example.LevelUp.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleBoletaService {
    private  final ProductoRepository productoRepository;
    private final DetalleBoletaRepository detalleBoletaRepository;
    private final BoletaRepository boletaRepository;

    public DetalleBoletaService(ProductoRepository productoRepository, DetalleBoletaRepository detalleBoletaRepository, BoletaRepository boletaRepository) {
        this.productoRepository = productoRepository;
        this.detalleBoletaRepository = detalleBoletaRepository;
        this.boletaRepository = boletaRepository;
    }

    public List<DetalleBoleta> findAll() {
        return detalleBoletaRepository.findAll();
    }

    public Optional<DetalleBoleta> findById(Integer id) {
        return detalleBoletaRepository.findById(id);
    }

    public DetalleBoleta save(DetalleBoletaPOST detalleBoleta) {

        Producto u = productoRepository.findById(detalleBoleta.idProducto)
                .orElseThrow(() -> new RuntimeException("El usuario no existe: " + detalleBoleta.idBoleta));

        Boleta p = boletaRepository.findById(detalleBoleta.idBoleta)
                .orElseThrow(() -> new RuntimeException("la boleta no existe: " + detalleBoleta.idProducto));
        p.setTotalBruto(p.getTotalBruto() + u.getPrecioBruto());
        p.setTotalImpuestos((int) (p.getTotalBruto() * 0.19));
        p.setTotal(p.getTotalImpuestos() + p.getTotalImpuestos());
        DetalleBoleta d = new DetalleBoleta();
        d.setIdBoleta(p);
        d.setIdProducto(u);
        u.setStock(u.getStock() - 1);
        productoRepository.save(u);
        return detalleBoletaRepository.save(d);
    }

    public DetalleBoleta update(Integer id, DetalleBoletaPOST detalleBoleta) {
        return detalleBoletaRepository.findById(id).map(det -> {
            // 1. Buscas las nuevas relaciones (Producto y Boleta)
            Producto nuevoProducto = productoRepository.findById(detalleBoleta.idProducto)
                    .orElseThrow(() -> new RuntimeException("El producto no existe"));

            Boleta nuevaBoleta = boletaRepository.findById(detalleBoleta.idBoleta)
                    .orElseThrow(() -> new RuntimeException("La boleta no existe"));
            Producto Old = det.getIdProducto();
            Boleta OldBoleta = det.getIdBoleta();
            OldBoleta.setTotalBruto(OldBoleta.getTotalBruto() + nuevoProducto.getPrecioBruto() - Old.getPrecioBruto());
            OldBoleta.setTotalImpuestos((int) (OldBoleta.getTotalBruto() *0.19));
            OldBoleta.setTotal(OldBoleta.getTotal() + OldBoleta.getTotalImpuestos());
            det.setIdBoleta(OldBoleta);   // <-- Modificamos 'det', no creamos uno nuevo
            det.setIdProducto(nuevoProducto);
            nuevaBoleta.setTotalBruto(nuevaBoleta.getTotalBruto() );// <-- Modificamos 'det'
            boletaRepository.save(OldBoleta);
            // 3. Guardas 'det' que ahora ya tiene los datos cambiados
            return detalleBoletaRepository.save(det);

        }).orElse(null);
    }

    public void delete(Integer id) {
        detalleBoletaRepository.deleteById(id);
    }
}
