package br.com.domenic.edtech.app.ui.modals.professor;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import br.com.domenic.edtech.app.R;
import br.com.domenic.edtech.app.models.Curso;

public class EditarCursoDialog {
    private final Dialog dialog;
    private final EditText etNomeCurso;
    private final EditText etDescricaoCurso;
    private final EditText etDuracaoCurso;
    private final EditText etPrecoCurso;
    private final EditText etPrecoCursoAulaParticular;
    private final Curso curso;

    public EditarCursoDialog(Context context, Curso curso, OnEditCursoDialogListener listener) {
        this.curso = curso;
        View view = LayoutInflater.from(context).inflate(R.layout.modal_editar_curso, null);
        dialog = new Dialog(context);
        dialog.setContentView(view);
        dialog.setCancelable(true);

        etNomeCurso = view.findViewById(R.id.etNomeCurso);
        etDescricaoCurso = view.findViewById(R.id.etDescricaoCurso);
        etDuracaoCurso = view.findViewById(R.id.etDuracaoHoras);
        etPrecoCurso = view.findViewById(R.id.etPrecoCurso);
        etPrecoCursoAulaParticular = view.findViewById(R.id.etPrecoCursoAulaParticular);

        // Preencher os campos com as informações atuais do curso
        etNomeCurso.setText(curso.getNomeCurso());
        etDescricaoCurso.setText(curso.getDescricao());
        etDuracaoCurso.setText(String.valueOf(curso.getDuracaoHoras()));
        etPrecoCurso.setText(String.valueOf(curso.getPrecoCurso()));
        etPrecoCursoAulaParticular.setText(String.valueOf(curso.getPrecoCursoAulaParticular()));

        view.findViewById(R.id.btnSalvarCurso).setOnClickListener(v -> {
            String nomeCurso = etNomeCurso.getText().toString();
            String descricaoCurso = etDescricaoCurso.getText().toString();
            int duracaoCurso = Integer.parseInt(etDuracaoCurso.getText().toString());
            double precoCurso = Double.parseDouble(etPrecoCurso.getText().toString());
            double precoAulaParticular = Double.parseDouble(etPrecoCursoAulaParticular.getText().toString());

            curso.setNomeCurso(nomeCurso);
            curso.setDescricao(descricaoCurso);
            curso.setDuracaoHoras(duracaoCurso);
            curso.setPrecoCurso(precoCurso);
            curso.setPrecoCursoAulaParticular(precoAulaParticular);

            if (listener != null) {
                listener.onEditCurso(curso);
            }
            dialog.dismiss();
        });

        view.findViewById(R.id.btnCancelarCurso).setOnClickListener(v -> dialog.dismiss());
    }

    public void show() {
        dialog.show();
    }

    public interface OnEditCursoDialogListener {
        void onEditCurso(Curso curso);
    }
}