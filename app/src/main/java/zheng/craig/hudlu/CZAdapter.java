package zheng.craig.hudlu;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

import zheng.craig.hudlu.models.MashableNewsItem;

/**
 * Created by cz on 11/15/15.
 */
public class CZAdapter extends RecyclerView.Adapter<CZAdapter.CZViewHolder> {
    private List<MashableNewsItem> czDataset;
    private OnAdapterInteractionListener myListener;
    private RequestQueue mRequestQueue;
    
    public static class CZViewHolder extends RecyclerView.ViewHolder {
        public ImageView CoverView;
        public TextView TitleView;
        public TextView AuthorView;

        public CZViewHolder(CardView view) {
            super(view);
            CoverView = (ImageView) view.findViewById(R.id.item_image);
            TitleView = (TextView) view.findViewById(R.id.item_title);
            AuthorView = (TextView) view.findViewById(R.id.item_author);
        }
    }

    public interface OnAdapterInteractionListener {
        void onItemClicked(View view, int position);
    }
    
    public CZAdapter(Context myContext, List<MashableNewsItem> myDataset) {
        mRequestQueue = Volley.newRequestQueue(myContext);
        czDataset = myDataset;
        myListener = (OnAdapterInteractionListener) myContext;
    }

    @Override
    public CZAdapter.CZViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                                    .inflate(R.layout.cz_news_item_view, parent, false);

        return new CZViewHolder((CardView)v);
    }
    
    @Override
    public void onBindViewHolder(final CZViewHolder holder, final int position) {
        MashableNewsItem currentItem = czDataset.get(position);

        holder.TitleView.setText(currentItem.title);
        holder.AuthorView.setText(currentItem.author);

        ImageRequest request = new ImageRequest(
                currentItem.image,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        holder.CoverView.setImageBitmap(bitmap);
                    }
                }, 0, 0, null, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        // no
                    }
                });

        mRequestQueue.add(request);

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
