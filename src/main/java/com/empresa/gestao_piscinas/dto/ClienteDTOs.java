package com.empresa.gestao_piscinas.dto;

import com.empresa.gestao_piscinas.model.Cliente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public final class ClienteDTOs {

    private ClienteDTOs() {
    }

    public record ClienteRequest(
            @NotBlank @Size(max = 100) String nome,
            @NotBlank @Size(max = 20) String telefone,
            @NotBlank @Size(max = 200) String endereco
    ) {
    }

    public record ClienteResponse(Long id, String nome, String telefone, String endereco) {
        public static ClienteResponse de(Cliente c) {
            return new ClienteResponse(c.getId(), c.getNome(), c.getTelefone(), c.getEndereco());
        }
    }
}
