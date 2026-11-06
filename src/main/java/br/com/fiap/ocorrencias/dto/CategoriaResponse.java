package br.com.fiap.ocorrencias.dto;

import br.com.fiap.ocorrencias.model.Categoria;

public record CategoriaResponse(Long id, String nome) {

    public static CategoriaResponse de(Categoria categoria) {
        return new CategoriaResponse(categoria.getId(), categoria.getNome());
    }
}
