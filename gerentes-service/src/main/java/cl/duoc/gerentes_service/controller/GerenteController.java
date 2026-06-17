package cl.duoc.gerentes_service.controller;

import cl.duoc.gerentes_service.dto.GerenteDTO;
import cl.duoc.gerentes_service.exception.ErrorResponse;
import cl.duoc.gerentes_service.model.Gerente;
import cl.duoc.gerentes_service.service.GerenteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/gerentes")
public class GerenteController {

    @Autowired
    private GerenteService gerenteService;

    //1
    @Operation(
            summary = "Listar gerentes",
            description = "Método que lista todos los gerentes del sistema"
    )
    @ApiResponse(
            responseCode = "200",
            description = "gerentes encontrados",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = Gerente.class)
                    )
            )
    )
    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(gerenteService.findAll());
    }

    //2
    @Operation(
            summary = "Buscar gerentes por ID",
            description = "Método que busca un gerente en el sistema a través del ID. Este id es de tipo numerico (LONG)"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Gerente encontrado",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Gerente.class)
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Gerente no encontrado",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
            )
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Gerente gerente = gerenteService.findById(id);
        if (gerente == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(gerente);
    }

    //3
    @Operation(
            summary = "Registra un gerente",
            description = "Método que registra un gerente en el sistema"
    )
    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Gerente gerente) {
        Gerente gerenteNuevo = gerenteService.save(gerente);
        return new ResponseEntity<>(gerenteNuevo, HttpStatus.CREATED);
    }

    //4
    @Operation(
            summary = "Actualiza un gerente por ID",
            description = "Método que actualiza un gerente en el sistema a través del ID."
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Gerente gerente) {
        Gerente gerenteActualizado = gerenteService.update(id, gerente);
        if (gerenteActualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(gerenteActualizado);
    }

    //5
    @Operation(
            summary = "Elimina un gerente por ID",
            description = "Método que elimina un producto en el sistema a través del ID"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrar(@PathVariable Long id) {
        gerenteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //6
    @Operation(
            summary = "Listar gerentes ocultando informacion sensible",
            description = "Método que lista todos los gerentes del sistema de forma resumida"
    )
    @ApiResponse(
            responseCode = "200",
            description = "gerentes encontrados",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = GerenteDTO.class)
                    )
            )
    )
    @GetMapping("/listado")
    public ResponseEntity<?> listarDTO(){
        return ResponseEntity.ok(gerenteService.findDTOList());
    }

    //7
    @Operation(
            summary = "Buscar gerentes por ID ocultando informacion sensible",
            description = "Método que busca un gerente en el sistema a través del ID de forma resumida. Este id es de tipo numerico (LONG)"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Gerente encontrado",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GerenteDTO.class)
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Gerente no encontrado",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
            )
    )
    @GetMapping("/listado/{id}")
    public ResponseEntity<?> buscarPorIdDTO(@PathVariable Long id){
        GerenteDTO gerente = gerenteService.findDTO(id);
        if (gerente == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(gerente);
    }

    //8
    @Operation(
            summary = "Listar gerentes por correo",
            description = "Método que lista todos los gerentes del sistema a traves del tipo de correo ingresado."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Gerentes encontrados",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = Gerente.class)
                    )
            )
    )
    @GetMapping("/email/{email}")
    public ResponseEntity<?> gerentePorEmail( @PathVariable String email){
        return ResponseEntity.ok(gerenteService.findByEmail(email));
    }

    //9
    @Operation(
            summary = "Listar gerentes por salario mayor a un valor",
            description = "Método que lista todos los gerentes del sistema que tienen un salario mayor a un valor ingresado."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Gerentes encontrados",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = Gerente.class)
                    )
            )
    )
    @GetMapping("/salario-mayor/{salario}")
    public ResponseEntity<?> gerentePorSalarioMayor( @PathVariable Integer salario){
        return ResponseEntity.ok(gerenteService.findBySalarioGreaterEqual(salario));
    }

    //10
    @Operation(
            summary = "Listar gerentes por salario menor a un valor",
            description = "Método que lista todos los gerentes del sistema que tienen un salario menor a un valor ingresado."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Gerentes encontrados",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = Gerente.class)
                    )
            )
    )
    @GetMapping("/salario-menor/{salario}")
    public ResponseEntity<?> gerentePorSalarioMenor( @PathVariable Integer salario){
        return ResponseEntity.ok(gerenteService.findBySalarioLessEqual(salario));
    }

    //11
    @Operation(
            summary = "Listar gerentes por bono mayor a un valor",
            description = "Método que lista todos los gerentes del sistema que tienen un bono mayor a un valor ingresado."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Gerentes encontrados",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = Gerente.class)
                    )
            )
    )
    @GetMapping("/bono-mayor/{bono}")
    public ResponseEntity<?> gerentePorBonoMayor( @PathVariable Integer bono){
        return ResponseEntity.ok(gerenteService.findByBonoGreaterEqual(bono));
    }

    //12
    @Operation(
            summary = "Listar gerentes por bono menor a un valor",
            description = "Método que lista todos los gerentes del sistema que tienen un bono menor a un valor ingresado."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Gerentes encontrados",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = Gerente.class)
                    )
            )
    )
    @GetMapping("/bono-menor/{bono}")
    public ResponseEntity<?> gerentePorBonoMenor( @PathVariable Integer bono){
        return ResponseEntity.ok(gerenteService.findByBonoLessEqual(bono));
    }
}
