package br.com.fiap.ocorrencias.repository;

import br.com.fiap.ocorrencias.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
