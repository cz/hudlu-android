package zheng.craig.hudlu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import zheng.craig.hudlu.models.MashableNews;
import zheng.craig.hudlu.models.MashableNewsItem;

public class MainActivity extends AppCompatActivity implements CZAdapter.OnAdapterInteractionListener {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private final List<MashableNewsItem> myDataset = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Context context = getApplicationContext();

        mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CZAdapter(this, myDataset);
        mRecyclerView.setAdapter(mAdapter);

        // Show an alert for first-time users
        SharedPreferences preferences = getSharedPreferences("HudlPrefs", Context.MODE_PRIVATE);
        Boolean alertShown = preferences.getBoolean("alertShown", false);

        if (alertShown == false) {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle(context.getResources().getString(R.string.hello_alert_title));
            alertDialog.setMessage(context.getResources().getString(R.string.hello_alert_body));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, context.getResources().getString(R.string.ok_text),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("alertShown", true);
            editor.apply();
        }

        fetchLatestNews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent launchNewIntent = new Intent(MainActivity.this,FavoritesActivity.class);
        startActivityForResult(launchNewIntent, 0);
        return super.onOptionsItemSelected(item);
    }

    public void onItemClicked(View view, int position) {
        MashableNewsItem item = myDataset.get(position);

        if (view.getId() == R.id.favorite_button) {

            if (!FavoriteUtil.isFavorite(this, item)) {
                FavoriteUtil.addFavorite(this, item);
            } else {
                FavoriteUtil.removeFavorite(this, item);
            }

            mAdapter.notifyDataSetChanged();
        } else {
            Uri webpage = Uri.parse(item.link);
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }

    public void fetchLatestNews() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            StringRequest request = new StringRequest(Request.Method.GET,
                    "http://mashable.com/stories.json?hot_per_page=0&new_per_page=5&rising_per_page=0",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            MashableNews news = new Gson().fromJson(response, MashableNews.class);
                            myDataset.addAll(news.newsItems);
                            mAdapter.notifyDataSetChanged();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Sorry, couldn't fetch stories", Toast.LENGTH_SHORT).show();
                        }
                    }
            );

            Toast.makeText(getApplicationContext(), "Fetching latest stories", Toast.LENGTH_SHORT).show();
            requestQueue.add(request);
        } else {
            Toast.makeText(getApplicationContext(), "You are offline", Toast.LENGTH_SHORT).show();
        }
    }
}
