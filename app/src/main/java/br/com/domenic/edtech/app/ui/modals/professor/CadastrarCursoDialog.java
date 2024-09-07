package br.com.domenic.edtech.app.ui.modals.professor;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.Date;

import br.com.domenic.edtech.app.R;
import br.com.domenic.edtech.app.models.Curso;
import br.com.domenic.edtech.app.utils.geradores.GeradorDados;

public class CadastrarCursoDialog {
    private final Dialog dialog;
    private final EditText etNomeCurso;
    private final EditText etDescricaoCurso;
    private final EditText etDuracaoCurso;
    private final EditText etPrecoCurso;
    private final EditText etPrecoCursoAulaParticular;

    public CadastrarCursoDialog(Context context, OnCursoDialogListener listener) {
        View view = LayoutInflater.from(context).inflate(R.layout.modal_cadastrar_curso, null);
        dialog = new Dialog(context);
        dialog.setContentView(view);
        dialog.setCancelable(true);

        etNomeCurso = view.findViewById(R.id.etNomeCurso);
        etDescricaoCurso = view.findViewById(R.id.etDescricaoCurso);
        etDuracaoCurso = view.findViewById(R.id.etDuracaoCurso);
        etPrecoCurso = view.findViewById(R.id.etPrecoCurso);
        etPrecoCursoAulaParticular = view.findViewById(R.id.etPrecoCursoAulaParticular);

        Curso curso = GeradorDados.gerarCurso();
        etNomeCurso.setText(curso.getNomeCurso());
        etDescricaoCurso.setText(curso.getDescricao());
        etDuracaoCurso.setText(String.valueOf(curso.getDuracaoHoras()));
        etPrecoCurso.setText(String.format("%.2f", curso.getPrecoCurso()));
        etPrecoCursoAulaParticular.setText(String.format("%.2f", curso.getPrecoCursoAulaParticular()));
        Date dataCriacao = curso.getDataCriacao();
        int popularidade = curso.getPopularidade();
        int recomendacao = curso.getRecomendacao();

        view.findViewById(R.id.btnCadastrar).setOnClickListener(v -> {
            String nomeCurso = etNomeCurso.getText().toString();
            String descricaoCurso = etDescricaoCurso.getText().toString();
            String duracaoCursoStr = etDuracaoCurso.getText().toString();
            String precoCursoStr = etPrecoCurso.getText().toString();
            String precoCursoAulaParticularStr = etPrecoCursoAulaParticular.getText().toString();

            // Convertendo os valores de duração e preço para o tipo correto
            int duracaoCurso = !duracaoCursoStr.isEmpty() ? Integer.parseInt(duracaoCursoStr) : 0;
            double precoCurso = !precoCursoStr.isEmpty() ? Double.parseDouble(precoCursoStr) : 0.0;
            double precoCursoAulaParticular = !precoCursoAulaParticularStr.isEmpty() ? Double.parseDouble(precoCursoAulaParticularStr) : 0.0;

            if (listener != null) {
                listener.onCadastrarCurso(nomeCurso, descricaoCurso, duracaoCurso, precoCurso, precoCursoAulaParticular, dataCriacao, popularidade, recomendacao);
            }
            dialog.dismiss();
        });

        view.findViewById(R.id.btnCancelar).setOnClickListener(v -> dialog.dismiss());
    }

    public void show() {
        dialog.show();
    }

    public interface OnCursoDialogListener {
        void onCadastrarCurso(String nomeCurso, String descricaoCurso, int duracaoCurso, double precoCurso, double precoCursoAulaParticular, Date dataCriacao, int popularidade, int recomendacao);
    }
}