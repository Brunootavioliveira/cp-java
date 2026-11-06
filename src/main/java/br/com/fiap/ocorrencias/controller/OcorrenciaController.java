package br.com.fiap.ocorrencias.controller;

import br.com.fiap.ocorrencias.dto.OcorrenciaRequest;
import br.com.fiap.ocorrencias.dto.OcorrenciaResponse;
import br.com.fiap.ocorrencias.service.OcorrenciaService;
import jakarta.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
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
@RequestMapping("/ocorrencias")
public class OcorrenciaController {

    private final OcorrenciaService service;

    public OcorrenciaController(OcorrenciaService service) {
        this.service = service;
    }

    @GetMapping
    public List<OcorrenciaResponse> listar(
            @RequestParam(required = false) Long alunoId,
            @RequestParam(required = false) Long turmaId,
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(required = false) Long tipoId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        return service.listar(alunoId, turmaId, categoriaId, tipoId, dataInicio, dataFim);
    }

    @GetMapping("/{id}")
    public OcorrenciaResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<OcorrenciaResponse> criar(@Valid @RequestBody OcorrenciaRequest request) {
        OcorrenciaResponse criada = service.criar(request);
        return ResponseEntity.created(URI.create("/ocorrencias/" + criada.id())).body(criada);
    }

    @PutMapping("/{id}")
    public OcorrenciaResponse atualizar(@PathVariable Long id, @Valid @RequestBody OcorrenciaRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
