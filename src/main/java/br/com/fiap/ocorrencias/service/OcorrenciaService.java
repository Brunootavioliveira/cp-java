package br.com.fiap.ocorrencias.service;

import br.com.fiap.ocorrencias.dto.OcorrenciaRequest;
import br.com.fiap.ocorrencias.dto.OcorrenciaResponse;
import br.com.fiap.ocorrencias.exception.RecursoNaoEncontradoException;
import br.com.fiap.ocorrencias.exception.RegraNegocioException;
import br.com.fiap.ocorrencias.model.Aluno;
import br.com.fiap.ocorrencias.model.Categoria;
import br.com.fiap.ocorrencias.model.Ocorrencia;
import br.com.fiap.ocorrencias.model.TipoOcorrencia;
import br.com.fiap.ocorrencias.model.Turma;
import br.com.fiap.ocorrencias.model.Usuario;
import br.com.fiap.ocorrencias.repository.AlunoRepository;
import br.com.fiap.ocorrencias.repository.CategoriaRepository;
import br.com.fiap.ocorrencias.repository.OcorrenciaRepository;
import br.com.fiap.ocorrencias.repository.TipoOcorrenciaRepository;
import br.com.fiap.ocorrencias.repository.TurmaRepository;
import br.com.fiap.ocorrencias.repository.UsuarioRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OcorrenciaService {

    private static final String PERFIL_PROFESSOR = "PROFESSOR";

    private final OcorrenciaRepository ocorrencias;
    private final TurmaRepository turmas;
    private final AlunoRepository alunos;
    private final UsuarioRepository usuarios;
    private final CategoriaRepository categorias;
    private final TipoOcorrenciaRepository tipos;

    public OcorrenciaService(OcorrenciaRepository ocorrencias,
                             TurmaRepository turmas,
                             AlunoRepository alunos,
                             UsuarioRepository usuarios,
                             CategoriaRepository categorias,
                             TipoOcorrenciaRepository tipos) {
        this.ocorrencias = ocorrencias;
        this.turmas = turmas;
        this.alunos = alunos;
        this.usuarios = usuarios;
        this.categorias = categorias;
        this.tipos = tipos;
    }

    @Transactional(readOnly = true)
    public List<OcorrenciaResponse> listar(Long alunoId, Long turmaId, Long categoriaId, Long tipoId,
                                           LocalDate dataInicio, LocalDate dataFim) {
        return ocorrencias.filtrar(alunoId, turmaId, categoriaId, tipoId, dataInicio, dataFim)
                .stream()
                .map(OcorrenciaResponse::de)
                .toList();
    }

    @Transactional(readOnly = true)
    public OcorrenciaResponse buscar(Long id) {
        return OcorrenciaResponse.de(obter(id));
    }

    @Transactional
    public OcorrenciaResponse criar(OcorrenciaRequest request) {
        Ocorrencia ocorrencia = new Ocorrencia();
        preencher(ocorrencia, request);
        return OcorrenciaResponse.de(ocorrencias.save(ocorrencia));
    }

    @Transactional
    public OcorrenciaResponse atualizar(Long id, OcorrenciaRequest request) {
        Ocorrencia ocorrencia = obter(id);
        if (!ocorrencia.getUsuario().getId().equals(request.usuarioId())) {
            throw new RegraNegocioException("Somente o professor que registrou a ocorrência pode alterá-la");
        }
        preencher(ocorrencia, request);
        return OcorrenciaResponse.de(ocorrencias.save(ocorrencia));
    }

    @Transactional
    public void excluir(Long id) {
        ocorrencias.delete(obter(id));
    }

    private void preencher(Ocorrencia ocorrencia, OcorrenciaRequest request) {
        Turma turma = turmas.findById(request.turmaId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Turma não encontrada: " + request.turmaId()));
        Aluno aluno = alunos.findById(request.alunoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aluno não encontrado: " + request.alunoId()));
        Usuario usuario = usuarios.findById(request.usuarioId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado: " + request.usuarioId()));
        Categoria categoria = categorias.findById(request.categoriaId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada: " + request.categoriaId()));
        TipoOcorrencia tipo = tipos.findById(request.tipoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Tipo de ocorrência não encontrado: " + request.tipoId()));

        if (!PERFIL_PROFESSOR.equals(usuario.getPerfil().getNome())) {
            throw new RegraNegocioException("Somente usuários com perfil Professor podem registrar ocorrências");
        }
        if (!turmas.existsByIdAndAlunosId(turma.getId(), aluno.getId())) {
            throw new RegraNegocioException("O aluno informado não pertence à turma selecionada");
        }
        if (!tipo.getCategoria().getId().equals(categoria.getId())) {
            throw new RegraNegocioException("O tipo de ocorrência não pertence à categoria selecionada");
        }

        ocorrencia.setTurma(turma);
        ocorrencia.setAluno(aluno);
        ocorrencia.setUsuario(usuario);
        ocorrencia.setCategoria(categoria);
        ocorrencia.setTipo(tipo);
        ocorrencia.setData(request.data());
        ocorrencia.setHora(request.hora());
        ocorrencia.setDescricao(request.descricao());
    }

    private Ocorrencia obter(Long id) {
        return ocorrencias.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Ocorrência não encontrada: " + id));
    }
}
