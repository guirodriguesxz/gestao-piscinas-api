package com.empresa.gestao_piscinas.Controller;

import com.empresa.gestao_piscinas.model.Cliente;
import com.empresa.gestao_piscinas.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository repository;

    // Rota para CADASTRAR um novo cliente (POST)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente cadastrar(@RequestBody Cliente cliente) {
        return repository.save(cliente);
    }

    // Rota para LISTAR todos os clientes (GET)
    @GetMapping
    public List<Cliente> listar() {
        return repository.findAll();
    }
}