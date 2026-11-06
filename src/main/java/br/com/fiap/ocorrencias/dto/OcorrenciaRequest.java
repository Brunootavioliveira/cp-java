package br.com.fiap.ocorrencias.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;

public record OcorrenciaRequest(
        @NotNull(message = "A turma é obrigatória")
        Long turmaId,

        @NotNull(message = "O aluno é obrigatório")
        Long alunoId,

        @NotNull(message = "O professor é obrigatório")
        Long usuarioId,

        @NotNull(message = "A categoria é obrigatória")
        Long categoriaId,

        @NotNull(message = "O tipo de ocorrência é obrigatório")
        Long tipoId,

        @NotNull(message = "A data é obrigatória")
        @PastOrPresent(message = "A data não pode ser futura")
        LocalDate data,

        @NotNull(message = "A hora é obrigatória")
        LocalTime hora,

        @NotBlank(message = "A descrição é obrigatória")
        @Size(max = 1000, message = "A descrição deve ter no máximo 1000 caracteres")
        String descricao) {
}
