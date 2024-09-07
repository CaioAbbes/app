package br.com.domenic.edtech.app.ui.modals.aluno;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.domenic.edtech.app.R;
import br.com.domenic.edtech.app.models.AulaCurso;
import br.com.domenic.edtech.app.models.AulaCursoInscrito;
import br.com.domenic.edtech.app.ui.calendars.DisabledDaysDecorator;
import br.com.domenic.edtech.app.ui.viewmodels.AulaCursoInscritoViewModel;
import br.com.domenic.edtech.app.ui.viewmodels.AulaCursoViewModel;
import br.com.domenic.edtech.app.utils.geradores.GeradorDados;

public class AgendarAulaParticularDialog {
    private final Dialog dialog;
    private final Context context;
    private final AulaCursoViewModel aulaCursoViewModel;
    private final AulaCursoInscritoViewModel aulaCursoInscritoViewModel;
    private final int idCurso, idAluno;
    private Date selectedDate;
    private final Set<CalendarDay> diasIndisponiveis = new HashSet<>();

    private MaterialCalendarView calendarView;
    private TextInputEditText etTitulo, etDescricao, etLink;
    private MaterialButton btnCadastrar, btnCancelar;
    private RadioGroup radioGroupTipoAula;
    private RadioButton radioAulaOnline, radioIsAulaPresencial;

    public AgendarAulaParticularDialog(Context context, AulaCursoViewModel aulaCursoViewModel, AulaCursoInscritoViewModel aulaCursoInscritoViewModel, int idCurso, int idAluno) {
        this.context = context;
        this.aulaCursoViewModel = aulaCursoViewModel;
        this.aulaCursoInscritoViewModel = aulaCursoInscritoViewModel;
        this.idCurso = idCurso;
        this.idAluno = idAluno;

        dialog = createDialog();
        configurarCalendario();
        configurarBotoes();
        configurarRadioGroup();
    }

    private Dialog createDialog() {
        View view = LayoutInflater.from(context).inflate(R.layout.modal_cadastrar_aula, null);
        Dialog dialog = new Dialog(context);
        dialog.setContentView(view);
        dialog.setCancelable(true);

        calendarView = view.findViewById(R.id.calendarViewAulaEmGrupo);
        etTitulo = view.findViewById(R.id.etTituloAulaGrupo);
        etDescricao = view.findViewById(R.id.etDescricaoAulaGrupo);
        etLink = view.findViewById(R.id.etLinkAulaGrupo);
        btnCadastrar = view.findViewById(R.id.btnCadastrar);
        btnCancelar = view.findViewById(R.id.btnCancelar);
        radioGroupTipoAula = view.findViewById(R.id.radioGroupTipoAula);
        radioAulaOnline = view.findViewById(R.id.radioIsAulaOnline);
        radioIsAulaPresencial = view.findViewById(R.id.radioIsAulaPresencial);

        etTitulo.setText("Agendando aula particular");
        AulaCurso aulaCursoDadosGerados = GeradorDados.gerarAulaGrupo2();
        etTitulo.setText(aulaCursoDadosGerados.getTitulo());
        etDescricao.setText(aulaCursoDadosGerados.getDescricao());
        etLink.setText(aulaCursoDadosGerados.getLinkAulaAoVivo());

        disableFields();
        return dialog;
    }

    public void show() {
        dialog.show();
    }

    private void configurarCalendario() {
        aulaCursoViewModel.findAulasByCursoId(idCurso).observe((LifecycleOwner) context, aulas -> {
            if (aulas != null && !aulas.isEmpty()) {
                for (AulaCurso aula : aulas) {
                    diasIndisponiveis.add(CalendarDay.from(aula.getDataAula()));
                }
                calendarView.addDecorator(new DisabledDaysDecorator(diasIndisponiveis));
            }
        });

        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            if (isDayValid(date)) {
                selectedDate = date.getDate();
                enableFields();
            } else {
                disableFields();
                showToast("Dia indisponível");
            }
        });
    }

    private void configurarRadioGroup() {
        radioGroupTipoAula.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioIsAulaOnline) {
                etLink.setEnabled(true);
            } else if (checkedId == R.id.radioIsAulaPresencial) {
                etLink.setEnabled(false);
                etLink.setText("");
            }
        });
    }

    private void configurarBotoes() {
        btnCadastrar.setOnClickListener(v -> cadastrarAulaParticular());
        btnCancelar.setOnClickListener(v -> dialog.dismiss());
    }

    private void enableFields() {
        etTitulo.setEnabled(true);
        etDescricao.setEnabled(true);
        etLink.setEnabled(true);
        btnCadastrar.setEnabled(true);
    }

    private void disableFields() {
        etTitulo.setEnabled(false);
        etDescricao.setEnabled(false);
        etLink.setEnabled(false);
        btnCadastrar.setEnabled(false);
    }

    private boolean isDayValid(CalendarDay date) {
        return !diasIndisponiveis.contains(date);
    }

    private void cadastrarAulaParticular() {
        String titulo = etTitulo.getText().toString();
        String descricao = etDescricao.getText().toString();
        String linkAula = etLink.getText().toString();

        if (!validateInput(titulo, descricao)) {
            showToast("Preencha todos os campos obrigatórios");
            return;
        }

        boolean isAulaParticular = true;
        boolean isAulaOnline = radioAulaOnline.isChecked();

        LiveData<List<AulaCurso>> aulasLiveData = aulaCursoViewModel.findAulasByCursoId(idCurso);
        aulasLiveData.observe((LifecycleOwner) context, aulas -> {
            if (aulas != null && !aulas.isEmpty()) {
                int idProfessor = aulas.get(0).getIdProfessor();
                AulaCurso novaAula = new AulaCurso(
                        idCurso,
                        idProfessor,
                        isAulaParticular,
                        isAulaOnline,
                        titulo,
                        descricao,
                        selectedDate,
                        linkAula
                );

                // Inserir a nova aula
                aulaCursoViewModel.insert(novaAula, () -> {
                    ((Activity) context).runOnUiThread(() -> {
                        aulasLiveData.removeObservers((LifecycleOwner) context);
                        buscarUltimaAulaCriada();
                    });
                });
            } else {
                showToast("Nenhuma aula encontrada para este curso");
            }
        });
    }

    private void buscarUltimaAulaCriada() {
        ((Activity) context).runOnUiThread(() -> {
            LiveData<List<AulaCurso>> novaAulaLiveData = aulaCursoViewModel.findAulasByCursoId(idCurso);
            novaAulaLiveData.observe((LifecycleOwner) context, novasAulas -> {
                if (novasAulas != null && !novasAulas.isEmpty()) {
                    AulaCurso aulaCriada = novasAulas.get(novasAulas.size() - 1);
                    ((Activity) context).runOnUiThread(() -> {
                        AulaCursoInscrito aulaCursoInscrito = new AulaCursoInscrito(aulaCriada.getIdAulaCurso(), idAluno);
                        aulaCursoInscritoViewModel.insert(aulaCursoInscrito, () -> {
                            ((Activity) context).runOnUiThread(() -> {
                                novaAulaLiveData.removeObservers((LifecycleOwner) context);
                                onInscricaoConcluida();
                            });
                        });
                    });
                }
            });
        });
    }

    private void onInscricaoConcluida() {
        showToast("Aula particular agendada e inscrição realizada com sucesso!");
        dialog.dismiss();
    }

    private boolean validateInput(String titulo, String descricao) {
        return !titulo.isEmpty() && !descricao.isEmpty() && selectedDate != null;
    }

    private void showToast(String message) {
        if (context instanceof Activity) {
            ((Activity) context).runOnUiThread(() ->
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            );
        }
    }
}