package br.com.domenic.edtech.app.ui.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import br.com.domenic.edtech.app.R;
import br.com.domenic.edtech.app.models.AulaCurso;
import br.com.domenic.edtech.app.ui.adapters.base.BaseAdapter;

public class AulasAdapter extends BaseAdapter<AulaCurso, AulasAdapter.AulaCursoViewHolder> {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public AulasAdapter(Context context, List<AulaCurso> aulas, OnItemClickListener<AulaCurso> itemClickListener) {
        super(context, aulas, itemClickListener);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.card_aula; // O layout que será inflado para cada item
    }

    @Override
    protected AulaCursoViewHolder createViewHolder(View view) {
        return new AulaCursoViewHolder(view);
    }

    @Override
    protected void bindViewHolder(AulaCursoViewHolder holder, AulaCurso aulaCurso) {
        holder.tvNomeAula.setText(aulaCurso.getTitulo());
        holder.tvDescricaoAula.setText(aulaCurso.getDescricao());
        holder.tvTipoAulaCurso.setText(aulaCurso.isAulaOnline() ? "Online" : "Presencial");
        holder.tvDataAulaCurso.setText(dateFormat.format(aulaCurso.getDataAula()));
    }

    @Override
    protected boolean areItemsEqual(AulaCurso oldItem, AulaCurso newItem) {
        return oldItem.getIdAulaCurso() == newItem.getIdAulaCurso();
    }

    @Override
    protected boolean areContentsEqual(AulaCurso oldItem, AulaCurso newItem) {
        return oldItem.equals(newItem);
    }

    public static class AulaCursoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomeAula, tvDescricaoAula, tvTipoAulaCurso, tvDataAulaCurso;

        public AulaCursoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomeAula = itemView.findViewById(R.id.tvAulaCursoNome);
            tvDescricaoAula = itemView.findViewById(R.id.tvAulaCursoDescricao);
            tvTipoAulaCurso = itemView.findViewById(R.id.tvTipoAulaCurso);
            tvDataAulaCurso = itemView.findViewById(R.id.tvAulaCursoData);
        }
    }
}