package br.com.fiap.ocorrencias.controller;

import br.com.fiap.ocorrencias.dto.TipoOcorrenciaRequest;
import br.com.fiap.ocorrencias.dto.TipoOcorrenciaResponse;
import br.com.fiap.ocorrencias.service.TipoOcorrenciaService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tipos-ocorrencia")
public class TipoOcorrenciaController {

    private final TipoOcorrenciaService service;

    public TipoOcorrenciaController(TipoOcorrenciaService service) {
        this.service = service;
    }

    @GetMapping
    public List<TipoOcorrenciaResponse> listar(@RequestParam(required = false) Long categoriaId) {
        return service.listar(categoriaId);
    }

    @GetMapping("/{id}")
    public TipoOcorrenciaResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<TipoOcorrenciaResponse> criar(@Valid @RequestBody TipoOcorrenciaRequest request) {
        TipoOcorrenciaResponse criado = service.criar(request);
        return ResponseEntity.created(URI.create("/tipos-ocorrencia/" + criado.id())).body(criado);
    }

    @PutMapping("/{id}")
    public TipoOcorrenciaResponse atualizar(@PathVariable Long id, @Valid @RequestBody TipoOcorrenciaRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
