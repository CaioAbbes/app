package br.com.domenic.edtech.app.ui.modals.professor;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.lifecycle.LifecycleOwner;

import com.google.android.material.button.MaterialButton;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import br.com.domenic.edtech.app.R;
import br.com.domenic.edtech.app.models.AulaCurso;
import br.com.domenic.edtech.app.ui.calendars.DisabledDaysDecorator;
import br.com.domenic.edtech.app.ui.viewmodels.AulaCursoViewModel;
import br.com.domenic.edtech.app.utils.geradores.GeradorDados;

public class CadastrarAulaEmGrupoDialog {

    private final Dialog dialog;
    private final MaterialCalendarView calendarView;
    private final EditText etTitulo, etDescricao, etLinkAula;
    private final MaterialButton btnCadastrar, btnCancelar;
    private final RadioGroup radioGroupTipoAula;
    private final RadioButton radioAulaOnline, radioAulaPresencial;
    private CalendarDay dataSelecionada;
    private final AulaCursoViewModel aulaCursoViewModel;
    private final int idCurso;
    private final int idProfessor;
    private final Set<CalendarDay> diasIndisponiveis = new HashSet<>();

    // Passar LifecycleOwner como parâmetro
    public CadastrarAulaEmGrupoDialog(Context context, AulaCursoViewModel aulaCursoViewModel, int idCurso, int professorId, LifecycleOwner lifecycleOwner) {
        this.aulaCursoViewModel = aulaCursoViewModel;
        this.idCurso = idCurso;
        this.idProfessor = professorId;

        View view = LayoutInflater.from(context).inflate(R.layout.modal_cadastrar_aula, null);
        dialog = new Dialog(context);
        dialog.setContentView(view);
        dialog.setCancelable(true);

        calendarView = view.findViewById(R.id.calendarViewAulaEmGrupo);
        etTitulo = view.findViewById(R.id.etTituloAulaGrupo);
        etDescricao = view.findViewById(R.id.etDescricaoAulaGrupo);
        etLinkAula = view.findViewById(R.id.etLinkAulaGrupo);
        btnCadastrar = view.findViewById(R.id.btnCadastrar);
        btnCancelar = view.findViewById(R.id.btnCancelar);
        radioGroupTipoAula = view.findViewById(R.id.radioGroupTipoAula);
        radioAulaOnline = view.findViewById(R.id.radioIsAulaOnline);
        radioAulaPresencial = view.findViewById(R.id.radioIsAulaPresencial);

        etTitulo.setText("Agendando aula em grupo");

        AulaCurso aulaCursoDadosGerados = GeradorDados.gerarAulaGrupo2();
        etTitulo.setText(aulaCursoDadosGerados.getTitulo());
        etDescricao.setText(aulaCursoDadosGerados.getDescricao());
        etLinkAula.setText(aulaCursoDadosGerados.getLinkAulaAoVivo());
        desabilitarCampos();

        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            dataSelecionada = date;
            habilitarCampos();
        });

        configurarRadioGroup();

        btnCadastrar.setOnClickListener(v -> {
            if (dataSelecionada != null && validarCampos()) {
                cadastrarAula(context);
            } else {
                exibirToast(context, "Por favor, preencha todos os campos.");
            }
        });

        btnCancelar.setOnClickListener(v -> dialog.dismiss());

        // Passar o LifecycleOwner para observar LiveData corretamente
        configurarDiasIndisponiveis(lifecycleOwner);
    }

    public void show() {
        dialog.show();
    }

    private void cadastrarAula(Context context) {
        boolean isAulaParticular = false;
        boolean isAulaOnline = radioAulaOnline.isChecked();
        String titulo = etTitulo.getText().toString();
        String descricao = etDescricao.getText().toString();
        Date selectedDate = dataSelecionada.getDate();
        String linkAula = etLinkAula.getText().toString();

        AulaCurso novaAula = new AulaCurso(
                idCurso,
                idProfessor,
                isAulaParticular,
                isAulaOnline,
                titulo,
                descricao,
                selectedDate,
                isAulaOnline ? linkAula : null
        );

        aulaCursoViewModel.insert(novaAula, () -> {
            ((Activity) context).runOnUiThread(() -> {
                exibirToast(context, "Aula em grupo cadastrada com sucesso!");
                dialog.dismiss();
            });
        });
    }

    private boolean validarCampos() {
        boolean camposPreenchidos = !etTitulo.getText().toString().isEmpty() && !etDescricao.getText().toString().isEmpty();
        boolean linkPreenchido = !etLinkAula.getText().toString().isEmpty();

        if (radioAulaOnline.isChecked()) {
            return camposPreenchidos && linkPreenchido;
        }
        return camposPreenchidos;
    }

    private void habilitarCampos() {
        etTitulo.setEnabled(true);
        etDescricao.setEnabled(true);
        if (radioAulaOnline.isChecked()) {
            etLinkAula.setEnabled(true);
        }
        btnCadastrar.setEnabled(true);
    }

    private void desabilitarCampos() {
        etTitulo.setEnabled(false);
        etDescricao.setEnabled(false);
        etLinkAula.setEnabled(false);
        btnCadastrar.setEnabled(false);
    }

    private void configurarRadioGroup() {
        radioGroupTipoAula.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioIsAulaOnline) {
                etLinkAula.setEnabled(true);
            } else if (checkedId == R.id.radioIsAulaPresencial) {
                etLinkAula.setEnabled(false);
                etLinkAula.setText("");
            }
        });
    }

    private void configurarDiasIndisponiveis(LifecycleOwner lifecycleOwner) {
        aulaCursoViewModel.findAulasByCursoId(idCurso).observe(lifecycleOwner, aulas -> {
            if (aulas != null && !aulas.isEmpty()) {
                for (AulaCurso aula : aulas) {
                    diasIndisponiveis.add(CalendarDay.from(aula.getDataAula()));
                }
                calendarView.addDecorator(new DisabledDaysDecorator(diasIndisponiveis));
            }
        });
    }

    private void exibirToast(Context context, String mensagem) {
        ((Activity) context).runOnUiThread(() ->
                Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show()
        );
    }
}