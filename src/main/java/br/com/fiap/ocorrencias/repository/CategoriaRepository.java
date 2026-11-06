package br.com.fiap.ocorrencias.repository;

import br.com.fiap.ocorrencias.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
