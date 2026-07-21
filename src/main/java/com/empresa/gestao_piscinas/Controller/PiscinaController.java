package com.empresa.gestao_piscinas.Controller;

import com.empresa.gestao_piscinas.model.Piscina;
import com.empresa.gestao_piscinas.repository.PiscinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/piscinas")
public class PiscinaController {

    @Autowired
    private PiscinaRepository repository;

    // Cadastrar uma nova piscina
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Piscina cadastrar(@RequestBody Piscina piscina) {
        return repository.save(piscina);
    }

    // Listar todas as piscinas
    @GetMapping
    public List<Piscina> listar() {
        return repository.findAll();
    }
}