package com.empresa.gestao_piscinas.service;

import com.empresa.gestao_piscinas.dto.ClienteDTOs.ClienteRequest;
import com.empresa.gestao_piscinas.dto.ClienteDTOs.ClienteResponse;
import com.empresa.gestao_piscinas.exception.RecursoNaoEncontradoException;
import com.empresa.gestao_piscinas.model.Cliente;
import com.empresa.gestao_piscinas.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    @Transactional
    public ClienteResponse cadastrar(ClienteRequest request) {
        Cliente cliente = new Cliente();
        cliente.setNome(request.nome());
        cliente.setTelefone(request.telefone());
        cliente.setEndereco(request.endereco());
        return ClienteResponse.de(repository.save(cliente));
    }

    @Transactional(readOnly = true)
    public List<ClienteResponse> listar() {
        return repository.findAll().stream().map(ClienteResponse::de).toList();
    }

    @Transactional(readOnly = true)
    public ClienteResponse buscar(Long id) {
        return ClienteResponse.de(buscarEntidade(id));
    }

    Cliente buscarEntidade(Long id) {
        return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Cliente", id));
    }
}
