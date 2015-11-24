package zheng.craig.hudlu;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import zheng.craig.hudlu.models.MashableNewsItem;

/**
 * Created by cz on 11/15/15.
 */
public class CZAdapter extends RecyclerView.Adapter<CZAdapter.CZViewHolder> {
    private List<MashableNewsItem> czDataset;
    private OnAdapterInteractionListener myListener;
    
    public static class CZViewHolder extends RecyclerView.ViewHolder {
        public TextView czTextView;

        public CZViewHolder(CardView view) {
            super(view);
            czTextView = (TextView) view.findViewById(R.id.cz_text_item);
        }
    }

    public interface OnAdapterInteractionListener {
        void onItemClicked(View view, int position);
    }
    
    public CZAdapter(Context myContext, List<MashableNewsItem> myDataset) {
        czDataset = myDataset;
        myListener = (OnAdapterInteractionListener) myContext;
    }

    @Override
    public CZAdapter.CZViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                                    .inflate(R.layout.cz_text_view, parent, false);

        return new CZViewHolder((CardView)v);
    }
    
    @Override
    public void onBindViewHolder(CZViewHolder holder, final int position) {
        holder.czTextView.setText(czDataset.get(position).title);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListener.onItemClicked(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return czDataset.size();
    }
}
