package br.com.fiap.ocorrencias.dto;

import br.com.fiap.ocorrencias.model.Aluno;
import java.time.LocalDate;

public record AlunoResponse(Long id, String nome, LocalDate dataNascimento, String foto) {

    public static AlunoResponse de(Aluno aluno) {
        return new AlunoResponse(aluno.getId(), aluno.getNome(), aluno.getDataNascimento(), aluno.getFoto());
    }
}
