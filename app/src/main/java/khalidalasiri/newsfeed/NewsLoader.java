package khalidalasiri.newsfeed;

import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kasir on 1/23/2018.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    String key;
    String uriKey;
    Context context;

    public NewsLoader(Context context) {
        super(context);
        this.context = context;
        key = context.getResources().getString(R.string.Key);
    }

    @Override
    public List<News> loadInBackground() {
        List<News> newsList = new ArrayList<>();

        try {
            uriBuild(key);


            URL url = new URL(uriKey);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.connect();

            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder builder = new StringBuilder();
            String line;


            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }

            // JSON

            JSONObject root = new JSONObject(builder.toString());
            JSONObject response = root.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");
            String author = "";

            for (int i = 0; i < results.length(); i++) {
                JSONObject article = results.getJSONObject(i);
                JSONArray references = article.getJSONArray("references");
                if (references.length() > 0) {
                    for (int j = 0; i < references.length(); j++) {
                        JSONObject refauthor = references.getJSONObject(i);
                        if (refauthor.has("author"))
                            author = refauthor.getString("id");
                        else
                            author = "";

                    }
                }
                News news = new News(article.getString("webTitle"),
                        article.getString("sectionName"),
                        author,
                        article.getString("webPublicationDate"),
                        article.getString("webUrl"));
                newsList.add(news);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsList;
    }

    public String uriBuild(String key) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String numPages = sharedPrefs.getString(context.getString(R.string.keyPage), context.getString(R.string.defPage));
        String resultNum = sharedPrefs.getString(context.getString(R.string.keyResult), context.getString(R.string.defResult));
        String section = sharedPrefs.getString(context.getString(R.string.keySection), context.getString(R.string.allSection));

        Uri baseUri = Uri.parse(key);

        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("page", numPages);
        uriBuilder.appendQueryParameter("page-size", resultNum);
        uriBuilder.appendQueryParameter("section", section);

        uriKey = uriBuilder.toString();

        return uriKey;
    }
}
