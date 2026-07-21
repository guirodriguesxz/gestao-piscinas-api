package com.empresa.gestao_piscinas.repository;

import com.empresa.gestao_piscinas.model.VisitaTecnica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitaTecnicaRepository extends JpaRepository<VisitaTecnica, Long> {
}