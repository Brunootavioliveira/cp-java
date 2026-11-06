package br.com.fiap.ocorrencias.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TipoOcorrenciaRequest(
        @NotBlank(message = "O nome do tipo é obrigatório")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
        String nome,

        @NotNull(message = "A categoria é obrigatória")
        Long categoriaId) {
}
