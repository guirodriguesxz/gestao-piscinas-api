package com.empresa.gestao_piscinas.repository;

import com.empresa.gestao_piscinas.model.VisitaTecnica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitaTecnicaRepository extends JpaRepository<VisitaTecnica, Long> {

    // Série histórica de uma piscina, da visita mais recente para a mais antiga
    List<VisitaTecnica> findByPiscinaIdOrderByDataVisitaDescIdDesc(Long piscinaId);

    List<VisitaTecnica> findAllByOrderByDataVisitaDescIdDesc();
}
