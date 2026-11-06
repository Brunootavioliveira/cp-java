package br.com.fiap.ocorrencias.controller;

import br.com.fiap.ocorrencias.dto.AlunoResponse;
import br.com.fiap.ocorrencias.dto.TurmaRequest;
import br.com.fiap.ocorrencias.dto.TurmaResponse;
import br.com.fiap.ocorrencias.service.TurmaService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/turmas")
public class TurmaController {

    private final TurmaService service;

    public TurmaController(TurmaService service) {
        this.service = service;
    }

    @GetMapping
    public List<TurmaResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public TurmaResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<TurmaResponse> criar(@Valid @RequestBody TurmaRequest request) {
        TurmaResponse criada = service.criar(request);
        return ResponseEntity.created(URI.create("/turmas/" + criada.id())).body(criada);
    }

    @PutMapping("/{id}")
    public TurmaResponse atualizar(@PathVariable Long id, @Valid @RequestBody TurmaRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/alunos")
    public List<AlunoResponse> listarAlunos(@PathVariable Long id) {
        return service.listarAlunos(id);
    }

    @PostMapping("/{id}/alunos/{alunoId}")
    public ResponseEntity<Void> vincularAluno(@PathVariable Long id, @PathVariable Long alunoId) {
        service.vincularAluno(id, alunoId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/alunos/{alunoId}")
    public ResponseEntity<Void> desvincularAluno(@PathVariable Long id, @PathVariable Long alunoId) {
        service.desvincularAluno(id, alunoId);
        return ResponseEntity.noContent().build();
    }
}
