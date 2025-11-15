package com.example.LevelUp.controller;

import com.example.LevelUp.model.TipoProducto;
import com.example.LevelUp.service.TipoProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipos-producto")
public class TipoProductoController {

    private final TipoProductoService tipoProductoService;

    public TipoProductoController(TipoProductoService tipoProductoService) {
        this.tipoProductoService = tipoProductoService;
    }

    @GetMapping
    public List<TipoProducto> getAll() {
        return tipoProductoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoProducto> getById(@PathVariable Integer id) {
        return tipoProductoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public TipoProducto create(@RequestBody TipoProducto tipoProducto) {
        return tipoProductoService.save(tipoProducto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoProducto> update(@PathVariable Integer id, @RequestBody TipoProducto data) {
        TipoProducto updated = tipoProductoService.update(id, data);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        tipoProductoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
