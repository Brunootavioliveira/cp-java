package br.com.fiap.ocorrencias.config;

import br.com.fiap.ocorrencias.model.Aluno;
import br.com.fiap.ocorrencias.model.Categoria;
import br.com.fiap.ocorrencias.model.Perfil;
import br.com.fiap.ocorrencias.model.TipoOcorrencia;
import br.com.fiap.ocorrencias.model.Turma;
import br.com.fiap.ocorrencias.model.Turno;
import br.com.fiap.ocorrencias.model.Usuario;
import br.com.fiap.ocorrencias.repository.AlunoRepository;
import br.com.fiap.ocorrencias.repository.CategoriaRepository;
import br.com.fiap.ocorrencias.repository.PerfilRepository;
import br.com.fiap.ocorrencias.repository.TipoOcorrenciaRepository;
import br.com.fiap.ocorrencias.repository.TurmaRepository;
import br.com.fiap.ocorrencias.repository.UsuarioRepository;
import java.time.LocalDate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CargaInicial implements CommandLineRunner {

    private final PerfilRepository perfis;
    private final UsuarioRepository usuarios;
    private final CategoriaRepository categorias;
    private final TipoOcorrenciaRepository tipos;
    private final AlunoRepository alunos;
    private final TurmaRepository turmas;

    public CargaInicial(PerfilRepository perfis,
                        UsuarioRepository usuarios,
                        CategoriaRepository categorias,
                        TipoOcorrenciaRepository tipos,
                        AlunoRepository alunos,
                        TurmaRepository turmas) {
        this.perfis = perfis;
        this.usuarios = usuarios;
        this.categorias = categorias;
        this.tipos = tipos;
        this.alunos = alunos;
        this.turmas = turmas;
    }

    @Override
    public void run(String... args) {
        if (perfis.count() > 0) {
            return;
        }

        Perfil professor = perfis.save(new Perfil("PROFESSOR"));
        Perfil analista = perfis.save(new Perfil("ANALISTA_QUALIDADE"));
        Perfil coordenador = perfis.save(new Perfil("COORDENADOR"));
        Perfil administrativo = perfis.save(new Perfil("PROFESSOR_ADMINISTRATIVO"));

        usuarios.save(new Usuario("Carlos Souza", "professor", "123456", professor));
        usuarios.save(new Usuario("Ana Lima", "analista", "123456", analista));
        usuarios.save(new Usuario("Marta Rocha", "coordenador", "123456", coordenador));
        usuarios.save(new Usuario("Paulo Mendes", "administrativo", "123456", administrativo));

        Categoria disciplinar = categorias.save(new Categoria("Disciplinar"));
        Categoria pedagogica = categorias.save(new Categoria("Pedagógica"));
        Categoria administrativa = categorias.save(new Categoria("Administrativa"));

        tipos.save(new TipoOcorrencia("Indisciplina", disciplinar));
        tipos.save(new TipoOcorrencia("Rendimento", pedagogica));
        tipos.save(new TipoOcorrencia("Atraso", administrativa));

        Aluno joao = new Aluno();
        joao.setNome("João Pereira");
        joao.setDataNascimento(LocalDate.of(2006, 3, 14));
        joao = alunos.save(joao);

        Aluno maria = new Aluno();
        maria.setNome("Maria Santos");
        maria.setDataNascimento(LocalDate.of(2007, 7, 2));
        maria = alunos.save(maria);

        Turma turma = new Turma();
        turma.setNome("Turma A");
        turma.setTurno(Turno.NOITE);
        turma.setAno(2026);
        turma.setSemestre(2);
        turma.getAlunos().add(joao);
        turma.getAlunos().add(maria);
        turmas.save(turma);
    }
}
