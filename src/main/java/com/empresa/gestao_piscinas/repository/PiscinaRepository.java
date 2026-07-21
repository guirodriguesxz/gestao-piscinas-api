package com.empresa.gestao_piscinas.repository;

import com.empresa.gestao_piscinas.model.Piscina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PiscinaRepository extends JpaRepository<Piscina, Long> {
}