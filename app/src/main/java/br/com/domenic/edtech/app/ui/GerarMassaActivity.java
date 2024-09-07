package br.com.domenic.edtech.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.domenic.edtech.app.R;
import br.com.domenic.edtech.app.database.AppDatabase;
import br.com.domenic.edtech.app.models.Aluno;
import br.com.domenic.edtech.app.models.AlunoCurso;
import br.com.domenic.edtech.app.models.AulaCurso;
import br.com.domenic.edtech.app.models.AulaCursoInscrito;
import br.com.domenic.edtech.app.models.Curso;
import br.com.domenic.edtech.app.models.CursoAvaliacao;
import br.com.domenic.edtech.app.models.Professor;
import br.com.domenic.edtech.app.models.ProfessorAvaliacao;
import br.com.domenic.edtech.app.ui.viewmodels.AlunoCursoViewModel;
import br.com.domenic.edtech.app.ui.viewmodels.AlunoViewModel;
import br.com.domenic.edtech.app.ui.viewmodels.AulaCursoInscritoViewModel;
import br.com.domenic.edtech.app.ui.viewmodels.AulaCursoViewModel;
import br.com.domenic.edtech.app.ui.viewmodels.CursoAvaliacaoViewModel;
import br.com.domenic.edtech.app.ui.viewmodels.CursoViewModel;
import br.com.domenic.edtech.app.ui.viewmodels.ProfessorAvaliacaoViewModel;
import br.com.domenic.edtech.app.ui.viewmodels.ProfessorViewModel;
import br.com.domenic.edtech.app.utils.AppUtils;
import br.com.domenic.edtech.app.utils.geradores.GerarMassa;

public class GerarMassaActivity extends AppCompatActivity {

    private AlunoCursoViewModel alunoCursoViewModel;
    private AlunoViewModel alunoViewModel;
    private AulaCursoInscritoViewModel aulaCursoInscritoViewModel;
    private AulaCursoViewModel aulaCursoViewModel;
    private CursoAvaliacaoViewModel cursoAvaliacaoViewModel;
    private CursoViewModel cursoViewModel;
    private ProfessorAvaliacaoViewModel professorAvaliacaoViewModel;
    private ProfessorViewModel professorViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.initVisuals(this, R.layout.activity_gerar_massa);

        // Inicializa os ViewModels
        initViewModels();

        // Configura os botões
        configurarBotoes();
    }

    private void initViewModels() {
        professorViewModel = new ViewModelProvider(this).get(ProfessorViewModel.class);
        cursoViewModel = new ViewModelProvider(this).get(CursoViewModel.class);
        aulaCursoViewModel = new ViewModelProvider(this).get(AulaCursoViewModel.class);
        alunoViewModel = new ViewModelProvider(this).get(AlunoViewModel.class);
        alunoCursoViewModel = new ViewModelProvider(this).get(AlunoCursoViewModel.class);
        aulaCursoInscritoViewModel = new ViewModelProvider(this).get(AulaCursoInscritoViewModel.class);
        cursoAvaliacaoViewModel = new ViewModelProvider(this).get(CursoAvaliacaoViewModel.class);
        professorAvaliacaoViewModel = new ViewModelProvider(this).get(ProfessorAvaliacaoViewModel.class);
    }

    private void configurarBotoes() {
        MaterialButton btnGerarMassa = findViewById(R.id.btnGerarMassa);
        MaterialButton btnDeletarMassa = findViewById(R.id.btnDeletarMassa);
        MaterialButton btnCancelar = findViewById(R.id.btnCancelar);

        // Ação para gerar massa de dados
        btnGerarMassa.setOnClickListener(v -> initGeradorMassa(true));

        // Ação para deletar massa de dados
        btnDeletarMassa.setOnClickListener(v -> deletarTodosOsDados());

        btnCancelar.setOnClickListener(v -> startLogin());
    }

    private void initGeradorMassa(boolean ativarGerarMassa) {
        if (!ativarGerarMassa) {
            startLogin();
        } else {
            GerarMassa gerarMassa = new GerarMassa(
                    professorViewModel,
                    cursoViewModel,
                    aulaCursoViewModel,
                    alunoViewModel,
                    alunoCursoViewModel,
                    this
            );

            Toast.makeText(this, "Gerando massa de dados...", Toast.LENGTH_SHORT).show();

            // Executa a geração de dados em uma thread separada
            new Thread(() -> {
                gerarMassa.gerarProfessorComCursoEAula(5, 3, 2);
                gerarMassa.gerarAlunoComCurso(5, 4);

                runOnUiThread(() -> {
                    Toast.makeText(this, "Geração de massa de dados finalizada!", Toast.LENGTH_SHORT).show();
                    startLogin();
                });
            }).start();
        }
    }

    private void deletarTodosOsDados() {
        AppDatabase database = AppDatabase.getInstance(this);

        // Cria um único executor para executar todas as tarefas em sequência
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // Printar todos os dados antes da exclusão
        executor.execute(() -> {
            printAllDatabaseData(database);
            // Excluir todas as dependências antes de excluir as entidades principais
            database.professorAvaliacaoDao().deleteAll();  // Tabela dependente de Professor
            database.cursoAvaliacaoDao().deleteAll();      // Tabela dependente de Curso
            database.alunoCursoDao().deleteAll();          // Tabela dependente de Aluno e Curso
            database.aulaCursoInscritoDao().deleteAll();   // Tabela dependente de AulaCurso e Aluno

            // Agora exclua as tabelas principais
            try {database.aulaCursoDao().deleteAll();} catch (Exception e) {Log.d("Exception", e.toString());}// Depende de Curso e Professor
            try {database.cursoDao().deleteAll();} catch (Exception e) {Log.d("Exception", e.toString());}  // Depende de Professor
            try {database.alunoDao().deleteAll();} catch (Exception e) {Log.d("Exception", e.toString());}  // Tabela principal de Aluno
            try {database.professorDao().deleteAll();} catch (Exception e) {Log.d("Exception", e.toString());} // Tabela principal de Professor

            // Printar todos os dados após a exclusão
            printAllDatabaseData(database);
        });

        // Fecha o executor após a conclusão das tarefas
        executor.shutdown();

        startLogin();
    }

    // Método para printar todos os dados do banco de dados
    private void printAllDatabaseData(AppDatabase database) {
        Log.d("Database", "=================== ANTES/DEPOIS DA EXCLUSÃO ===================");

        List<Professor> professores = database.professorDao().getAllDirect();
        List<Aluno> alunos = database.alunoDao().getAllDirect();
        List<Curso> cursos = database.cursoDao().getAllDirect();
        List<AulaCurso> aulasCurso = database.aulaCursoDao().getAllDirect();
        List<AlunoCurso> alunosCursos = database.alunoCursoDao().getAllDirect();
        List<AulaCursoInscrito> aulasCursoInscrito = database.aulaCursoInscritoDao().getAllDirect();
        List<CursoAvaliacao> cursoAvaliacoes = database.cursoAvaliacaoDao().getAllDirect();
        List<ProfessorAvaliacao> professorAvaliacoes = database.professorAvaliacaoDao().getAllDirect();

        Log.d("Database", "Professores: " + (professores != null && !professores.isEmpty() ? professores.toString() : "Nenhum professor encontrado"));
        Log.d("Database", "Alunos: " + (alunos != null && !alunos.isEmpty() ? alunos.toString() : "Nenhum aluno encontrado"));
        Log.d("Database", "Cursos: " + (cursos != null && !cursos.isEmpty() ? cursos.toString() : "Nenhum curso encontrado"));
        Log.d("Database", "AulasCurso: " + (aulasCurso != null && !aulasCurso.isEmpty() ? aulasCurso.toString() : "Nenhuma aula encontrada"));
        Log.d("Database", "AlunosCursos: " + (alunosCursos != null && !alunosCursos.isEmpty() ? alunosCursos.toString() : "Nenhuma relação aluno-curso encontrada"));
        Log.d("Database", "AulasCursoInscrito: " + (aulasCursoInscrito != null && !aulasCursoInscrito.isEmpty() ? aulasCursoInscrito.toString() : "Nenhuma inscrição em aula encontrada"));
        Log.d("Database", "CursoAvaliacoes: " + (cursoAvaliacoes != null && !cursoAvaliacoes.isEmpty() ? cursoAvaliacoes.toString() : "Nenhuma avaliação de curso encontrada"));
        Log.d("Database", "ProfessorAvaliacoes: " + (professorAvaliacoes != null && !professorAvaliacoes.isEmpty() ? professorAvaliacoes.toString() : "Nenhuma avaliação de professor encontrada"));
    }

    private void startLogin() {
        Intent intent = new Intent(GerarMassaActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}