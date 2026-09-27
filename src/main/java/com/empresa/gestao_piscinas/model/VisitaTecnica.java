package com.empresa.gestao_piscinas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_visitas")
public class VisitaTecnica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate dataVisita;

    @Column(nullable = false)
    private Double nivelCloro; // ppm

    @Column(nullable = false)
    private Double nivelPh;

    @Column(length = 255)
    private String produtosUsados; // Ex: "100g de cloro, 50g de barrilha"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusVisita status;

    // Cada visita gera um ponto na série histórica da piscina (base para o modelo preditivo)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "piscina_id", nullable = false)
    private Piscina piscina;

    public CondicaoAgua getCondicaoAgua() {
        return CondicaoAgua.avaliar(nivelPh, nivelCloro);
    }
}
