package khalidalasiri.newsfeed;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.Loader;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    RecyclerView recyclerView;
    RVAdapter rvAdapter;
    TextView notConnecte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notConnecte = findViewById(R.id.notConnecte);
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo ni = null;
        if (cm != null) {
            ni = cm.getActiveNetworkInfo();

            if (ni != null && ni.isConnected()) {
                //Log.d("print", "Connected");
                recyclerView = findViewById(R.id.recyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                rvAdapter = new RVAdapter(this, new ArrayList<News>());
                recyclerView.setAdapter(rvAdapter);
                getSupportLoaderManager().initLoader(0, null, this).forceLoad();
            } else {
                //Log.d("print", "Not Connected");
                notConnecte.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        //Log.d("print", "onCreateLoader");
        return new NewsLoader(MainActivity.this);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newsList) {
        //Log.d("print", "onLoadFinished");
        rvAdapter = new RVAdapter(this, newsList);
        recyclerView.setAdapter(rvAdapter);

    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {

    }
}
