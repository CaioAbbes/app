package br.com.domenic.edtech.app.ui.fragments.professor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.com.domenic.edtech.app.R;
import br.com.domenic.edtech.app.models.Curso;
import br.com.domenic.edtech.app.ui.adapters.CursoAdapter;
import br.com.domenic.edtech.app.ui.modals.professor.CadastrarAulaEmGrupoDialog;
import br.com.domenic.edtech.app.ui.modals.professor.CadastrarCursoDialog;
import br.com.domenic.edtech.app.ui.modals.professor.CursoOptionProfessorDialog;
import br.com.domenic.edtech.app.ui.modals.professor.DeletarCursoDialog;
import br.com.domenic.edtech.app.ui.modals.professor.EditarCursoDialog;
import br.com.domenic.edtech.app.ui.viewmodels.AulaCursoViewModel;
import br.com.domenic.edtech.app.ui.viewmodels.CursoAvaliacaoViewModel;
import br.com.domenic.edtech.app.ui.viewmodels.CursoViewModel;
import br.com.domenic.edtech.app.utils.SessionManager;

public class MeusCursosProfessorFragment extends Fragment {

    private CursoViewModel cursoViewModel;
    private CursoAvaliacaoViewModel cursoAvaliacaoViewModel;
    private AulaCursoViewModel aulaCursoViewModel;
    private RecyclerView recyclerViewCardsMeusCursos;
    private FloatingActionButton fabAddCurso;
    private SessionManager sessionManager;
    private int professorId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meus_cursos_professor, container, false);

        recyclerViewCardsMeusCursos = view.findViewById(R.id.recyclerViewMeusCursos);
        fabAddCurso = view.findViewById(R.id.fabAddCurso);

        recyclerViewCardsMeusCursos.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicializando o SessionManager e obtendo o ID do professor
        sessionManager = new SessionManager(requireContext());
        professorId = sessionManager.getUserId();

        if (!"professor".equals(sessionManager.getUserType())) {
            // Garante que o Toast seja mostrado na UI thread
            requireActivity().runOnUiThread(() ->
                    Toast.makeText(getContext(), "Acesso restrito a professores.", Toast.LENGTH_SHORT).show()
            );
            requireActivity().onBackPressed();
            return view;
        }

        // Inicializando o ViewModel
        cursoViewModel = new ViewModelProvider(requireActivity()).get(CursoViewModel.class);
        cursoAvaliacaoViewModel = new ViewModelProvider(requireActivity()).get(CursoAvaliacaoViewModel .class);
        aulaCursoViewModel = new ViewModelProvider(requireActivity()).get(AulaCursoViewModel.class);

        cursoViewModel.findByProfessorId(professorId).observe(getViewLifecycleOwner(), cursos -> {
            cursoAvaliacaoViewModel.getAll().observe(getViewLifecycleOwner(), avaliacoes -> {
                CursoAdapter cursoAdapter = new CursoAdapter(getContext(), cursos, avaliacoes, curso -> {
                    try {
                        showCursoOptionsModal(curso);
                    } catch (Exception e) {
                        // Garante que o Toast seja mostrado na UI thread
                        requireActivity().runOnUiThread(() ->
                                Toast.makeText(getContext(), "Erro ao abrir opções: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                        );
                    }
                });
                recyclerViewCardsMeusCursos.setAdapter(cursoAdapter);
            });
        });

        fabAddCurso.setOnClickListener(v -> {
            try {
                showAddCursoDialog();
            } catch (Exception e) {
                // Garante que o Toast seja mostrado na UI thread
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(getContext(), "Erro ao abrir diálogo de adição: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
            }
        });

        return view;
    }

    // No método que lida com as opções do curso
    private void showCursoOptionsModal(Curso curso) {
        CursoOptionProfessorDialog dialog = new CursoOptionProfessorDialog(getContext(), curso, new CursoOptionProfessorDialog.OnCursoOptionDialogListener() {
            @Override
            public void onEditCurso(Curso curso) {
                showEditCursoDialog(curso);
            }

            @Override
            public void onDeleteCurso(Curso curso) {
                showDeleteCursoConfirmationDialog(curso);
            }

            @Override
            public void onAgendarAulaEmGrupo(Curso curso) {
                CadastrarAulaEmGrupoDialog cadastrarAulaEmGrupoDialog = new CadastrarAulaEmGrupoDialog(getContext(), aulaCursoViewModel, curso.getIdCurso(), professorId, getViewLifecycleOwner());
                cadastrarAulaEmGrupoDialog.show();
            }
        });
        dialog.show();
    }

    private void showEditCursoDialog(Curso curso) {
        EditarCursoDialog dialog = new EditarCursoDialog(getContext(), curso, updatedCurso -> {
            cursoViewModel.update(updatedCurso);
            // Garante que o Toast seja mostrado na UI thread
            requireActivity().runOnUiThread(() ->
                    Toast.makeText(getContext(), "Curso atualizado com sucesso!", Toast.LENGTH_SHORT).show()
            );
        });
        dialog.show();
    }

    private void showDeleteCursoConfirmationDialog(Curso curso) {
        DeletarCursoDialog dialog = new DeletarCursoDialog(getContext(), curso, cursoToDelete -> {
            cursoViewModel.delete(cursoToDelete);
            // Garante que o Toast seja mostrado na UI thread
            requireActivity().runOnUiThread(() ->
                    Toast.makeText(getContext(), "Curso deletado com sucesso!", Toast.LENGTH_SHORT).show()
            );
        });
        dialog.show();
    }

    private void showAddCursoDialog() {
        CadastrarCursoDialog dialog = new CadastrarCursoDialog(requireContext(), (nomeCurso, descricaoCurso, duracaoCurso, precoCurso, precoCursoAulaParticular, dataCriacao, popularidade, recomendacao) -> {
            Curso novoCurso = new Curso(nomeCurso, descricaoCurso, duracaoCurso, precoCurso, precoCursoAulaParticular, professorId, dataCriacao, popularidade, recomendacao);

            cursoViewModel.insert(novoCurso, () -> {
                // Garante que o Toast seja mostrado na UI thread
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(getContext(), "Curso cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                );
            });
        });
        dialog.show();
    }
}