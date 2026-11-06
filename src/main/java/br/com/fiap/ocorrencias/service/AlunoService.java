package br.com.fiap.ocorrencias.service;

import br.com.fiap.ocorrencias.dto.AlunoRequest;
import br.com.fiap.ocorrencias.dto.AlunoResponse;
import br.com.fiap.ocorrencias.exception.RecursoNaoEncontradoException;
import br.com.fiap.ocorrencias.model.Aluno;
import br.com.fiap.ocorrencias.repository.AlunoRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlunoService {

    private final AlunoRepository alunos;

    public AlunoService(AlunoRepository alunos) {
        this.alunos = alunos;
    }

    @Transactional(readOnly = true)
    public List<AlunoResponse> listar(String nome) {
        List<Aluno> resultado = (nome == null || nome.isBlank())
                ? alunos.findAll()
                : alunos.findByNomeContainingIgnoreCase(nome.trim());
        return resultado.stream().map(AlunoResponse::de).toList();
    }

    @Transactional(readOnly = true)
    public AlunoResponse buscar(Long id) {
        return AlunoResponse.de(obter(id));
    }

    @Transactional
    public AlunoResponse criar(AlunoRequest request) {
        Aluno aluno = new Aluno();
        preencher(aluno, request);
        return AlunoResponse.de(alunos.save(aluno));
    }

    @Transactional
    public AlunoResponse atualizar(Long id, AlunoRequest request) {
        Aluno aluno = obter(id);
        preencher(aluno, request);
        return AlunoResponse.de(alunos.save(aluno));
    }

    @Transactional
    public void excluir(Long id) {
        alunos.delete(obter(id));
        alunos.flush();
    }

    private void preencher(Aluno aluno, AlunoRequest request) {
        aluno.setNome(request.nome());
        aluno.setDataNascimento(request.dataNascimento());
        aluno.setFoto(request.foto());
    }

    private Aluno obter(Long id) {
        return alunos.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aluno não encontrado: " + id));
    }
}
