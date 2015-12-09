package zheng.craig.hudlu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import zheng.craig.hudlu.models.Favorite;

public class FavoritesActivity extends AppCompatActivity implements FaveAdapter.OnAdapterInteractionListener {
    private RecyclerView fRecyclerView;
    private RecyclerView.Adapter fAdapter;
    private RecyclerView.LayoutManager fLayoutManager;
    private final List<Favorite> fDataset = new ArrayList<>();

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
    }

    public void onItemClicked(View view, int position) {
    }

}
