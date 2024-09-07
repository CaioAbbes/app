package br.com.domenic.edtech.app.ui.modals.aluno;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.LifecycleOwner;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import br.com.domenic.edtech.app.R;
import br.com.domenic.edtech.app.models.AulaCurso;
import br.com.domenic.edtech.app.models.AulaCursoInscrito;
import br.com.domenic.edtech.app.ui.calendars.CustomCalendarDecorator;
import br.com.domenic.edtech.app.ui.viewmodels.AulaCursoInscritoViewModel;
import br.com.domenic.edtech.app.ui.viewmodels.AulaCursoViewModel;
import br.com.domenic.edtech.app.utils.StringUtils;

public class IncreverAulaEmGrupoDialog {
    private final Dialog dialog;
    private final MaterialCalendarView calendarView;
    private final Context context;
    private final AulaCursoViewModel aulaCursoViewModel;
    private final AulaCursoInscritoViewModel aulaCursoInscritoViewModel;
    private final Map<CalendarDay, AulaCurso> aulaMap = new HashMap<>();
    private final int idAluno;

    public IncreverAulaEmGrupoDialog(Context context, AulaCursoViewModel aulaCursoViewModel, AulaCursoInscritoViewModel aulaCursoInscritoViewModel, int idCurso, int idAluno) {
        this.context = context;
        this.aulaCursoViewModel = aulaCursoViewModel;
        this.aulaCursoInscritoViewModel = aulaCursoInscritoViewModel;
        this.idAluno = idAluno;

        View view = LayoutInflater.from(context).inflate(R.layout.modal_aula_em_grupo, null);
        dialog = new Dialog(context);
        dialog.setContentView(view);
        dialog.setCancelable(true);

        calendarView = view.findViewById(R.id.calendarViewAulaEmGrupo);

        configurarCalendario(idCurso);
        configurarBotoes(view);
    }

    // Exibe o diálogo
    public void show() {
        dialog.show();
    }

    // Configuração inicial do calendário
    private void configurarCalendario(int idCurso) {
        aulaCursoViewModel.findAulasByCursoId(idCurso).observe((LifecycleOwner) context, aulasGrupo -> {
            Set<CalendarDay> datasPresenciais = new HashSet<>();
            Set<CalendarDay> datasOnline = new HashSet<>();

            for (AulaCurso aula : aulasGrupo) {
                CalendarDay day = CalendarDay.from(aula.getDataAula());
                aulaMap.put(day, aula);

                if (aula.isAulaOnline()) {
                    datasOnline.add(day);
                } else {
                    datasPresenciais.add(day);
                }
            }

            atualizarDecoradores(datasPresenciais, datasOnline);
        });

        // Listener para detectar cliques nas datas
        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            AulaCurso aulaSelecionada = aulaMap.get(date);
            if (aulaSelecionada != null) {
                openAulaGrupoInscricaoModal(aulaSelecionada);
            }
        });
    }

    // Atualiza os decoradores no calendário
    private void atualizarDecoradores(Set<CalendarDay> datasPresenciais, Set<CalendarDay> datasOnline) {
        calendarView.removeDecorators();
        calendarView.addDecorator(new CustomCalendarDecorator(context, datasPresenciais, R.color.md_theme_primary));
        calendarView.addDecorator(new CustomCalendarDecorator(context, datasOnline, R.color.green));
    }

    // Configuração dos botões "Inscrever-se" e "Cancelar"
    private void configurarBotoes(View view) {
        view.findViewById(R.id.btnInscreverSe).setOnClickListener(v -> dialog.dismiss());
        view.findViewById(R.id.btnCancelar).setOnClickListener(v -> dialog.dismiss());
    }

    // Abre o modal de inscrição para a aula selecionada
    private void openAulaGrupoInscricaoModal(AulaCurso aula) {
        View modalView = LayoutInflater.from(context).inflate(R.layout.modal_aula_inscricao_aluno, null);
        Dialog modalDialog = new Dialog(context);
        modalDialog.setContentView(modalView);
        modalDialog.setCancelable(true);

        preencherInformacoesAula(modalView, aula);
        configurarBotoesModal(modalView, modalDialog, aula);

        modalDialog.show();
    }

    // Preenche o modal com as informações da aula
    private void preencherInformacoesAula(View modalView, AulaCurso aula) {
        TextView tvNomeAula = modalView.findViewById(R.id.tvAulaGrupoNome);
        TextView tvDescricaoAula = modalView.findViewById(R.id.tvAulaGrupoDescricao);
        TextView tvTipoAula = modalView.findViewById(R.id.tvTipoAula);
        TextView tvDataAula = modalView.findViewById(R.id.tvAulaGrupoData);

        tvNomeAula.setText(aula.getTitulo());
        tvDescricaoAula.setText(aula.getDescricao());
        tvTipoAula.setText(aula.isAulaOnline() ? "Online" : "Presencial");
        tvDataAula.setText(StringUtils.formatarData(aula.getDataAula()));
    }

    // Configura os botões do modal de inscrição
    private void configurarBotoesModal(View modalView, Dialog modalDialog, AulaCurso aula) {
        modalView.findViewById(R.id.btnAulaGrupoIncreverSe).setOnClickListener(v -> inscreverAlunoNaAula(aula, modalDialog));
        modalView.findViewById(R.id.btnAulaGrupoCancelar).setOnClickListener(v -> modalDialog.dismiss());
    }

    // Inscreve o aluno na aula e exibe mensagem de sucesso
    private void inscreverAlunoNaAula(AulaCurso aula, Dialog modalDialog) {
        AulaCursoInscrito aulaCursoInscrito = new AulaCursoInscrito(aula.getIdAulaCurso(), idAluno);

        aulaCursoInscritoViewModel.insert(aulaCursoInscrito, () -> {
            if (context instanceof Activity) {
                ((Activity) context).runOnUiThread(() -> {
                    Toast.makeText(context, "Você foi inscrito na aula " + aula.getTitulo(), Toast.LENGTH_SHORT).show();
                });
            }
        });

        modalDialog.dismiss();
    }
}