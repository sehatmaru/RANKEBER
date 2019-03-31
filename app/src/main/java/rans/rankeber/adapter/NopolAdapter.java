package rans.rankeber.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import rans.rankeber.R;
import rans.rankeber.realm.NopolRealm;

/**
 * Created by Sehat MT Samosir on 03/30/2019.
 */

public class NopolAdapter extends RecyclerView.Adapter<NopolAdapter.MyViewHolder> {

    private List<NopolRealm> listData;
    private LayoutInflater layoutInflater;
    public static Context mContext;

    public NopolAdapter(Context context, List<NopolRealm> listData) {
        mContext = context;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_nopol, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final NopolRealm nopolRealm = listData.get(position);

        holder.nopol.setText(nopolRealm.getNopol());

        if (nopolRealm.getKategori() == 1)
            holder.gbr.setImageResource(R.drawable.ic_motorcycle_black_24dp);
        else if(nopolRealm.getKategori() == 2)
            holder.gbr.setImageResource(R.drawable.ic_car_black_24dp);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nopol;
        ImageView gbr;
        CardView cardview;
        public MyViewHolder(View itemView) {
            super(itemView);
            nopol = (TextView) itemView.findViewById(R.id.nopol);
            gbr = (ImageView) itemView.findViewById(R.id.gbr);
            cardview = (CardView) itemView.findViewById(R.id.cardViewAturan);
        }
    }
    public void animateTo(List<NopolRealm> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }
    private void applyAndAnimateRemovals(List<NopolRealm> newModels) {
        for (int i = listData.size() - 1; i >= 0; i--) {
            final NopolRealm model = listData.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<NopolRealm> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final NopolRealm model = newModels.get(i);
            if (!listData.contains(model)) {
                addItem(i, model);
            }
        }
    }
    public void addItem(int position, NopolRealm model) {
        listData.add(position, model);
        notifyItemInserted(position);
    }
    private void applyAndAnimateMovedItems(List<NopolRealm> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final NopolRealm model = newModels.get(toPosition);
            final int fromPosition = listData.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public void moveItem(int fromPosition, int toPosition) {
        final NopolRealm model = listData.remove(fromPosition);
        listData.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }
    public void removeItem(int position) {
        listData.remove(position);
        notifyItemRemoved(position);
    }
}
