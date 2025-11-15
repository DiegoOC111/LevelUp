package com.example.LevelUp.controller;

import com.example.LevelUp.model.DetalleBoleta;
import com.example.LevelUp.service.DetalleBoletaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/detalle-boletas")
public class DetalleBoletaController {

    private final DetalleBoletaService detalleBoletaService;

    public DetalleBoletaController(DetalleBoletaService detalleBoletaService) {
        this.detalleBoletaService = detalleBoletaService;
    }

    @GetMapping
    public List<DetalleBoleta> getAll() {
        return detalleBoletaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleBoleta> getById(@PathVariable Integer id) {
        return detalleBoletaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public DetalleBoleta create(@RequestBody DetalleBoleta detalleBoleta) {
        return detalleBoletaService.save(detalleBoleta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleBoleta> update(@PathVariable Integer id, @RequestBody DetalleBoleta detalles) {
        DetalleBoleta updated = detalleBoletaService.update(id, detalles);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        detalleBoletaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
