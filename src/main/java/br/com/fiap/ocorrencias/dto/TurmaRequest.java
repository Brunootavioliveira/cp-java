package br.com.fiap.ocorrencias.dto;

import br.com.fiap.ocorrencias.model.Turno;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TurmaRequest(
        @NotBlank(message = "O nome da turma é obrigatório")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
        String nome,

        @NotNull(message = "O turno é obrigatório (MANHA, TARDE ou NOITE)")
        Turno turno,

        @NotNull(message = "O ano letivo é obrigatório")
        @Min(value = 2000, message = "O ano letivo deve ser maior ou igual a 2000")
        Integer ano,

        @NotNull(message = "O semestre é obrigatório")
        @Min(value = 1, message = "O semestre deve ser 1 ou 2")
        @Max(value = 2, message = "O semestre deve ser 1 ou 2")
        Integer semestre) {
}
