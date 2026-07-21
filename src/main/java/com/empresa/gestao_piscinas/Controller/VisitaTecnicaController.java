package com.empresa.gestao_piscinas.Controller;

import com.empresa.gestao_piscinas.model.VisitaTecnica;
import com.empresa.gestao_piscinas.repository.VisitaTecnicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visitas")
public class VisitaTecnicaController {

    @Autowired
    private VisitaTecnicaRepository repository;

    // Registrar uma nova visita técnica
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VisitaTecnica registrar(@RequestBody VisitaTecnica visita) {
        return repository.save(visita);
    }

    // Listar todo o histórico de visitas
    @GetMapping
    public List<VisitaTecnica> listar() {
        return repository.findAll();
    }
}