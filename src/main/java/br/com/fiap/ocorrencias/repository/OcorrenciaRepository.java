package br.com.fiap.ocorrencias.repository;

import br.com.fiap.ocorrencias.model.Ocorrencia;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {

    @Query("select o from Ocorrencia o "
            + "where (:alunoId is null or o.aluno.id = :alunoId) "
            + "and (:turmaId is null or o.turma.id = :turmaId) "
            + "and (:categoriaId is null or o.categoria.id = :categoriaId) "
            + "and (:tipoId is null or o.tipo.id = :tipoId) "
            + "and (:dataInicio is null or o.data >= :dataInicio) "
            + "and (:dataFim is null or o.data <= :dataFim) "
            + "order by o.data desc, o.hora desc")
    List<Ocorrencia> filtrar(@Param("alunoId") Long alunoId,
                             @Param("turmaId") Long turmaId,
                             @Param("categoriaId") Long categoriaId,
                             @Param("tipoId") Long tipoId,
                             @Param("dataInicio") LocalDate dataInicio,
                             @Param("dataFim") LocalDate dataFim);
}
