package com.example.LevelUp.controller.Privates;

import com.example.LevelUp.model.Producto;
import com.example.LevelUp.service.ProductoService;
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
@RequestMapping("/productos")
@Tag(
        name = "Productos",
        description = "Gestión de productos del sistema. Consulta pública y modificaciones restringidas a administradores."
)
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // ============================================
    // GET ALL
    // ============================================
    @Operation(
            summary = "Obtener todos los productos",
            description = "Devuelve una lista con todos los productos registrados en el sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de productos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Producto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno al obtener los productos"
            )
    })
    @GetMapping
    public List<Producto> getAll() {
        try {
            return productoService.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ============================================
    // GET BY ID
    // ============================================
    @Operation(
            summary = "Obtener producto por ID",
            description = "Busca y devuelve un producto específico según su identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Producto encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Producto.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Ejemplo de producto",
                                            value = """
                                                    {
                                                      "id": 1,
                                                      "nombre": "Mouse Gamer",
                                                      "precio": 19990,
                                                      "stock": 25
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Producto no encontrado"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getById(@PathVariable Integer id) {
        return productoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ============================================
    // CREATE
    // ============================================
    @Operation(
            summary = "Crear nuevo producto",
            description = "Crea un nuevo producto en el sistema. Requiere rol ADMIN."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Producto creado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Producto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acceso denegado. Se requiere rol ADMIN"
            )
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Producto create(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Datos del nuevo producto",
                    content = @Content(
                            schema = @Schema(implementation = Producto.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Crear producto",
                                            value = """
                                                    {
                                                      "nombre": "Teclado Mecánico",
                                                      "precio": 49990,
                                                      "stock": 15
                                                    }
                                                    """
                                    )
                            }
                    )
            )
            Producto producto
    ) {
        return productoService.save(producto);
    }

    // ============================================
    // UPDATE
    // ============================================
    @Operation(
            summary = "Actualizar producto",
            description = "Actualiza los datos de un producto existente. Requiere rol ADMIN."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Producto actualizado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Producto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Producto no encontrado"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acceso denegado. Se requiere rol ADMIN"
            )
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Producto> update(
            @PathVariable Integer id,
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Datos actualizados del producto",
                    content = @Content(
                            schema = @Schema(implementation = Producto.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Actualizar producto",
                                            value = """
                                                    {
                                                      "nombre": "Teclado Mecánico RGB",
                                                      "precio": 54990,
                                                      "stock": 20
                                                    }
                                                    """
                                    )
                            }
                    )
            )
            Producto data
    ) {
        Producto updated = productoService.update(id, data);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    // ============================================
    // DELETE
    // ============================================
    @Operation(
            summary = "Eliminar producto",
            description = "Elimina un producto del sistema según su ID. Requiere rol ADMIN."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Producto eliminado correctamente"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acceso denegado. Se requiere rol ADMIN"
            )
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
