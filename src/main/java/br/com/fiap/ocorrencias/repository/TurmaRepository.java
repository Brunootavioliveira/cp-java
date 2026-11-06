package br.com.fiap.ocorrencias.repository;

import br.com.fiap.ocorrencias.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurmaRepository extends JpaRepository<Turma, Long> {

    boolean existsByIdAndAlunosId(Long id, Long alunoId);
}
