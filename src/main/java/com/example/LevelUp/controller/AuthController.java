package com.example.LevelUp.controller;

import com.example.LevelUp.controller.DTO.LoginRequest;
import com.example.LevelUp.controller.DTO.LoginResponse;
import com.example.LevelUp.model.Usuario;
import com.example.LevelUp.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(
        name = "Auth",
        description = "Endpoints de autenticación, login y creación de administrador."
)
public class AuthController {

    @Autowired
    private UsuarioService userService;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder encoder;

    @Autowired
    private com.example.LevelUp.repository.UsuarioRepository userRepository;

    // ============================================
    // STATUS
    // ============================================
    @Operation(
            summary = "Verificar estado del servicio de autenticación",
            description = "Devuelve un mensaje simple indicando que el servicio está activo."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Servicio funcionando correctamente")
    })
    @GetMapping("/status")
    public String status() {
        return "Auth service is running";
    }

    // ============================================
    // LOGIN
    // ============================================
    @Operation(
            summary = "Iniciar sesión",
            description = "Recibe un usuario y contraseña, valida las credenciales y devuelve un token JWT si son correctas."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Login exitoso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Login correcto",
                                            value = """
                        {
                          "status": "ok",
                          "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
                        }
                        """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Credenciales inválidas",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Login incorrecto",
                                            value = """
                        {
                          "status": "error",
                          "token": null
                        }
                        """
                                    )
                            }
                    )
            )
    })
    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Credenciales del usuario",
                    content = @Content(
                            schema = @Schema(implementation = LoginRequest.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Petición de ejemplo",
                                            value = """
                                                    {
                                                      "correo": "diego@gmail.com",
                                                      "password": "Admin"
                                                    }
                        """
                                    )
                            }
                    )
            )
            LoginRequest request
    ) {
        Usuario user = userRepository.findByCorreo(request.getCorreo());
        String token = userService.login(user, request.getPassword());

        if (token == null) return new LoginResponse("error", null);

        return new LoginResponse("ok", token);
    }



    @Operation(
            summary = "Crear un administrador inicial",
            description = "Crea un usuario ADMIN por defecto en la base de datos. Se recomienda usar solo una vez."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Administrador creado exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno al crear el administrador")
    })
    @PostMapping("/bootstrap-admin")
    public ResponseEntity<?> bootstrapAdmin() {

        Usuario admin = new Usuario();
        admin.setCorreo("admin@gmail.com");
        admin.setContrasena(encoder.encode("Admin"));
        admin.setRol("ADMIN");

        userRepository.save(admin);

        return ResponseEntity.ok("Admin creado");
    }
}
