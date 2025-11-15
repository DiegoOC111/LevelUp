package com.example.LevelUp.controller;

import com.example.LevelUp.model.TipoDespacho;
import com.example.LevelUp.service.TipoDespachoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipos-despacho")
public class TipoDespachoController {

    private final TipoDespachoService tipoDespachoService;

    public TipoDespachoController(TipoDespachoService tipoDespachoService) {
        this.tipoDespachoService = tipoDespachoService;
    }

    @GetMapping
    public List<TipoDespacho> getAll() {
        return tipoDespachoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoDespacho> getById(@PathVariable Integer id) {
        return tipoDespachoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public TipoDespacho create(@RequestBody TipoDespacho tipoDespacho) {
        return tipoDespachoService.save(tipoDespacho);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoDespacho> update(@PathVariable Integer id, @RequestBody TipoDespacho data) {
        TipoDespacho updated = tipoDespachoService.update(id, data);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        tipoDespachoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
