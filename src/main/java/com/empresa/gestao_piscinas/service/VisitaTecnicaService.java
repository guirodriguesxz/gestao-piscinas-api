package com.empresa.gestao_piscinas.service;

import com.empresa.gestao_piscinas.dto.VisitaDTOs.VisitaRequest;
import com.empresa.gestao_piscinas.dto.VisitaDTOs.VisitaResponse;
import com.empresa.gestao_piscinas.model.VisitaTecnica;
import com.empresa.gestao_piscinas.repository.VisitaTecnicaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitaTecnicaService {

    private final VisitaTecnicaRepository repository;
    private final PiscinaService piscinaService;

    @Transactional
    public VisitaResponse registrar(VisitaRequest request) {
        VisitaTecnica visita = new VisitaTecnica();
        visita.setPiscina(piscinaService.buscarEntidade(request.piscinaId()));
        visita.setDataVisita(request.dataVisita());
        visita.setNivelCloro(request.nivelCloro());
        visita.setNivelPh(request.nivelPh());
        visita.setProdutosUsados(request.produtosUsados());
        visita.setStatus(request.status());
        return VisitaResponse.de(repository.save(visita));
    }

    @Transactional(readOnly = true)
    public List<VisitaResponse> listar() {
        return repository.findAllByOrderByDataVisitaDescIdDesc().stream().map(VisitaResponse::de).toList();
    }

    @Transactional(readOnly = true)
    public List<VisitaResponse> historicoDaPiscina(Long piscinaId) {
        piscinaService.buscarEntidade(piscinaId); // 404 se a piscina não existir
        return repository.findByPiscinaIdOrderByDataVisitaDescIdDesc(piscinaId).stream()
                .map(VisitaResponse::de)
                .toList();
    }
}
