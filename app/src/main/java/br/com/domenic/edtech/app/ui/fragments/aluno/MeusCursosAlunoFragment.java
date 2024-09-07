package br.com.domenic.edtech.app.ui.fragments.aluno;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import br.com.domenic.edtech.app.R;
import br.com.domenic.edtech.app.models.Curso;
import br.com.domenic.edtech.app.ui.adapters.CursoAdapter;
import br.com.domenic.edtech.app.ui.modals.aluno.IncreverAulaEmGrupoDialog;
import br.com.domenic.edtech.app.ui.modals.aluno.AvaliarCursoDialog;
import br.com.domenic.edtech.app.ui.modals.aluno.AgendarAulaParticularDialog; // Importação da nova classe
import br.com.domenic.edtech.app.ui.viewmodels.AlunoCursoViewModel;
import br.com.domenic.edtech.app.ui.viewmodels.AulaCursoInscritoViewModel;
import br.com.domenic.edtech.app.ui.viewmodels.AulaCursoViewModel;
import br.com.domenic.edtech.app.ui.viewmodels.CursoViewModel;
import br.com.domenic.edtech.app.utils.SessionManager;

public class MeusCursosAlunoFragment extends Fragment {

    private AlunoCursoViewModel alunoCursoViewModel;
    private AulaCursoViewModel aulaCursoViewModel;
    private AulaCursoInscritoViewModel aulaCursoInscritoViewModel;
    private CursoViewModel cursoViewModel;
    private RecyclerView recyclerViewMeusCursos;
    private SessionManager sessionManager;
    private int idAluno;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meus_cursos_aluno, container, false);

        initializeUI(view);
        initializeViewModels();
        initSessionManager();
        observeAlunoCursos();
        setupDeleteAllCursosListener(view);

        return view;
    }

    private void initSessionManager() {
        idAluno = sessionManager.getUserId();
    }

    // Inicializa os componentes de UI
    private void initializeUI(View view) {
        recyclerViewMeusCursos = view.findViewById(R.id.recyclerViewMeusCursos);
        recyclerViewMeusCursos.setLayoutManager(new LinearLayoutManager(getContext()));
        sessionManager = new SessionManager(requireContext());
    }

    // Inicializa os ViewModels
    private void initializeViewModels() {
        alunoCursoViewModel = new ViewModelProvider(requireActivity()).get(AlunoCursoViewModel.class);
        cursoViewModel = new ViewModelProvider(requireActivity()).get(CursoViewModel.class);
        aulaCursoInscritoViewModel = new ViewModelProvider(requireActivity()).get(AulaCursoInscritoViewModel.class);
        aulaCursoViewModel = new ViewModelProvider(requireActivity()).get(AulaCursoViewModel.class);
    }

    // Observa os cursos do aluno e configura o adapter
    private void observeAlunoCursos() {
        alunoCursoViewModel.getOnlyCursosByAluno(idAluno).observe(getViewLifecycleOwner(), cursos -> {
            CursoAdapter cursoAdapter = new CursoAdapter(getContext(), cursos, this::showCursoOptionsModal);
            recyclerViewMeusCursos.setAdapter(cursoAdapter);
        });
    }

    // Configura o listener para deletar todos os cursos
    private void setupDeleteAllCursosListener(View view) {
        TextView tvMeusCursos = view.findViewById(R.id.tvMeusCursos);
        tvMeusCursos.setOnClickListener(v -> confirmDeleteAllCursos());
    }

    // Confirmação para deletar todos os cursos
    private void confirmDeleteAllCursos() {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Confirmação")
                .setMessage("Você deseja apagar todos os seus cursos?")
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("Apagar", (dialog, which) -> deleteAllCursos())
                .show();
    }

    // Deleta todos os cursos do aluno
    private void deleteAllCursos() {
        alunoCursoViewModel.deleteAllCursosByAlunoId(idAluno, () -> {
            requireActivity().runOnUiThread(() -> {
                Toast.makeText(getContext(), "Todos os cursos foram apagados.", Toast.LENGTH_SHORT).show();
            });
        });
    }

    // Exibe o modal de opções de curso
    private void showCursoOptionsModal(Curso curso) {
        try {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.modal_curso_opcoes, null);
            setupCursoOptionsDialog(view, curso);
        } catch (Exception e) {
            handleError("Erro ao abrir opções", e);
        }
    }

    // Configura o modal de opções de curso
    private void setupCursoOptionsDialog(View view, Curso curso) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext()).setView(view);
        final androidx.appcompat.app.AlertDialog dialog = builder.create();

        view.findViewById(R.id.btnAulaParticular).setOnClickListener(v -> {
            dialog.dismiss();
            openAulaParticularDialog(curso); // Passa o curso para abrir o agendamento
        });

        view.findViewById(R.id.btnAulaGrupo).setOnClickListener(v -> {
            dialog.dismiss();
            openAulaGrupoDialog(curso);
        });

        view.findViewById(R.id.btnAvaliar).setOnClickListener(v -> {
            dialog.dismiss();
            openAvaliarCursoDialog(curso);
        });

        dialog.show();
    }

    // Abre o diálogo de aula particular (atualizado)
    private void openAulaParticularDialog(Curso curso) {
        try {
            // Chama o AgendarAulaParticularDialog para agendar uma aula particular
            AgendarAulaParticularDialog agendarDialog = new AgendarAulaParticularDialog(
                    getContext(),
                    aulaCursoViewModel,
                    aulaCursoInscritoViewModel,
                    curso.getIdCurso(),
                    idAluno
            );
            agendarDialog.show();
        } catch (Exception e) {
            handleError("Erro ao abrir Agendar Aula Particular", e);
        }
    }

    // Abre o diálogo de aula em grupo
    private void openAulaGrupoDialog(Curso curso) {
        try {
            IncreverAulaEmGrupoDialog increverAulaEmGrupoDialog = new IncreverAulaEmGrupoDialog(getContext(), aulaCursoViewModel, aulaCursoInscritoViewModel, curso.getIdCurso(), idAluno);
            increverAulaEmGrupoDialog.show();
        } catch (Exception e) {
            handleError("Erro ao abrir Aula em Grupo", e);
        }
    }

    // Abre o diálogo de avaliação de curso
    private void openAvaliarCursoDialog(Curso curso) {
        try {
            AvaliarCursoDialog avaliarCursoDialog = new AvaliarCursoDialog(getContext(), curso);
            avaliarCursoDialog.show();
            avaliarCursoDialog.setOnAvaliarListener(v -> saveCursoAvaliacao(avaliarCursoDialog, curso));
        } catch (Exception e) {
            handleError("Erro ao abrir Avaliação", e);
        }
    }

    // Salva a avaliação do curso
    private void saveCursoAvaliacao(AvaliarCursoDialog avaliarCursoDialog, Curso curso) {
        float nota = avaliarCursoDialog.getNota();
        String comentario = avaliarCursoDialog.getComentario();
        Toast.makeText(getContext(), "Avaliação salva! Nota: " + nota + " Comentário: " + comentario, Toast.LENGTH_SHORT).show();
        avaliarCursoDialog.dismiss();
    }

    // Tratamento de erros e exibição de mensagens
    private void handleError(String message, Exception e) {
        Toast.makeText(getContext(), message + ": " + e.getMessage(), Toast.LENGTH_SHORT).show();
    }
}