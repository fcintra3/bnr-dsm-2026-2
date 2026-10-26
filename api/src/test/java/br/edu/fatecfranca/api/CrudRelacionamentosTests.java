package br.edu.fatecfranca.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.edu.fatecfranca.api.controllers.*;
import br.edu.fatecfranca.api.models.*;
import br.edu.fatecfranca.api.repositories.*;
import br.edu.fatecfranca.api.services.*;

class CrudRelacionamentosTests {
    private AlunoRepository alunos;
    private CursoRepository cursos;
    private InstrutorRepository instrutores;
    private TurmaRepository turmas;
    private MatriculaRepository matriculas;
    private MockMvc mvc;

    @BeforeEach
    void preparar() {
        alunos = mock(AlunoRepository.class);
        cursos = mock(CursoRepository.class);
        instrutores = mock(InstrutorRepository.class);
        turmas = mock(TurmaRepository.class);
        matriculas = mock(MatriculaRepository.class);
        mvc = MockMvcBuilders.standaloneSetup(
                new AlunoController(new AlunoService(alunos, matriculas)),
                new CursoController(new CursoService(cursos, turmas)),
                new InstrutorController(new InstrutorService(instrutores)),
                new TurmaController(new TurmaService(turmas, cursos, matriculas)),
                new MatriculaController(new MatriculaService(matriculas, alunos, turmas)))
                .build();
    }

    private <T> void prepararCrud(MongoRepository<T, String> repository, T entidade) {
        when(repository.findAll()).thenReturn(List.of(entidade));
        when(repository.findById("id")).thenReturn(Optional.of(entidade));
        when(repository.existsById("id")).thenReturn(true);
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @ParameterizedTest
    @CsvSource({
        "alunos,Aluno",
        "cursos,Curso",
        "instrutores,Instrutor",
        "turmas,Turma",
        "matriculas,Matricula"
    })
    void crudRetornaStatusEsperados(String rota, String tipo) throws Exception {
        prepararCrud(alunos, new Aluno());
        prepararCrud(cursos, new Curso());
        prepararCrud(instrutores, new Instrutor());
        prepararCrud(turmas, new Turma());
        prepararCrud(matriculas, new Matricula());
        String json = switch (tipo) {
            case "Instrutor" -> """
                {"nome":"Marcos", "especialidades":["Java"],
                 "enderecos":[{"logradouro":"Rua das Acácias","numero":"250",
                    "municipio":"Franca","uf":"SP","cep":"14400000"}]}
                """;
            case "Turma" -> """
                {"cursoId":"id","instrutorId":"id","inicio":"2026-10-05",
                 "fim":"2026-12-16","vagas":30,
                 "horarios":[{"diaSemana":"segunda-feira","inicio":"19:00","fim":"21:00"}]}
                """;
            case "Matricula" -> """
                {"alunoId":"id","turmaId":"id","data":"2026-10-19",
                 "situacao":"ativa","notas":[{"avaliacao":"Atividade prática 1","valor":8.5}]}
                """;
            default -> "{\"nome\":\"Exemplo\"}";
        };
        mvc.perform(post("/" + rota).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated());
        mvc.perform(get("/" + rota)).andExpect(status().isOk());
        mvc.perform(get("/" + rota + "/id")).andExpect(status().isOk());
        mvc.perform(put("/" + rota + "/id").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").value("id"));
        mvc.perform(delete("/" + rota + "/id")).andExpect(status().isNoContent());
        mvc.perform(get("/" + rota + "/ausente")).andExpect(status().isNotFound());
        mvc.perform(put("/" + rota + "/ausente").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isNotFound());
        mvc.perform(delete("/" + rota + "/ausente")).andExpect(status().isNotFound());
        if (tipo.equals("Turma")) {
            verify(turmas, times(2)).save(argThat(t -> t.getHorarios().size() == 1
                    && t.getHorarios().get(0).getInicio().getHour() == 19));
        } else if (tipo.equals("Instrutor")) {
            verify(instrutores, times(2)).save(argThat(i -> i.getEnderecos().size() == 1
                    && i.getEspecialidades().contains("Java")));
        } else if (tipo.equals("Matricula")) {
            verify(matriculas, times(2)).save(argThat(m -> m.getNotas().get(0).getValor() == 8.5));
        }
    }

    @ParameterizedTest
    @CsvSource({"POST,turmas,cursoId", "PUT,turmas,cursoId",
        "POST,matriculas,turmaId", "PUT,matriculas,turmaId",
        "POST,matriculas,alunoId", "PUT,matriculas,alunoId"})
    void vinculosInvalidosImpedemGravacao(String verbo, String rota, String campo) throws Exception {
        when(alunos.existsById("id")).thenReturn(true);
        when(turmas.existsById("id")).thenReturn(true);
        when(matriculas.findById("id")).thenReturn(Optional.of(new Matricula()));
        when(turmas.findById("id")).thenReturn(Optional.of(new Turma()));
        for (String valor : new String[]{null, "", "   ", "ausente", "omitir"}) {
            String json = "{";
            for (String identificador : rota.equals("turmas")
                    ? List.of("cursoId") : List.of("alunoId", "turmaId")) {
                if (identificador.equals(campo) && "omitir".equals(valor)) {
                    continue;
                }
                if (json.length() > 1) {
                    json += ",";
                }
                String conteudo = identificador.equals(campo) ? valor : "id";
                json += "\"" + identificador + "\":"
                        + (conteudo == null ? "null" : "\"" + conteudo + "\"");
            }
            json += "}";
            mvc.perform(request(org.springframework.http.HttpMethod.valueOf(verbo),
                    "/" + rota + (verbo.equals("PUT") ? "/id" : ""))
                    .contentType(MediaType.APPLICATION_JSON).content(json))
                    .andExpect(status().is(valor != null && valor.equals("ausente") ? 404 : 400));
        }
        verify(matriculas, never()).save(any());
        verify(turmas, never()).save(any());
    }

    @Test
    void exclusaoComDependenciasRetornaConflitoSemExcluir() throws Exception {
        when(alunos.existsById("id")).thenReturn(true);
        when(cursos.existsById("id")).thenReturn(true);
        when(turmas.existsById("id")).thenReturn(true);
        when(cursos.findById("id")).thenReturn(Optional.of(new Curso()));
        when(turmas.findById("id")).thenReturn(Optional.of(new Turma()));
        when(matriculas.existsByAlunoId("id")).thenReturn(true);
        when(matriculas.existsByTurmaId("id")).thenReturn(true);
        when(turmas.existsByCursoId("id")).thenReturn(true);
        for (String rota : List.of("alunos", "cursos", "turmas")) {
            mvc.perform(delete("/" + rota + "/id")).andExpect(status().isConflict());
        }
        verify(alunos, never()).deleteById(anyString());
        verify(cursos, never()).deleteById(anyString());
        verify(turmas, never()).deleteById(anyString());
    }

    @Test
    void consultasPorVinculoDiferenciamListaVaziaDePaiInexistente() throws Exception {
        when(alunos.existsById("id")).thenReturn(true);
        when(turmas.existsById("id")).thenReturn(true);
        when(cursos.existsById("id")).thenReturn(true);
        when(matriculas.findByAlunoId("id")).thenReturn(List.of());
        when(matriculas.findByTurmaId("id")).thenReturn(List.of());
        when(turmas.findByCursoId("id")).thenReturn(List.of());
        for (String rota : List.of("/matriculas/aluno/", "/matriculas/turma/", "/turmas/curso/")) {
            mvc.perform(get(rota + "id")).andExpect(status().isOk()).andExpect(content().json("[]"));
            mvc.perform(get(rota + "ausente")).andExpect(status().isNotFound());
        }
        when(matriculas.findByTurmaId("id")).thenReturn(List.of(new Matricula()));
        when(matriculas.findByAlunoId("id")).thenReturn(List.of(new Matricula()));
        when(turmas.findByCursoId("id")).thenReturn(List.of(new Turma()));
        mvc.perform(get("/matriculas/turma/id")).andExpect(jsonPath("$.length()").value(1));
        mvc.perform(get("/matriculas/aluno/id")).andExpect(jsonPath("$.length()").value(1));
        mvc.perform(get("/turmas/curso/id")).andExpect(jsonPath("$.length()").value(1));
    }
}
