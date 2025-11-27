package com.example.LevelUp.controller.Privates;

import com.example.LevelUp.controller.DTO.DetalleBoletaPOST;
import com.example.LevelUp.model.DetalleBoleta;
import com.example.LevelUp.service.DetalleBoletaService;
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
@RequestMapping("/detalle-boletas")
@Tag(
        name = "Detalle de Boletas",
        description = "Gestión de los detalles asociados a boletas de venta. Consulta general y modificación restringida a administradores."
)
public class DetalleBoletaController {

    private final DetalleBoletaService detalleBoletaService;

    public DetalleBoletaController(DetalleBoletaService detalleBoletaService) {
        this.detalleBoletaService = detalleBoletaService;
    }

    // ============================================
    // GET ALL
    // ============================================
    @Operation(
            summary = "Obtener todos los detalles de boleta",
            description = "Devuelve una lista con todos los detalles de boletas registrados en el sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de detalles de boleta",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DetalleBoleta.class)
                    )
            )
    })
    @GetMapping
    public List<DetalleBoleta> getAll() {
        return detalleBoletaService.findAll();
    }

    // ============================================
    // GET BY ID
    // ============================================
    @Operation(
            summary = "Obtener detalle de boleta por ID",
            description = "Busca y devuelve un detalle de boleta específico según su identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Detalle de boleta encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DetalleBoleta.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Ejemplo de detalle de boleta",
                                            value = """
                                                    {
                                                      "id": 1,
                                                      "cantidad": 2,
                                                      "precioUnitario": 19990
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Detalle de boleta no encontrado"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<DetalleBoleta> getById(@PathVariable Integer id) {
        return detalleBoletaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ============================================
    // CREATE
    // ============================================
    @Operation(
            summary = "Crear detalle de boleta",
            description = "Crea un nuevo detalle asociado a una boleta de venta."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Detalle de boleta creado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DetalleBoleta.class)
                    )
            )
    })
    @PostMapping
    public DetalleBoleta create(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Datos del detalle de la boleta",
                    content = @Content(
                            schema = @Schema(implementation = DetalleBoleta.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Crear detalle de boleta",
                                            value = """
                                                    {
                                                      "cantidad": 3,
                                                      "precioUnitario": 15990
                                                    }
                                                    """
                                    )
                            }
                    )
            )
            DetalleBoletaPOST detalleBoleta
    ) {
        return detalleBoletaService.save(detalleBoleta);
    }

    // ============================================
    // UPDATE
    // ============================================
    @Operation(
            summary = "Actualizar detalle de boleta",
            description = "Actualiza un detalle de boleta existente. Requiere rol ADMIN."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Detalle de boleta actualizado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DetalleBoleta.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Detalle de boleta no encontrado"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acceso denegado. Se requiere rol ADMIN"
            )
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DetalleBoleta> update(
            @PathVariable Integer id,
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Datos actualizados del detalle de boleta",
                    content = @Content(
                            schema = @Schema(implementation = DetalleBoleta.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Actualizar detalle de boleta",
                                            value = """
                                                    {
                                                      "cantidad": 5,
                                                      "precioUnitario": 14990
                                                    }
                                                    """
                                    )
                            }
                    )
            )
            DetalleBoletaPOST detalles
    ) {
        DetalleBoleta updated = detalleBoletaService.update(id, detalles);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    // ============================================
    // DELETE
    // ============================================
    @Operation(
            summary = "Eliminar detalle de boleta",
            description = "Elimina un detalle de boleta del sistema según su ID. Requiere rol ADMIN."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Detalle de boleta eliminado correctamente"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acceso denegado. Se requiere rol ADMIN"
            )
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        detalleBoletaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
