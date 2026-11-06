package br.com.fiap.ocorrencias.service;

import br.com.fiap.ocorrencias.dto.CategoriaRequest;
import br.com.fiap.ocorrencias.dto.CategoriaResponse;
import br.com.fiap.ocorrencias.exception.RecursoNaoEncontradoException;
import br.com.fiap.ocorrencias.model.Categoria;
import br.com.fiap.ocorrencias.repository.CategoriaRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaService {

    private final CategoriaRepository categorias;

    public CategoriaService(CategoriaRepository categorias) {
        this.categorias = categorias;
    }

    @Transactional(readOnly = true)
    public List<CategoriaResponse> listar() {
        return categorias.findAll().stream().map(CategoriaResponse::de).toList();
    }

    @Transactional(readOnly = true)
    public CategoriaResponse buscar(Long id) {
        return CategoriaResponse.de(obter(id));
    }

    @Transactional
    public CategoriaResponse criar(CategoriaRequest request) {
        Categoria categoria = new Categoria(request.nome());
        return CategoriaResponse.de(categorias.save(categoria));
    }

    @Transactional
    public CategoriaResponse atualizar(Long id, CategoriaRequest request) {
        Categoria categoria = obter(id);
        categoria.setNome(request.nome());
        return CategoriaResponse.de(categorias.save(categoria));
    }

    @Transactional
    public void excluir(Long id) {
        categorias.delete(obter(id));
        categorias.flush();
    }

    private Categoria obter(Long id) {
        return categorias.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada: " + id));
    }
}
