package br.com.domenic.edtech.app.ui.fragments.professor;

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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.domenic.edtech.app.R;
import br.com.domenic.edtech.app.models.AulaCurso;
import br.com.domenic.edtech.app.ui.adapters.AulasAdapter;
import br.com.domenic.edtech.app.ui.calendars.CustomCalendarDecorator;
import br.com.domenic.edtech.app.ui.viewmodels.AulaCursoViewModel;
import br.com.domenic.edtech.app.utils.SessionManager;
import br.com.domenic.edtech.app.utils.StringUtils;

public class MinhasAulasProfessorFragment extends Fragment {

    private MaterialCalendarView calendarView;
    private RecyclerView recyclerViewAulas;
    private AulasAdapter aulasAdapter;
    private AulaCursoViewModel aulaCursoViewModel;
    private int idProfessor;
    private final Set<CalendarDay> datasPresenciais = new HashSet<>();
    private final Set<CalendarDay> datasOnline = new HashSet<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_minhas_aulas_professor, container, false);

        // Inicializar variáveis e views
        calendarView = view.findViewById(R.id.calendarViewAulas);
        recyclerViewAulas = view.findViewById(R.id.recyclerViewMinhasAulas);
        recyclerViewAulas.setLayoutManager(new LinearLayoutManager(getContext()));

        // Configurar o ViewModel
        aulaCursoViewModel = new ViewModelProvider(requireActivity()).get(AulaCursoViewModel.class);

        // Configurar o Adapter
        aulasAdapter = new AulasAdapter(getContext(), new ArrayList<>(), this::onItemClick);
        recyclerViewAulas.setAdapter(aulasAdapter);

        // Obtendo o ID do professor da sessão
        SessionManager sessionManager = new SessionManager(requireContext());
        idProfessor = sessionManager.getUserId();

        // Observar as mudanças nos dados
        observeViewModels();

        return view;
    }

    private void observeViewModels() {
        aulaCursoViewModel.findAulasByProfessorId(idProfessor).observe(getViewLifecycleOwner(), aulas -> {
            // Ordenar por data da aula
            Collections.sort(aulas, Comparator.comparing(AulaCurso::getDataAula));
            updateCalendarView(aulas);
            updateRecyclerView(aulas);
        });
    }

    private void updateRecyclerView(List<AulaCurso> aulaCursoList) {
        aulasAdapter.updateItems(aulaCursoList); // Usar updateItems para atualizar o adapter
    }

    private void updateCalendarView(List<AulaCurso> aulaCursoList) {
        datasPresenciais.clear();
        datasOnline.clear();

        for (AulaCurso aulaCurso : aulaCursoList) {
            CalendarDay dataAula = CalendarDay.from(aulaCurso.getDataAula());
            if (aulaCurso.isAulaOnline()) {
                datasOnline.add(dataAula);
            } else {
                datasPresenciais.add(dataAula);
            }
        }

        // Adicionar decoradores ao calendário
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

        if (aulaCurso.isAulaOnline()) {
            configurarAulaOnline(aulaCurso, tvAulaModalidade, btnEntrarReuniao);
            dialog.dismiss();
        } else {
            configurarAulaPresencial(tvAulaModalidade, btnEntrarReuniao);
            dialog.dismiss();
        }

        btnCancelar.setOnClickListener(v -> dialog.dismiss());
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

    private void showRescheduleDialog() {
        View view = getLayoutInflater().inflate(R.layout.modal_aula_reagendar, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(view);

        MaterialCalendarView calendarView = view.findViewById(R.id.calendarViewAulaReagendar);

        MaterialButton btnReagendar = view.findViewById(R.id.btnReagendar);
        MaterialButton btnCancelarAula = view.findViewById(R.id.btnCancelarAula);

        btnReagendar.setOnClickListener(v -> {
            CalendarDay selectedDate = calendarView.getSelectedDate();
            if (selectedDate != null) {
                Toast.makeText(getContext(), "Aula reagendada para " + selectedDate.getDate(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Selecione uma data para reagendar", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancelarAula.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Aula cancelada", Toast.LENGTH_SHORT).show();
        });

        builder.setNeutralButton("Fechar", null);
        builder.create().show();
    }
}