package zheng.craig.hudlu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import zheng.craig.hudlu.models.Favorite;
import zheng.craig.hudlu.models.MashableNewsItem;

public class FavoritesActivity extends AppCompatActivity implements FaveAdapter.OnAdapterInteractionListener {
    private RecyclerView fRecyclerView;
    private RecyclerView.Adapter fAdapter;
    private RecyclerView.LayoutManager fLayoutManager;
    private final List<Favorite> fDataset = new ArrayList<>();
    private MashableNewsItem newsItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fRecyclerView = (RecyclerView)findViewById(R.id.favorites_view);

        fLayoutManager = new LinearLayoutManager(this);
        fRecyclerView.setLayoutManager(fLayoutManager);

        fAdapter = new FaveAdapter(this, fDataset);
        fRecyclerView.setAdapter(fAdapter);

        fDataset.addAll(FavoriteUtil.getAllFavorites(this.getApplicationContext()));
        fAdapter.notifyDataSetChanged();
    }

    public void onItemClicked(View view, int position) {
        Favorite item = fDataset.get(position);

        if (view.getId() == R.id.favorite_button) {
            newsItem = new MashableNewsItem();
            newsItem.link = item.getLink();

            FavoriteUtil.removeFavorite(this, newsItem);
            fAdapter.notifyDataSetChanged();
        } else {
            Uri webpage = Uri.parse(item.getLink());
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }

}
