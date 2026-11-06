package br.com.fiap.ocorrencias.dto;

import br.com.fiap.ocorrencias.model.TipoOcorrencia;

public record TipoOcorrenciaResponse(Long id, String nome, Long categoriaId, String categoria) {

    public static TipoOcorrenciaResponse de(TipoOcorrencia tipo) {
        return new TipoOcorrenciaResponse(
                tipo.getId(),
                tipo.getNome(),
                tipo.getCategoria().getId(),
                tipo.getCategoria().getNome());
    }
}
