package br.com.fiap.ocorrencias.service;

import br.com.fiap.ocorrencias.dto.TipoOcorrenciaRequest;
import br.com.fiap.ocorrencias.dto.TipoOcorrenciaResponse;
import br.com.fiap.ocorrencias.exception.RecursoNaoEncontradoException;
import br.com.fiap.ocorrencias.model.Categoria;
import br.com.fiap.ocorrencias.model.TipoOcorrencia;
import br.com.fiap.ocorrencias.repository.CategoriaRepository;
import br.com.fiap.ocorrencias.repository.TipoOcorrenciaRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TipoOcorrenciaService {

    private final TipoOcorrenciaRepository tipos;
    private final CategoriaRepository categorias;

    public TipoOcorrenciaService(TipoOcorrenciaRepository tipos, CategoriaRepository categorias) {
        this.tipos = tipos;
        this.categorias = categorias;
    }

    @Transactional(readOnly = true)
    public List<TipoOcorrenciaResponse> listar(Long categoriaId) {
        List<TipoOcorrencia> resultado = categoriaId == null
                ? tipos.findAll()
                : tipos.findByCategoriaId(categoriaId);
        return resultado.stream().map(TipoOcorrenciaResponse::de).toList();
    }

    @Transactional(readOnly = true)
    public TipoOcorrenciaResponse buscar(Long id) {
        return TipoOcorrenciaResponse.de(obter(id));
    }

    @Transactional
    public TipoOcorrenciaResponse criar(TipoOcorrenciaRequest request) {
        TipoOcorrencia tipo = new TipoOcorrencia(request.nome(), obterCategoria(request.categoriaId()));
        return TipoOcorrenciaResponse.de(tipos.save(tipo));
    }

    @Transactional
    public TipoOcorrenciaResponse atualizar(Long id, TipoOcorrenciaRequest request) {
        TipoOcorrencia tipo = obter(id);
        tipo.setNome(request.nome());
        tipo.setCategoria(obterCategoria(request.categoriaId()));
        return TipoOcorrenciaResponse.de(tipos.save(tipo));
    }

    @Transactional
    public void excluir(Long id) {
        tipos.delete(obter(id));
        tipos.flush();
    }

    private TipoOcorrencia obter(Long id) {
        return tipos.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Tipo de ocorrência não encontrado: " + id));
    }

    private Categoria obterCategoria(Long id) {
        return categorias.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada: " + id));
    }
}
