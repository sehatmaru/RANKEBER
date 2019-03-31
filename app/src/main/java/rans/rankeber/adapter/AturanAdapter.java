package rans.rankeber.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import rans.rankeber.R;
import rans.rankeber.realm.AturanRealm;


public class AturanAdapter extends RecyclerView.Adapter<AturanAdapter.MyViewHolder> {

    private List<AturanRealm> listData;
    private OnClickAturanListener onClickAturan;

    public AturanAdapter(Context context, List<AturanRealm> listData, OnClickAturanListener onClick) {
        this.listData = listData;
        LayoutInflater.from(context);
        this.onClickAturan = onClick;
    }

    public interface OnClickAturanListener {
        void OnClickAturan(String idAturan);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_aturan, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final AturanRealm aturanRoda2 = listData.get(position);

        holder.judul.setText(aturanRoda2.getJudulAturan());

        holder.cardview.setOnClickListener(view -> onClickAturan.OnClickAturan(aturanRoda2.getHashId()));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView judul;
        ImageView gbr;
        CardView cardview;
        MyViewHolder(View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.judul);
            gbr = itemView.findViewById(R.id.gbr);
            cardview = itemView.findViewById(R.id.cardViewAturan);
        }
    }
    public void animateTo(List<AturanRealm> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }
    private void applyAndAnimateRemovals(List<AturanRealm> newModels) {
        for (int i = listData.size() - 1; i >= 0; i--) {
            final AturanRealm model = listData.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<AturanRealm> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final AturanRealm model = newModels.get(i);
            if (!listData.contains(model)) {
                addItem(i, model);
            }
        }
    }
    private void addItem(int position, AturanRealm model) {
        listData.add(position, model);
        notifyItemInserted(position);
    }
    private void applyAndAnimateMovedItems(List<AturanRealm> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final AturanRealm model = newModels.get(toPosition);
            final int fromPosition = listData.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    private void moveItem(int fromPosition, int toPosition) {
        final AturanRealm model = listData.remove(fromPosition);
        listData.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }
    private void removeItem(int position) {
        listData.remove(position);
        notifyItemRemoved(position);
    }
}
