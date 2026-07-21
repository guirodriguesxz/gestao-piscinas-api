package com.empresa.gestao_piscinas.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "tb_visitas")
public class VisitaTecnica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate dataVisita; // Data que o técnico foi lá

    @Column(nullable = false)
    private Double nivelCloro; // Ex: 1.5, 3.0

    @Column(nullable = false)
    private Double nivelPh; // Ex: 7.2, 7.6

    @Column(length = 255)
    private String produtosUsados; // Ex: "100g de cloro, 50g de barrilha"

    @Column(length = 100)
    private String status; // Ex: "Concluída", "Agendada"

    // A mágica: Várias visitas são feitas em UMA mesma piscina ao longo do ano.
    @ManyToOne
    @JoinColumn(name = "piscina_id", nullable = false)
    private Piscina piscina;
}