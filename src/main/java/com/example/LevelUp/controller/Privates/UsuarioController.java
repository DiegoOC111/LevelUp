package com.example.LevelUp.controller.Privates;

import com.example.LevelUp.controller.DTO.LoginResponse;
import com.example.LevelUp.controller.DTO.RegisterRequest;
import com.example.LevelUp.model.Usuario;
import com.example.LevelUp.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
@Tag(name = "Usuario", description = "Gestión de usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // ============================
    // GET ALL
    // ============================
    @Operation(summary = "Obtener todos los usuarios",
            description = "Retorna una lista completa de usuarios registrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class)))
    })
    @GetMapping
    public ResponseEntity<List<Usuario>> getAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    // ============================
    // GET BY ID
    // ============================
    @Operation(summary = "Obtener usuario por ID",
            description = "Devuelve la información del usuario asociado al ID proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario obtenido correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "400", description = "ID inválido o usuario no encontrado",
                    content = @Content)
    })
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
            Optional<Usuario> usuarioOpt = usuarioService.findById(id);
            if (usuarioOpt.isPresent()) {
                return ResponseEntity.ok(usuarioOpt.get());
            } else {
                return ResponseEntity.badRequest().body("Usuario no encontrado");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ============================
    // CREATE
    // ============================
    @Operation(summary = "Crear un nuevo usuario",
            description = "Registra un nuevo usuario en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario creado correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos para crear usuario",
                    content = @Content)
    })
    @PostMapping

    public ResponseEntity<?> create(@RequestBody Usuario usuario) {
        try {
            Usuario created = usuarioService.save(usuario.getCorreo(), usuario.getContrasena(), usuario.getRol());
            return ResponseEntity.ok(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ============================
    // UPDATE
    // ============================
    @Operation(summary = "Actualizar un usuario",
            description = "Modifica los datos de un usuario existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos para actualizar",
                    content = @Content)
    })
    @PreAuthorize("hasAnyRole('USER','ADMIN')")

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Usuario data) {
        try {
            Usuario existing = usuarioService.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

            Usuario updated = usuarioService.update(existing.getCorreo(), data.getContrasena(), data.getRol());
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ============================
    // DELETE
    // ============================
    @Operation(summary = "Eliminar un usuario",
            description = "Elimina un usuario del sistema utilizando su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "400", description = "ID inválido o usuario no encontrado",
                    content = @Content)
    })
    @PostMapping("/create-user")

    public ResponseEntity<?> createUser(@RequestBody RegisterRequest req) {
        try {
            String rol = (req.getRole() == null || req.getRole().isBlank()) ? "USER" : req.getRole();

            Usuario u = usuarioService.save(
                    req.getCorreo(),
                    req.getPassword(),
                    rol
            );
            return ResponseEntity.ok("Usuario creado: " + u.getCorreo());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            usuarioService.delete(id);
            return ResponseEntity.ok("Usuario eliminado con éxito.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ============================
    // LOGIN
    // ============================

}
