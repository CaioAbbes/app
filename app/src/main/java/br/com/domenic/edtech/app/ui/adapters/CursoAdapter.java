package br.com.domenic.edtech.app.ui.adapters;

import android.content.Context;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.stream.Collectors;

import br.com.domenic.edtech.app.R;
import br.com.domenic.edtech.app.models.Curso;
import br.com.domenic.edtech.app.models.CursoAvaliacao;
import br.com.domenic.edtech.app.ui.adapters.base.BaseAdapter;
import br.com.domenic.edtech.app.utils.StringUtils;

public class CursoAdapter extends BaseAdapter<Curso, CursoAdapter.CursoViewHolder> {
    private List<CursoAvaliacao> avaliacaoList;

    public CursoAdapter(Context context, List<Curso> cursoList, OnItemClickListener<Curso> itemClickListener) {
        super(context, cursoList, itemClickListener);
    }

    public CursoAdapter(Context context, List<Curso> cursoList, List<CursoAvaliacao> avaliacaoList, OnItemClickListener<Curso> itemClickListener) {
        super(context, cursoList, itemClickListener);
        this.avaliacaoList = avaliacaoList;
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.card_curso_com_nota;
    }

    @Override
    protected CursoViewHolder createViewHolder(View view) {
        return new CursoViewHolder(view);
    }

    @Override
    protected void bindViewHolder(CursoViewHolder holder, Curso curso) {
        holder.tvNomeCurso.setText(curso.getNomeCurso());
        holder.tvDescricaoCurso.setText(curso.getDescricao());
        holder.tvCursoPreco.setText(new StringUtils().doubleParaBRL(curso.getPrecoCurso()));
        holder.tvCursoPrecoAulaParticular.setText(new StringUtils().doubleParaBRL(curso.getPrecoCursoAulaParticular()));
        holder.ratingBar.setRating(calculateCursoRating(curso.getIdCurso()));
    }

    private float calculateCursoRating(int cursoId) {
        float result = 0.0f;
        if (avaliacaoList != null) {
            // Filtra as avaliações para o curso atual e calcula a média das notas
            List<CursoAvaliacao> avaliacoesDoCurso = avaliacaoList.stream()
                    .filter(avaliacao -> avaliacao.getIdCurso() == cursoId)
                    .collect(Collectors.toList());

            if (avaliacoesDoCurso.isEmpty()) {
                return result;
            }

            // Calcula a média das notas
            int totalNotas = avaliacoesDoCurso.stream()
                    .mapToInt(CursoAvaliacao::getNota)
                    .sum();
            result = totalNotas / (float) avaliacoesDoCurso.size();
        }
        return result;
    }

    @Override
    protected boolean areItemsEqual(Curso oldItem, Curso newItem) {
        return oldItem.getIdCurso() == newItem.getIdCurso();
    }

    @Override
    protected boolean areContentsEqual(Curso oldItem, Curso newItem) {
        return oldItem.equals(newItem);
    }

    public static class CursoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomeCurso, tvDescricaoCurso, tvCursoPreco, tvCursoPrecoAulaParticular;
        RatingBar ratingBar;

        public CursoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomeCurso = itemView.findViewById(R.id.tvCursoNome);
            tvDescricaoCurso = itemView.findViewById(R.id.tvCursoDescricao);
            tvCursoPreco = itemView.findViewById(R.id.tvCursoPreco);
            tvCursoPrecoAulaParticular = itemView.findViewById(R.id.tvCursoPrecoAulaParticular);
            ratingBar = itemView.findViewById(R.id.ratingCurso);
        }
    }
}