package com.empresa.gestao_piscinas.repository;

import com.empresa.gestao_piscinas.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
