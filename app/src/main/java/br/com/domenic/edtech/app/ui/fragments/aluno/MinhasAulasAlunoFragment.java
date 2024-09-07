package br.com.domenic.edtech.app.ui.fragments.aluno;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
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

import com.google.android.material.button.MaterialButton;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.domenic.edtech.app.R;
import br.com.domenic.edtech.app.models.AulaCurso;
import br.com.domenic.edtech.app.ui.adapters.AulasAdapter;
import br.com.domenic.edtech.app.ui.calendars.CustomCalendarDecorator;
import br.com.domenic.edtech.app.ui.viewmodels.AulaCursoViewModel;
import br.com.domenic.edtech.app.utils.SessionManager;
import br.com.domenic.edtech.app.utils.StringUtils;

public class MinhasAulasAlunoFragment extends Fragment {

    private AulaCursoViewModel aulaCursoViewModel;
    private AulasAdapter aulasAdapter;
    private MaterialCalendarView calendarView;
    private RecyclerView recyclerViewAulas;
    private int idAluno;
    private final Set<CalendarDay> datasPresenciais = new HashSet<>();
    private final Set<CalendarDay> datasOnline = new HashSet<>();
    private final Map<CalendarDay, AulaCurso> mapaAulas = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_minhas_aulas_aluno, container, false);

        // Inicializar variáveis
        initViews(view);
        initViewModel();

        // Obter o ID do aluno da sessão
        idAluno = new SessionManager(requireContext()).getUserId();

        // Carregar aulas e configurar o calendário e o RecyclerView
        carregarAulas();

        // Configurar listener de seleção de datas no calendário
        configurarListenerCalendario();

        return view;
    }

    private void initViews(View view) {
        calendarView = view.findViewById(R.id.calendarViewAulas);
        recyclerViewAulas = view.findViewById(R.id.recyclerViewMinhasAulas);
        recyclerViewAulas.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicializar o adapter
        aulasAdapter = new AulasAdapter(getContext(), new ArrayList<>(), this::onItemClick);
        recyclerViewAulas.setAdapter(aulasAdapter);
    }

    private void initViewModel() {
        aulaCursoViewModel = new ViewModelProvider(requireActivity()).get(AulaCursoViewModel.class);
    }

    private void carregarAulas() {
        aulaCursoViewModel.findAulasByAlunoId(idAluno).observe(getViewLifecycleOwner(), aulas -> {
            aulas.sort((a1, a2) -> a1.getDataAula().compareTo(a2.getDataAula())); // Ordena por data
            atualizarCalendarioERecyclerView(aulas);
        });
    }

    private void configurarListenerCalendario() {
        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            AulaCurso aulaSelecionada = mapaAulas.get(date);
            if (aulaSelecionada != null) {
                onItemClick(aulaSelecionada);  // Chama o método para lidar com o item clicado
            } else {
                Toast.makeText(getContext(), "Nenhuma aula agendada para esta data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void atualizarCalendarioERecyclerView(List<AulaCurso> aulas) {
        datasPresenciais.clear();
        datasOnline.clear();
        mapaAulas.clear(); // Limpar o mapa antes de recarregar as aulas

        for (AulaCurso aula : aulas) {
            CalendarDay dataAula = CalendarDay.from(aula.getDataAula());
            mapaAulas.put(dataAula, aula); // Mapeia a data para a aula correspondente
            if (aula.isAulaOnline()) {
                datasOnline.add(dataAula);
            } else {
                datasPresenciais.add(dataAula);
            }
        }

        atualizarDecoradoresCalendario();
        aulasAdapter.updateItems(aulas);  // Atualiza o adapter usando o método do BaseAdapter
    }

    private void atualizarDecoradoresCalendario() {
        calendarView.removeDecorators();  // Remover decoradores anteriores para evitar duplicação
        calendarView.addDecorator(new CustomCalendarDecorator(requireContext(), datasPresenciais, R.color.md_theme_primary));
        calendarView.addDecorator(new CustomCalendarDecorator(requireContext(), datasOnline, R.color.green));
    }

    private void onItemClick(AulaCurso aulaCurso) {
        View view = getLayoutInflater().inflate(R.layout.modal_aula_entrar, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(view);

        AlertDialog dialog = builder.create();

        configurarModalAula(view, aulaCurso, dialog);
        dialog.show();
    }

    private void configurarModalAula(View view, AulaCurso aulaCurso, AlertDialog dialog) {
        TextView tvAulaNome = view.findViewById(R.id.tvAulaCursoNome);
        TextView tvAulaDescricao = view.findViewById(R.id.tvAulaCursoDescricao);
        TextView tvAulaData = view.findViewById(R.id.tvAulaCursoData);
        TextView tvAulaModalidade = view.findViewById(R.id.tvTipoAulaCurso);
        MaterialButton btnEntrarReuniao = view.findViewById(R.id.btnAulaCursoIncreverSe);
        MaterialButton btnCancelar = view.findViewById(R.id.btnAulaCursoCancelar);

        tvAulaNome.setText(aulaCurso.getTitulo());
        tvAulaDescricao.setText(aulaCurso.getDescricao());
        tvAulaData.setText(StringUtils.formatarData(aulaCurso.getDataAula()));

        configurarModalAulaAcao(aulaCurso, tvAulaModalidade, btnEntrarReuniao);

        btnCancelar.setOnClickListener(v -> dialog.dismiss());
    }

    private void configurarModalAulaAcao(AulaCurso aulaCurso, TextView tvAulaModalidade, MaterialButton btnEntrarReuniao) {
        if (aulaCurso.isAulaOnline()) {
            configurarAulaOnline(aulaCurso, tvAulaModalidade, btnEntrarReuniao);
        } else {
            configurarAulaPresencial(tvAulaModalidade, btnEntrarReuniao);
        }
    }

    private void configurarAulaOnline(AulaCurso aulaCurso, TextView tvAulaModalidade, MaterialButton btnEntrarReuniao) {
        tvAulaModalidade.setText("Online");
        btnEntrarReuniao.setText("Entrar na Reunião");

        String linkAula = aulaCurso.getLinkAulaAoVivo();

        if (linkAula != null && !linkAula.isEmpty()) {
            btnEntrarReuniao.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkAula));
                startActivity(intent);
            });
        } else {
            btnEntrarReuniao.setOnClickListener(v -> {
                Toast.makeText(requireContext(), "Link da aula online não disponível", Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void configurarAulaPresencial(TextView tvAulaModalidade, MaterialButton btnEntrarReuniao) {
        tvAulaModalidade.setText("Presencial");
        btnEntrarReuniao.setText("Abrir Google Maps");

        btnEntrarReuniao.setOnClickListener(v -> {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=Av. Paulista, 1106 - 7º andar - Bela Vista, São Paulo - SP, 01311-000");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });
    }

    private void exibirModalAula(AlertDialog.Builder builder) {
        builder.create().show();
    }
}