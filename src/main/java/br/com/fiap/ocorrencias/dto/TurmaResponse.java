package br.com.fiap.ocorrencias.dto;

import br.com.fiap.ocorrencias.model.Turma;
import br.com.fiap.ocorrencias.model.Turno;

public record TurmaResponse(Long id, String nome, Turno turno, Integer ano, Integer semestre) {

    public static TurmaResponse de(Turma turma) {
        return new TurmaResponse(
                turma.getId(),
                turma.getNome(),
                turma.getTurno(),
                turma.getAno(),
                turma.getSemestre());
    }
}
