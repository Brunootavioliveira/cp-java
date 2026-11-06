package br.com.fiap.ocorrencias.repository;

import br.com.fiap.ocorrencias.model.TipoOcorrencia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoOcorrenciaRepository extends JpaRepository<TipoOcorrencia, Long> {

    List<TipoOcorrencia> findByCategoriaId(Long categoriaId);
}
