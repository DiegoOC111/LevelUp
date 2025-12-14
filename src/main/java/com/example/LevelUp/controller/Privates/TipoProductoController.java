package com.example.LevelUp.controller.Privates;

import com.example.LevelUp.model.TipoProducto;
import com.example.LevelUp.service.TipoProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipos-productos")
@Tag(
        name = "Tipos de Producto",
        description = "Gestión de tipos o categorías de productos. Acceso de lectura público y modificaciones restringidas a administradores."
)
public class TipoProductoController {

    private final TipoProductoService tipoProductoService;

    public TipoProductoController(TipoProductoService tipoProductoService) {
        this.tipoProductoService = tipoProductoService;
    }

    // ============================================
    // GET ALL
    // ============================================
    @Operation(
            summary = "Obtener todos los tipos de producto",
            description = "Devuelve una lista con todos los tipos o categorías de productos registrados en el sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de tipos de producto",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TipoProducto.class)
                    )
            )
    })
    @GetMapping
    public List<TipoProducto> getAll() {
        return tipoProductoService.findAll();
    }

    // ============================================
    // GET BY ID
    // ============================================
    @Operation(
            summary = "Obtener tipo de producto por ID",
            description = "Busca y devuelve un tipo de producto según su identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Tipo de producto encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TipoProducto.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Ejemplo de tipo de producto",
                                            value = """
                                                    {
                                                      "id": 1,
                                                      "nombre": "Electrónica"
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tipo de producto no encontrado"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<TipoProducto> getById(@PathVariable Integer id) {
        return tipoProductoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ============================================
    // CREATE
    // ============================================
    @Operation(
            summary = "Crear nuevo tipo de producto",
            description = "Crea un nuevo tipo o categoría de producto en el sistema. Requiere rol ADMIN."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Tipo de producto creado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TipoProducto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acceso denegado. Se requiere rol ADMIN"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno al crear el tipo de producto"
            )
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public TipoProducto create(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Datos del nuevo tipo de producto",
                    content = @Content(
                            schema = @Schema(implementation = TipoProducto.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Crear tipo de producto",
                                            value = """
                                                    {
                                                      "nombre": "Ropa"
                                                    }
                                                    """
                                    )
                            }
                    )
            )
            TipoProducto tipoProducto
    ) {
        try {
            return tipoProductoService.save(tipoProducto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ============================================
    // UPDATE
    // ============================================
    @Operation(
            summary = "Actualizar tipo de producto",
            description = "Actualiza los datos de un tipo de producto existente. Requiere rol ADMIN."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Tipo de producto actualizado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TipoProducto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tipo de producto no encontrado"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acceso denegado. Se requiere rol ADMIN"
            )
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TipoProducto> update(
            @PathVariable Integer id,
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Datos actualizados del tipo de producto",
                    content = @Content(
                            schema = @Schema(implementation = TipoProducto.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Actualizar tipo de producto",
                                            value = """
                                                    {
                                                      "nombre": "Hogar"
                                                    }
                                                    """
                                    )
                            }
                    )
            )
            TipoProducto data
    ) {
        TipoProducto updated = tipoProductoService.update(id, data);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    // ============================================
    // DELETE
    // ============================================
    @Operation(
            summary = "Eliminar tipo de producto",
            description = "Elimina un tipo de producto del sistema según su ID. Requiere rol ADMIN."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Tipo de producto eliminado correctamente"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acceso denegado. Se requiere rol ADMIN"
            )
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        tipoProductoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
