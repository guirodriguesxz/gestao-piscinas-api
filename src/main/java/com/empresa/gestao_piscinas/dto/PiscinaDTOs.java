package com.empresa.gestao_piscinas.dto;

import com.empresa.gestao_piscinas.model.Piscina;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public final class PiscinaDTOs {

    private PiscinaDTOs() {
    }

    public record PiscinaRequest(
            @NotNull Long clienteId,
            @NotNull @Positive Double volumeLitros,
            @Size(max = 50) String tipoRevestimento,
            @NotNull Boolean ambienteExterno
    ) {
    }

    public record PiscinaResponse(
            Long id,
            Long clienteId,
            String clienteNome,
            Double volumeLitros,
            String tipoRevestimento,
            Boolean ambienteExterno
    ) {
        public static PiscinaResponse de(Piscina p) {
            return new PiscinaResponse(p.getId(), p.getCliente().getId(), p.getCliente().getNome(),
                    p.getVolumeLitros(), p.getTipoRevestimento(), p.getAmbienteExterno());
        }
    }
}
