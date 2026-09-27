package com.empresa.gestao_piscinas.controller;

import com.empresa.gestao_piscinas.dto.PiscinaDTOs.PiscinaRequest;
import com.empresa.gestao_piscinas.dto.PiscinaDTOs.PiscinaResponse;
import com.empresa.gestao_piscinas.dto.VisitaDTOs.VisitaResponse;
import com.empresa.gestao_piscinas.service.PiscinaService;
import com.empresa.gestao_piscinas.service.VisitaTecnicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Piscinas")
@RestController
@RequestMapping("/api/piscinas")
@RequiredArgsConstructor
public class PiscinaController {

    private final PiscinaService piscinaService;
    private final VisitaTecnicaService visitaService;

    @Operation(summary = "Cadastra uma piscina vinculada a um cliente")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PiscinaResponse cadastrar(@RequestBody @Valid PiscinaRequest request) {
        return piscinaService.cadastrar(request);
    }

    @Operation(summary = "Lista as piscinas")
    @GetMapping
    public List<PiscinaResponse> listar() {
        return piscinaService.listar();
    }

    @Operation(summary = "Busca uma piscina pelo id")
    @GetMapping("/{id}")
    public PiscinaResponse buscar(@PathVariable Long id) {
        return piscinaService.buscar(id);
    }

    @Operation(summary = "Histórico de visitas e condição da água da piscina (mais recente primeiro)")
    @GetMapping("/{id}/visitas")
    public List<VisitaResponse> historico(@PathVariable Long id) {
        return visitaService.historicoDaPiscina(id);
    }
}
