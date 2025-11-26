package com.example.LevelUp.controller.Privates;

import com.example.LevelUp.model.Boleta;
import com.example.LevelUp.service.BoletaService;
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
@RequestMapping("/boletas")
@Tag(
        name = "Boletas",
        description = "Gestión de boletas de venta. Consulta general y acciones administrativas según rol."
)
public class BoletaController {

    private final BoletaService boletaService;

    public BoletaController(BoletaService boletaService) {
        this.boletaService = boletaService;
    }

    // ============================================
    // GET ALL
    // ============================================
    @Operation(
            summary = "Obtener todas las boletas",
            description = "Devuelve una lista con todas las boletas registradas en el sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de boletas",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Boleta.class)
                    )
            )
    })
    @GetMapping
    public List<Boleta> getAll() {
        return boletaService.findAll();
    }

    // ============================================
    // GET BY ID
    // ============================================
    @Operation(
            summary = "Obtener boleta por ID",
            description = "Devuelve el detalle de una boleta específica. Requiere rol USER."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Boleta encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Boleta.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Ejemplo de boleta",
                                            value = """
                                                    {
                                                      "id": 10,
                                                      "total": 55980,
                                                      "fecha": "2025-11-25"
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Boleta no encontrada"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acceso denegado. Se requiere rol USER"
            )
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Boleta> getById(@PathVariable Integer id) {
        return boletaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ============================================
    // CREATE
    // ============================================
    @Operation(
            summary = "Crear nueva boleta",
            description = "Registra una nueva boleta de venta en el sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Boleta creada exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Boleta.class)
                    )
            )
    })
    @PostMapping
    public Boleta create(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Datos de la boleta",
                    content = @Content(
                            schema = @Schema(implementation = Boleta.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Crear boleta",
                                            value = """
                                                    {
                                                      "total": 45990,
                                                      "fecha": "2025-11-25"
                                                    }
                                                    """
                                    )
                            }
                    )
            )
            Boleta boleta
    ) {
        return boletaService.save(boleta);
    }

    // ============================================
    // UPDATE
    // ============================================
    @Operation(
            summary = "Actualizar boleta",
            description = "Actualiza una boleta existente. Requiere rol ADMIN."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Boleta actualizada exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Boleta.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Boleta no encontrada"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acceso denegado. Se requiere rol ADMIN"
            )
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boleta> update(
            @PathVariable Integer id,
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Datos actualizados de la boleta",
                    content = @Content(
                            schema = @Schema(implementation = Boleta.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Actualizar boleta",
                                            value = """
                                                    {
                                                      "total": 59990,
                                                      "fecha": "2025-11-26"
                                                    }
                                                    """
                                    )
                            }
                    )
            )
            Boleta boletaDetails
    ) {
        Boleta updated = boletaService.update(id, boletaDetails);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    // ============================================
    // DELETE
    // ============================================
    @Operation(
            summary = "Eliminar boleta",
            description = "Elimina una boleta del sistema según su ID. Requiere rol ADMIN."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Boleta eliminada correctamente"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acceso denegado. Se requiere rol ADMIN"
            )
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        boletaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
