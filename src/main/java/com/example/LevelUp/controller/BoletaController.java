package com.example.LevelUp.controller;

import com.example.LevelUp.model.Boleta;
import com.example.LevelUp.service.BoletaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boletas")
public class BoletaController {

    private final BoletaService boletaService;

    public BoletaController(BoletaService boletaService) {
        this.boletaService = boletaService;
    }

    @GetMapping
    public List<Boleta> getAll() {
        return boletaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Boleta> getById(@PathVariable Integer id) {
        return boletaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Boleta create(@RequestBody Boleta boleta) {
        return boletaService.save(boleta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boleta> update(@PathVariable Integer id, @RequestBody Boleta boletaDetails) {
        Boleta updated = boletaService.update(id, boletaDetails);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        boletaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
