package br.com.fiap.ocorrencias.service;

import br.com.fiap.ocorrencias.dto.AlunoResponse;
import br.com.fiap.ocorrencias.dto.TurmaRequest;
import br.com.fiap.ocorrencias.dto.TurmaResponse;
import br.com.fiap.ocorrencias.exception.RecursoNaoEncontradoException;
import br.com.fiap.ocorrencias.exception.RegraNegocioException;
import br.com.fiap.ocorrencias.model.Aluno;
import br.com.fiap.ocorrencias.model.Turma;
import br.com.fiap.ocorrencias.repository.AlunoRepository;
import br.com.fiap.ocorrencias.repository.TurmaRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TurmaService {

    private final TurmaRepository turmas;
    private final AlunoRepository alunos;

    public TurmaService(TurmaRepository turmas, AlunoRepository alunos) {
        this.turmas = turmas;
        this.alunos = alunos;
    }

    @Transactional(readOnly = true)
    public List<TurmaResponse> listar() {
        return turmas.findAll().stream().map(TurmaResponse::de).toList();
    }

    @Transactional(readOnly = true)
    public TurmaResponse buscar(Long id) {
        return TurmaResponse.de(obter(id));
    }

    @Transactional
    public TurmaResponse criar(TurmaRequest request) {
        Turma turma = new Turma();
        preencher(turma, request);
        return TurmaResponse.de(turmas.save(turma));
    }

    @Transactional
    public TurmaResponse atualizar(Long id, TurmaRequest request) {
        Turma turma = obter(id);
        preencher(turma, request);
        return TurmaResponse.de(turmas.save(turma));
    }

    @Transactional
    public void excluir(Long id) {
        turmas.delete(obter(id));
        turmas.flush();
    }

    @Transactional(readOnly = true)
    public List<AlunoResponse> listarAlunos(Long turmaId) {
        return obter(turmaId).getAlunos().stream().map(AlunoResponse::de).toList();
    }

    @Transactional
    public void vincularAluno(Long turmaId, Long alunoId) {
        Turma turma = obter(turmaId);
        Aluno aluno = alunos.findById(alunoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aluno não encontrado: " + alunoId));
        if (turmas.existsByIdAndAlunosId(turmaId, alunoId)) {
            throw new RegraNegocioException("O aluno já está vinculado a esta turma");
        }
        turma.getAlunos().add(aluno);
        turmas.save(turma);
    }

    @Transactional
    public void desvincularAluno(Long turmaId, Long alunoId) {
        Turma turma = obter(turmaId);
        boolean removido = turma.getAlunos().removeIf(aluno -> aluno.getId().equals(alunoId));
        if (!removido) {
            throw new RecursoNaoEncontradoException("O aluno não está vinculado a esta turma");
        }
        turmas.save(turma);
    }

    private void preencher(Turma turma, TurmaRequest request) {
        turma.setNome(request.nome());
        turma.setTurno(request.turno());
        turma.setAno(request.ano());
        turma.setSemestre(request.semestre());
    }

    private Turma obter(Long id) {
        return turmas.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Turma não encontrada: " + id));
    }
}
