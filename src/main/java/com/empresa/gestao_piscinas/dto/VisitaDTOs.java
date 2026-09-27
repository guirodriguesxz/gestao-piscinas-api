package com.empresa.gestao_piscinas.dto;

import com.empresa.gestao_piscinas.model.CondicaoAgua;
import com.empresa.gestao_piscinas.model.StatusVisita;
import com.empresa.gestao_piscinas.model.VisitaTecnica;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public final class VisitaDTOs {

    private VisitaDTOs() {
    }

    public record VisitaRequest(
            @NotNull Long piscinaId,
            @NotNull LocalDate dataVisita,
            @NotNull @DecimalMin("0.0") @DecimalMax("20.0") Double nivelCloro,
            @NotNull @DecimalMin("0.0") @DecimalMax("14.0") Double nivelPh,
            @Size(max = 255) String produtosUsados,
            @NotNull StatusVisita status
    ) {
    }

    public record VisitaResponse(
            Long id,
            Long piscinaId,
            LocalDate dataVisita,
            Double nivelCloro,
            Double nivelPh,
            String produtosUsados,
            StatusVisita status,
            CondicaoAgua condicaoAgua
    ) {
        public static VisitaResponse de(VisitaTecnica v) {
            return new VisitaResponse(v.getId(), v.getPiscina().getId(), v.getDataVisita(), v.getNivelCloro(),
                    v.getNivelPh(), v.getProdutosUsados(), v.getStatus(), v.getCondicaoAgua());
        }
    }
}
