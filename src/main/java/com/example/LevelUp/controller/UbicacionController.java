package com.example.LevelUp.controller;

import com.example.LevelUp.model.Ubicacion;
import com.example.LevelUp.service.UbicacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ubicaciones")
public class UbicacionController {

    private final UbicacionService ubicacionService;

    public UbicacionController(UbicacionService ubicacionService) {
        this.ubicacionService = ubicacionService;
    }

    @GetMapping
    public List<Ubicacion> getAll() {
        return ubicacionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ubicacion> getById(@PathVariable Integer id) {
        return ubicacionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Ubicacion create(@RequestBody Ubicacion ubicacion) {
        return ubicacionService.save(ubicacion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ubicacion> update(@PathVariable Integer id, @RequestBody Ubicacion data) {
        Ubicacion updated = ubicacionService.update(id, data);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        ubicacionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
