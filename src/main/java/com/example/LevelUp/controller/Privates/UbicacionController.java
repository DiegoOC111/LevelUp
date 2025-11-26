package com.example.LevelUp.controller.Privates;

import com.example.LevelUp.model.Ubicacion;
import com.example.LevelUp.service.UbicacionService;
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
@RequestMapping("/ubicaciones")
@Tag(
        name = "Ubicaciones",
        description = "Gestión de ubicaciones del sistema. Consulta pública y modificación restringida a administradores."
)
public class UbicacionController {

    private final UbicacionService ubicacionService;

    public UbicacionController(UbicacionService ubicacionService) {
        this.ubicacionService = ubicacionService;
    }

    // ============================================
    // GET ALL
    // ============================================
    @Operation(
            summary = "Obtener todas las ubicaciones",
            description = "Devuelve una lista con todas las ubicaciones registradas en el sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de ubicaciones",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Ubicacion.class)
                    )
            )
    })
    @GetMapping
    public List<Ubicacion> getAll() {
        return ubicacionService.findAll();
    }

    // ============================================
    // GET BY ID
    // ============================================
    @Operation(
            summary = "Obtener ubicación por ID",
            description = "Busca y devuelve una ubicación específica según su identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Ubicación encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Ubicacion.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Ejemplo de ubicación",
                                            value = """
                                                    {
                                                      "id": 1,
                                                      "nombre": "Sucursal Central"
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ubicación no encontrada"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Ubicacion> getById(@PathVariable Integer id) {
        return ubicacionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ============================================
    // CREATE
    // ============================================
    @Operation(
            summary = "Crear nueva ubicación",
            description = "Crea una nueva ubicación en el sistema. Requiere rol ADMIN."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Ubicación creada exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Ubicacion.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acceso denegado. Se requiere rol ADMIN"
            )
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Ubicacion create(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Datos de la nueva ubicación",
                    content = @Content(
                            schema = @Schema(implementation = Ubicacion.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Crear ubicación",
                                            value = """
                                                    {
                                                      "nombre": "Sucursal Norte"
                                                    }
                                                    """
                                    )
                            }
                    )
            )
            Ubicacion ubicacion
    ) {
        return ubicacionService.save(ubicacion);
    }

    // ============================================
    // UPDATE
    // ============================================
    @Operation(
            summary = "Actualizar ubicación",
            description = "Actualiza los datos de una ubicación existente. Requiere rol ADMIN."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Ubicación actualizada exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Ubicacion.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ubicación no encontrada"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acceso denegado. Se requiere rol ADMIN"
            )
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Ubicacion> update(
            @PathVariable Integer id,
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Datos actualizados de la ubicación",
                    content = @Content(
                            schema = @Schema(implementation = Ubicacion.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Actualizar ubicación",
                                            value = """
                                                    {
                                                      "nombre": "Sucursal Sur"
                                                    }
                                                    """
                                    )
                            }
                    )
            )
            Ubicacion data
    ) {
        Ubicacion updated = ubicacionService.update(id, data);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    // ============================================
    // DELETE
    // ============================================
    @Operation(
            summary = "Eliminar ubicación",
            description = "Elimina una ubicación del sistema según su ID. Requiere rol ADMIN."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Ubicación eliminada correctamente"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acceso denegado. Se requiere rol ADMIN"
            )
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        ubicacionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
