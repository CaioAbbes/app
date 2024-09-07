package br.com.domenic.edtech.app.ui.fragments.aluno;

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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import br.com.domenic.edtech.app.R;
import br.com.domenic.edtech.app.models.Curso;
import br.com.domenic.edtech.app.models.AlunoCurso;
import br.com.domenic.edtech.app.models.ProfessorAvaliacao;
import br.com.domenic.edtech.app.ui.adapters.CursoAdapter;
import br.com.domenic.edtech.app.ui.adapters.ProfessorAdapterHomeAluno;
import br.com.domenic.edtech.app.ui.modals.aluno.AvaliarProfessorDialog;
import br.com.domenic.edtech.app.ui.modals.aluno.MatricularCursoDialog;
import br.com.domenic.edtech.app.ui.viewmodels.AlunoCursoViewModel;
import br.com.domenic.edtech.app.ui.viewmodels.ProfessorAvaliacaoViewModel;
import br.com.domenic.edtech.app.ui.viewmodels.ProfessorViewModel;
import br.com.domenic.edtech.app.ui.viewmodels.CursoViewModel;
import br.com.domenic.edtech.app.utils.SessionManager;

public class HomeAlunoFragment extends Fragment {

    private ProfessorViewModel professorViewModel;
    private CursoViewModel cursoViewModel;
    private AlunoCursoViewModel alunoCursoViewModel;
    private ProfessorAvaliacaoViewModel professorAvaliacaoViewModel;
    private RecyclerView recyclerViewProfessores, recyclerViewCursosNovidades, recyclerViewCursosDestaques, recyclerViewCursosParaVoce;
    private SessionManager sessionManager;
    private int alunoId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_aluno, container, false);

        recyclerViewProfessores = view.findViewById(R.id.recyclerViewCardsProfessores);
        recyclerViewCursosNovidades = view.findViewById(R.id.recyclerViewCardsCursosNovidades);
        recyclerViewCursosDestaques = view.findViewById(R.id.recyclerViewCardsCursosDestaques);
        recyclerViewCursosParaVoce = view.findViewById(R.id.recyclerViewCardsCursosParaVoce);

        recyclerViewProfessores.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCursosNovidades.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCursosDestaques.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCursosParaVoce.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        professorViewModel = new ViewModelProvider(requireActivity()).get(ProfessorViewModel.class);
        cursoViewModel = new ViewModelProvider(requireActivity()).get(CursoViewModel.class);
        alunoCursoViewModel = new ViewModelProvider(requireActivity()).get(AlunoCursoViewModel.class);
        professorAvaliacaoViewModel = new ViewModelProvider(requireActivity()).get(ProfessorAvaliacaoViewModel.class);

        sessionManager = new SessionManager(requireContext());

        alunoId = sessionManager.getUserId();

        // Observa os professores e configura o adapter para avaliar
        professorViewModel.getAll().observe(getViewLifecycleOwner(), professores -> {
            ProfessorAdapterHomeAluno professorAdapterHomeAluno = new ProfessorAdapterHomeAluno(getContext(), professores, professor -> {
                openAvaliarProfessorDialog(professor.getNomeCompleto(), professor.getIdProfessor());
            });
            recyclerViewProfessores.setAdapter(professorAdapterHomeAluno);
        });

        // Observa os cursos já aderidos pelo aluno e os cursos disponíveis
        alunoCursoViewModel.getCursosByAluno(alunoId).observe(getViewLifecycleOwner(), cursosAderidos -> {
            cursoViewModel.getAll().observe(getViewLifecycleOwner(), todosOsCursos -> {

                // Filtra cursos que o aluno ainda não aderiu
                List<Curso> cursosNaoAderidos = todosOsCursos.stream()
                        .filter(curso -> cursosAderidos.stream()
                                .noneMatch(cursoAderido -> cursoAderido.getIdCurso() == curso.getIdCurso()))
                        .collect(Collectors.toList());

                // Cria listas filtradas e ordenadas para as categorias
                List<Curso> cursosNovidades = new ArrayList<>(cursosNaoAderidos);
                List<Curso> cursosDestaques = new ArrayList<>(cursosNaoAderidos);
                List<Curso> cursosParaVoce = new ArrayList<>(cursosNaoAderidos);

                cursosNovidades.sort((curso1, curso2) -> curso2.getDataCriacao().compareTo(curso1.getDataCriacao()));
                cursosDestaques.sort((curso1, curso2) -> Integer.compare(curso2.getPopularidade(), curso1.getPopularidade()));
                cursosParaVoce.sort((curso1, curso2) -> Double.compare(curso2.getRecomendacao(), curso1.getRecomendacao()));

                // Configura os adapters para cada categoria
                CursoAdapter novidadesAdapter = new CursoAdapter(getContext(), cursosNovidades, curso -> openAderirCursoDialog(curso));
                CursoAdapter destaquesAdapter = new CursoAdapter(getContext(), cursosDestaques, curso -> openAderirCursoDialog(curso));
                CursoAdapter paraVoceAdapter = new CursoAdapter(getContext(), cursosParaVoce, curso -> openAderirCursoDialog(curso));

                recyclerViewCursosNovidades.setAdapter(novidadesAdapter);
                recyclerViewCursosDestaques.setAdapter(destaquesAdapter);
                recyclerViewCursosParaVoce.setAdapter(paraVoceAdapter);
            });
        });

        return view;
    }

    private void openAvaliarProfessorDialog(String professorNome, int idProfessor) {
        AvaliarProfessorDialog avaliarProfessorDialog = new AvaliarProfessorDialog(getContext(), professorNome);
        avaliarProfessorDialog.show();

        avaliarProfessorDialog.setOnAvaliarListener(v -> {
            int nota = Integer.parseInt(Float.toString(avaliarProfessorDialog.getNota()));
            String comentario = avaliarProfessorDialog.getComentario();

            ProfessorAvaliacao professorAvaliacao = new ProfessorAvaliacao(
                    idProfessor,
                    alunoId,
                    nota,
                    comentario,
                    new Date()
            );

            professorAvaliacaoViewModel.insert(professorAvaliacao, () -> {
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Avaliação enviada! Nota: " + nota + ", Comentário: " + comentario, Toast.LENGTH_SHORT).show();
                    avaliarProfessorDialog.dismiss();
                });
            });
        });
    }

    private void openAderirCursoDialog(Curso curso) {
        MatricularCursoDialog matricularCursoDialog = new MatricularCursoDialog(
                getContext(),
                curso.getNomeCurso(),
                curso.getDescricao(),
                curso.getPrecoCurso()
        );
        matricularCursoDialog.show();
        matricularCursoDialog.setOnAderirListener(v -> {
            Date dataMatricula = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dataMatricula);
            calendar.add(Calendar.YEAR, 1);
            Date dataConclusao = calendar.getTime();

            int idAluno = alunoId;
            int idCurso = curso.getIdCurso();
            String status = "Iniciado";
            int qtdCursoAulaParticular = 0;
            Double notaFinal = 0.0;
            String comentarios = "";

            AlunoCurso alunoCurso = new AlunoCurso(idAluno, idCurso, dataMatricula, dataConclusao, status, qtdCursoAulaParticular, notaFinal, comentarios);

            alunoCursoViewModel.insert(alunoCurso, () -> {
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Você aderiu ao curso: " + curso.getNomeCurso(), Toast.LENGTH_SHORT).show();
                    matricularCursoDialog.dismiss();
                });
            });
        });
    }

}