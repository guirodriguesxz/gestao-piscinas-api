package com.empresa.gestao_piscinas.controller;

import com.empresa.gestao_piscinas.dto.ClienteDTOs.ClienteRequest;
import com.empresa.gestao_piscinas.dto.ClienteDTOs.ClienteResponse;
import com.empresa.gestao_piscinas.dto.PiscinaDTOs.PiscinaResponse;
import com.empresa.gestao_piscinas.service.ClienteService;
import com.empresa.gestao_piscinas.service.PiscinaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Clientes")
@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    private final PiscinaService piscinaService;

    @Operation(summary = "Cadastra um cliente")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponse cadastrar(@RequestBody @Valid ClienteRequest request) {
        return clienteService.cadastrar(request);
    }

    @Operation(summary = "Lista os clientes")
    @GetMapping
    public List<ClienteResponse> listar() {
        return clienteService.listar();
    }

    @Operation(summary = "Busca um cliente pelo id")
    @GetMapping("/{id}")
    public ClienteResponse buscar(@PathVariable Long id) {
        return clienteService.buscar(id);
    }

    @Operation(summary = "Lista as piscinas de um cliente")
    @GetMapping("/{id}/piscinas")
    public List<PiscinaResponse> piscinas(@PathVariable Long id) {
        return piscinaService.listarDoCliente(id);
    }
}
