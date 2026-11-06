package br.com.fiap.ocorrencias.dto;

import br.com.fiap.ocorrencias.model.Ocorrencia;
import java.time.LocalDate;
import java.time.LocalTime;

public record OcorrenciaResponse(
        Long id,
        Long turmaId,
        String turma,
        Long alunoId,
        String aluno,
        Long usuarioId,
        String usuario,
        Long categoriaId,
        String categoria,
        Long tipoId,
        String tipo,
        LocalDate data,
        LocalTime hora,
        String descricao) {

    public static OcorrenciaResponse de(Ocorrencia o) {
        return new OcorrenciaResponse(
                o.getId(),
                o.getTurma().getId(),
                o.getTurma().getNome(),
                o.getAluno().getId(),
                o.getAluno().getNome(),
                o.getUsuario().getId(),
                o.getUsuario().getNome(),
                o.getCategoria().getId(),
                o.getCategoria().getNome(),
                o.getTipo().getId(),
                o.getTipo().getNome(),
                o.getData(),
                o.getHora(),
                o.getDescricao());
    }
}
