package com.empresa.gestao_piscinas.repository;

import com.empresa.gestao_piscinas.model.Piscina;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PiscinaRepository extends JpaRepository<Piscina, Long> {

    // Carrega o cliente junto para evitar N+1 ao montar a resposta
    @EntityGraph(attributePaths = "cliente")
    List<Piscina> findAllByOrderByIdAsc();

    @EntityGraph(attributePaths = "cliente")
    List<Piscina> findByClienteIdOrderByIdAsc(Long clienteId);
}
