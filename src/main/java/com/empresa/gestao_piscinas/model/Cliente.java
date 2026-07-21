package com.empresa.gestao_piscinas.model;

import jakarta.persistence.*;
import lombok.Data;

@Data // O Lombok gera os Getters e Setters automaticamente com isso!
@Entity
@Table(name = "tb_clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 20)
    private String telefone;

    @Column(nullable = false, length = 200)
    private String endereco;
}
