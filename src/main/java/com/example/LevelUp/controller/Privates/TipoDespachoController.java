package com.example.LevelUp.controller.Privates;

import com.example.LevelUp.model.TipoDespacho;
import com.example.LevelUp.service.TipoDespachoService;
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
@RequestMapping("/tipos-despacho")
@Tag(
        name = "Tipos de Despacho",
        description = "Gestión de tipos de despacho o métodos de envío. Lectura pública y modificaciones restringidas a administradores."
)
public class TipoDespachoController {

    private final TipoDespachoService tipoDespachoService;

    public TipoDespachoController(TipoDespachoService tipoDespachoService) {
        this.tipoDespachoService = tipoDespachoService;
    }

    // ============================================
    // GET ALL
    // ============================================
    @Operation(
            summary = "Obtener todos los tipos de despacho",
            description = "Devuelve una lista con todos los tipos de despacho registrados en el sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de tipos de despacho",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TipoDespacho.class)
                    )
            )
    })
    @GetMapping
    public List<TipoDespacho> getAll() {
        return tipoDespachoService.findAll();
    }

    // ============================================
    // GET BY ID
    // ============================================
    @Operation(
            summary = "Obtener tipo de despacho por ID",
            description = "Busca y devuelve un tipo de despacho específico según su identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Tipo de despacho encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TipoDespacho.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Ejemplo de tipo de despacho",
                                            value = """
                                                    {
                                                      "id": 1,
                                                      "nombre": "Despacho a domicilio"
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tipo de despacho no encontrado"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<TipoDespacho> getById(@PathVariable Integer id) {
        return tipoDespachoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ============================================
    // CREATE
    // ============================================
    @Operation(
            summary = "Crear nuevo tipo de despacho",
            description = "Crea un nuevo tipo de despacho o método de envío. Requiere rol ADMIN."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Tipo de despacho creado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TipoDespacho.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acceso denegado. Se requiere rol ADMIN"
            )
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public TipoDespacho create(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Datos del nuevo tipo de despacho",
                    content = @Content(
                            schema = @Schema(implementation = TipoDespacho.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Crear tipo de despacho",
                                            value = """
                                                    {
                                                      "nombre": "Retiro en tienda"
                                                    }
                                                    """
                                    )
                            }
                    )
            )
            TipoDespacho tipoDespacho
    ) {
        return tipoDespachoService.save(tipoDespacho);
    }

    // ============================================
    // UPDATE
    // ============================================
    @Operation(
            summary = "Actualizar tipo de despacho",
            description = "Actualiza los datos de un tipo de despacho existente. Requiere rol ADMIN."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Tipo de despacho actualizado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TipoDespacho.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tipo de despacho no encontrado"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acceso denegado. Se requiere rol ADMIN"
            )
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TipoDespacho> update(
            @PathVariable Integer id,
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Datos actualizados del tipo de despacho",
                    content = @Content(
                            schema = @Schema(implementation = TipoDespacho.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Actualizar tipo de despacho",
                                            value = """
                                                    {
                                                      "nombre": "Envío express"
                                                    }
                                                    """
                                    )
                            }
                    )
            )
            TipoDespacho data
    ) {
        TipoDespacho updated = tipoDespachoService.update(id, data);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    // ============================================
    // DELETE
    // ============================================
    @Operation(
            summary = "Eliminar tipo de despacho",
            description = "Elimina un tipo de despacho del sistema según su ID. Requiere rol ADMIN."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Tipo de despacho eliminado correctamente"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acceso denegado. Se requiere rol ADMIN"
            )
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        tipoDespachoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
