package br.com.domenic.edtech.app.ui.adapters;

import android.content.Context;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.domenic.edtech.app.R;
import br.com.domenic.edtech.app.models.ProfessorAvaliacao;
import br.com.domenic.edtech.app.ui.adapters.base.BaseAdapter;

public class AvaliacaoAdapter extends BaseAdapter<ProfessorAvaliacao, AvaliacaoAdapter.AvaliacaoViewHolder> {

    public AvaliacaoAdapter(Context context, List<ProfessorAvaliacao> avaliacaoList, OnItemClickListener<ProfessorAvaliacao> itemClickListener) {
        super(context, avaliacaoList, itemClickListener);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.card_avaliacao_professor;
    }

    @Override
    protected AvaliacaoViewHolder createViewHolder(View view) {
        return new AvaliacaoViewHolder(view);
    }

    @Override
    protected void bindViewHolder(AvaliacaoViewHolder holder, ProfessorAvaliacao avaliacao) {
        holder.ratingBar.setRating(avaliacao.getNota());
        holder.comentarioTextView.setText(avaliacao.getComentario());
    }

    @Override
    protected boolean areItemsEqual(ProfessorAvaliacao oldItem, ProfessorAvaliacao newItem) {
        // Comparar as chaves compostas idAluno e idProfessor
        return oldItem.getIdAluno() == newItem.getIdAluno() &&
                oldItem.getIdProfessor() == newItem.getIdProfessor();
    }

    @Override
    protected boolean areContentsEqual(ProfessorAvaliacao oldItem, ProfessorAvaliacao newItem) {
        return oldItem.equals(newItem); // Isso vai comparar todos os campos.
    }

    public static class AvaliacaoViewHolder extends RecyclerView.ViewHolder {
        RatingBar ratingBar;
        TextView comentarioTextView;

        public AvaliacaoViewHolder(@NonNull View itemView) {
            super(itemView);
            ratingBar = itemView.findViewById(R.id.ratingBarAvaliacao);
            comentarioTextView = itemView.findViewById(R.id.textComentario);
        }
    }
}