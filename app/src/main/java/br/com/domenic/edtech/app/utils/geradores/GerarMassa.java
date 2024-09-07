package br.com.domenic.edtech.app.utils.geradores;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import br.com.domenic.edtech.app.models.Aluno;
import br.com.domenic.edtech.app.models.AlunoCurso;
import br.com.domenic.edtech.app.models.AulaCurso;
import br.com.domenic.edtech.app.models.Curso;
import br.com.domenic.edtech.app.models.Professor;
import br.com.domenic.edtech.app.ui.viewmodels.AlunoCursoViewModel;
import br.com.domenic.edtech.app.ui.viewmodels.AlunoViewModel;
import br.com.domenic.edtech.app.ui.viewmodels.AulaCursoViewModel;
import br.com.domenic.edtech.app.ui.viewmodels.CursoViewModel;
import br.com.domenic.edtech.app.ui.viewmodels.ProfessorViewModel;

public class GerarMassa {

    private final ProfessorViewModel professorViewModel;
    private final CursoViewModel cursoViewModel;
    private final AulaCursoViewModel aulaCursoViewModel;
    private final AlunoViewModel alunoViewModel;
    private final AlunoCursoViewModel alunoCursoViewModel;
    private final Executor executor = Executors.newSingleThreadExecutor();

    private int contadorProfessor;
    private int contadorAluno;
    private final AppCompatActivity activity;

    public GerarMassa(ProfessorViewModel professorViewModel, CursoViewModel cursoViewModel,
                      AulaCursoViewModel aulaCursoViewModel, AlunoViewModel alunoViewModel,
                      AlunoCursoViewModel alunoCursoViewModel, AppCompatActivity activity) {
        this.professorViewModel = professorViewModel;
        this.cursoViewModel = cursoViewModel;
        this.aulaCursoViewModel = aulaCursoViewModel;
        this.alunoViewModel = alunoViewModel;
        this.alunoCursoViewModel = alunoCursoViewModel;
        this.activity = activity;

        initContadores();
    }

    // Inicializa os contadores de professores e alunos
    private void initContadores() {
        activity.runOnUiThread(() -> {
            professorViewModel.getAll().observe(activity, professores -> {
                contadorProfessor = professores != null ? professores.size() : 0;
            });
            alunoViewModel.getAll().observe(activity, alunos -> {
                contadorAluno = alunos != null ? alunos.size() : 0;
            });
        });
    }

    // Gera professores, cursos e aulas
    public void gerarProfessorComCursoEAula(int qtdProfessores, int qtdCursoParaCadaProfessor, int qtdAulaParaCadaCurso) {
        executor.execute(() -> {
            for (int i = 0; i < qtdProfessores; i++) {
                Professor professor = GerarProfessor.gerarProfessor(++contadorProfessor);
                professorViewModel.insert(professor, () -> activity.runOnUiThread(() -> buscarProfessor(professor, qtdCursoParaCadaProfessor, qtdAulaParaCadaCurso)));
            }
        });
    }

    // Busca o professor inserido e gera cursos para ele
    private void buscarProfessor(Professor professor, int qtdCursoParaCadaProfessor, int qtdAulaParaCadaCurso) {
        LiveData<List<Professor>> professorLiveData = professorViewModel.getAll();
        professorLiveData.observe(activity, professores -> {
            // Remove o observer para evitar execuções repetidas
            professorLiveData.removeObservers(activity);

            Professor professorInserido = professores.stream()
                    .filter(p -> p.getNomeCompleto().equals(professor.getNomeCompleto()))
                    .findFirst().orElse(null);

            if (professorInserido != null) {
                gerarCursosParaProfessor(professorInserido.getIdProfessor(), qtdCursoParaCadaProfessor, qtdAulaParaCadaCurso);
            }
        });
    }

    // Gera cursos para um professor específico
    private void gerarCursosParaProfessor(int idProfessor, int qtdCursoParaCadaProfessor, int qtdAulaParaCadaCurso) {
        for (int j = 0; j < qtdCursoParaCadaProfessor; j++) {
            Curso curso = GerarCurso.gerarCurso(idProfessor);
            cursoViewModel.insert(curso, () -> activity.runOnUiThread(() -> buscarCurso(curso, idProfessor, qtdAulaParaCadaCurso)));
        }
    }

    // Busca o curso inserido e gera aulas para ele
    private void buscarCurso(Curso curso, int idProfessor, int qtdAulaParaCadaCurso) {
        LiveData<List<Curso>> cursoLiveData = cursoViewModel.getAll();
        cursoLiveData.observe(activity, cursos -> {
            // Remove o observer para evitar execuções repetidas
            cursoLiveData.removeObservers(activity);

            Curso cursoInserido = cursos.stream()
                    .filter(c -> c.getIdProfessor() == idProfessor && c.getNomeCurso().equals(curso.getNomeCurso()))
                    .findFirst().orElse(null);

            if (cursoInserido != null) {
                gerarAulasParaCurso(cursoInserido.getIdCurso(), idProfessor, qtdAulaParaCadaCurso);
            }
        });
    }

    // Gera aulas para um curso específico
    private void gerarAulasParaCurso(int idCurso, int idProfessor, int qtdAulaParaCadaCurso) {
        for (int k = 0; k < qtdAulaParaCadaCurso; k++) {
            AulaCurso aulaCurso = GerarAulaCurso.gerarAulaCurso(idCurso, idProfessor);
            aulaCursoViewModel.insert(aulaCurso, () -> Log.d("GerarMassa", "Aula cadastrada com sucesso: Curso ID " + idCurso));
        }
    }

    // Gera alunos e associa cursos existentes
    public void gerarAlunoComCurso(int qtdAlunos, int qtdCursoParaCadaAluno) {
        executor.execute(() -> {
            for (int i = 0; i < qtdAlunos; i++) {
                Aluno aluno = GerarAluno.gerarAluno(++contadorAluno);
                alunoViewModel.insert(aluno, () -> activity.runOnUiThread(() -> buscarAluno(aluno, qtdCursoParaCadaAluno)));
            }
        });
    }

    // Busca o aluno inserido e associa cursos a ele
    private void buscarAluno(Aluno aluno, int qtdCursoParaCadaAluno) {
        LiveData<List<Aluno>> alunoLiveData = alunoViewModel.getAll();
        alunoLiveData.observe(activity, alunos -> {
            // Remove o observer para evitar execuções repetidas
            alunoLiveData.removeObservers(activity);

            Aluno alunoInserido = alunos.stream()
                    .filter(a -> a.getNomeCompleto().equals(aluno.getNomeCompleto()))
                    .findFirst().orElse(null);

            if (alunoInserido != null) {
                gerarCursosParaAluno(alunoInserido.getIdAluno(), qtdCursoParaCadaAluno);
            }
        });
    }

    // Gera cursos para um aluno específico, obtendo cursos já existentes
    private void gerarCursosParaAluno(int idAluno, int qtdCursoParaCadaAluno) {
        LiveData<List<Curso>> cursosLiveData = cursoViewModel.getAll();
        cursosLiveData.observe(activity, cursosExistentes -> {
            // Remove o observer para evitar execuções repetidas
            cursosLiveData.removeObservers(activity);

            if (cursosExistentes != null && !cursosExistentes.isEmpty()) {
                for (int j = 0; j < qtdCursoParaCadaAluno; j++) {
                    Curso cursoAleatorio = cursosExistentes.get(new Random().nextInt(cursosExistentes.size()));
                    gerarAlunoCurso(idAluno, cursoAleatorio.getIdCurso());
                }
            } else {
                Log.e("GerarMassa", "Nenhum curso disponível na base de dados.");
            }
        });
    }

    // Método para associar o aluno ao curso
    private void gerarAlunoCurso(int idAluno, int idCurso) {
        AlunoCurso alunoCurso = GerarAlunoCurso.gerarAlunoCurso(idAluno, idCurso);
        alunoCursoViewModel.insert(alunoCurso, () -> Log.d("GerarMassa", "Curso associado ao aluno com sucesso: Curso ID " + idCurso));
    }
}