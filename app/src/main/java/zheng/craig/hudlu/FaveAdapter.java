package zheng.craig.hudlu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

import zheng.craig.hudlu.models.Favorite;
import zheng.craig.hudlu.models.MashableNewsItem;

/**
 * Created by cz on 11/15/15.
 */
public class FaveAdapter extends RecyclerView.Adapter<FaveAdapter.FaveViewHolder> {
    private List<Favorite> fDataset;
    private OnAdapterInteractionListener fListener;
    private RequestQueue fRequestQueue;
    private Context fContext;

    public static class FaveViewHolder extends RecyclerView.ViewHolder {
        public ImageView coverView;
        public TextView titleView;
        public TextView authorView;
        public Button faveButton;

        public FaveViewHolder(CardView view) {
            super(view);
            coverView = (ImageView) view.findViewById(R.id.item_image);
            titleView = (TextView) view.findViewById(R.id.item_title);
            authorView = (TextView) view.findViewById(R.id.item_author);
            faveButton = (Button) view.findViewById(R.id.favorite_button);
        }
    }

    public interface OnAdapterInteractionListener {
        void onItemClicked(View view, int position);
    }

    public FaveAdapter(Context myContext, List<Favorite> myDataset) {
        fRequestQueue = Volley.newRequestQueue(myContext);
        fDataset = myDataset;
        fListener = (OnAdapterInteractionListener) myContext;
        fContext = myContext;
    }

    @Override
    public FaveAdapter.FaveViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                                    .inflate(R.layout.cz_news_item_view, parent, false);

        return new FaveViewHolder((CardView)v);
    }
    
    @Override
    public void onBindViewHolder(final FaveViewHolder holder, final int position) {
        Favorite currentItem = fDataset.get(position);

        holder.titleView.setText(currentItem.getTitle());
        holder.authorView.setText(currentItem.getAuthor());

        ImageRequest request = new ImageRequest(
                currentItem.getImage(),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        holder.coverView.setImageBitmap(bitmap);
                    }
                }, 0, 0, null, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        // no
                    }
                });

        fRequestQueue.add(request);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fListener.onItemClicked(v, position);
            }
        });

        holder.faveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fListener.onItemClicked(v, position);
            }
        });

        holder.faveButton.setBackgroundColor(0xFFFF6600);
        holder.faveButton.setTextColor(Color.WHITE);
    }

    @Override
    public int getItemCount() {
        return fDataset.size();
    }
}
