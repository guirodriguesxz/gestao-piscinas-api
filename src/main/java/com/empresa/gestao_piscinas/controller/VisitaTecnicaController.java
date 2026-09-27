package com.empresa.gestao_piscinas.controller;

import com.empresa.gestao_piscinas.dto.VisitaDTOs.VisitaRequest;
import com.empresa.gestao_piscinas.dto.VisitaDTOs.VisitaResponse;
import com.empresa.gestao_piscinas.service.VisitaTecnicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Visitas técnicas")
@RestController
@RequestMapping("/api/visitas")
@RequiredArgsConstructor
public class VisitaTecnicaController {

    private final VisitaTecnicaService visitaService;

    @Operation(summary = "Registra uma visita técnica com as medições da água")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VisitaResponse registrar(@RequestBody @Valid VisitaRequest request) {
        return visitaService.registrar(request);
    }

    @Operation(summary = "Lista todas as visitas (mais recente primeiro)")
    @GetMapping
    public List<VisitaResponse> listar() {
        return visitaService.listar();
    }
}
