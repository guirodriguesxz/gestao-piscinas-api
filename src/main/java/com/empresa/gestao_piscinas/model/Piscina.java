package com.empresa.gestao_piscinas.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_piscinas")
public class Piscina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double volumeLitros;

    @Column(length = 50)
    private String tipoRevestimento; // Ex: Vinil, Azulejo, Fibra

    @Column(nullable = false)
    private Boolean ambienteExterno; // true = aberta (pega chuva), false = coberta

    // Aqui está a mágica da relação! Várias piscinas podem pertencer a UM cliente.
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}