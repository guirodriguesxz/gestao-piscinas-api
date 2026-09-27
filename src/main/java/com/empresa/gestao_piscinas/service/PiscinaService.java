package com.empresa.gestao_piscinas.service;

import com.empresa.gestao_piscinas.dto.PiscinaDTOs.PiscinaRequest;
import com.empresa.gestao_piscinas.dto.PiscinaDTOs.PiscinaResponse;
import com.empresa.gestao_piscinas.exception.RecursoNaoEncontradoException;
import com.empresa.gestao_piscinas.model.Piscina;
import com.empresa.gestao_piscinas.repository.PiscinaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PiscinaService {

    private final PiscinaRepository repository;
    private final ClienteService clienteService;

    @Transactional
    public PiscinaResponse cadastrar(PiscinaRequest request) {
        Piscina piscina = new Piscina();
        piscina.setCliente(clienteService.buscarEntidade(request.clienteId()));
        piscina.setVolumeLitros(request.volumeLitros());
        piscina.setTipoRevestimento(request.tipoRevestimento());
        piscina.setAmbienteExterno(request.ambienteExterno());
        return PiscinaResponse.de(repository.save(piscina));
    }

    @Transactional(readOnly = true)
    public List<PiscinaResponse> listar() {
        return repository.findAllByOrderByIdAsc().stream().map(PiscinaResponse::de).toList();
    }

    @Transactional(readOnly = true)
    public List<PiscinaResponse> listarDoCliente(Long clienteId) {
        clienteService.buscarEntidade(clienteId); // 404 se o cliente não existir
        return repository.findByClienteIdOrderByIdAsc(clienteId).stream().map(PiscinaResponse::de).toList();
    }

    @Transactional(readOnly = true)
    public PiscinaResponse buscar(Long id) {
        return PiscinaResponse.de(buscarEntidade(id));
    }

    Piscina buscarEntidade(Long id) {
        return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Piscina", id));
    }
}
