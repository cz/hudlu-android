package zheng.craig.hudlu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by cz on 11/15/15.
 */
public class CZAdapter extends RecyclerView.Adapter<CZAdapter.CZViewHolder> {
    private String[] czDataset;
    
    public static class CZViewHolder extends RecyclerView.ViewHolder {
        public TextView czTextView;

        public CZViewHolder(TextView view) {
            super(view);
            czTextView = view;
        }
    }
    
    public CZAdapter(Context myContext, String[] myDataset) {
        czDataset = myDataset;
    }

    @Override
    public CZAdapter.CZViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                                    .inflate(R.layout.cz_text_view, parent, false);

        return new CZViewHolder((TextView)v);
    }
    
    @Override
    public void onBindViewHolder(CZViewHolder holder, int position) {
        holder.czTextView.setText(czDataset[position]);
    }

    @Override
    public int getItemCount() {
        return czDataset.length;
    }
}
