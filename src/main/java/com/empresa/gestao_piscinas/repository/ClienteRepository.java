package com.empresa.gestao_piscinas.repository;

import com.empresa.gestao_piscinas.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Só de herdar o JpaRepository, o Spring já nos dá o Salvar, Listar, Deletar e Buscar prontos!
}
