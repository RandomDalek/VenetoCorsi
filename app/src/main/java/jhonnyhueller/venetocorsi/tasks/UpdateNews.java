package jhonnyhueller.venetocorsi.tasks;

import android.os.AsyncTask;
import android.util.Log;


import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URL;

import jhonnyhueller.venetocorsi.database.DatabaseAdapter;
import jhonnyhueller.venetocorsi.interfaces.HomeInterface;
import jhonnyhueller.venetocorsi.managers.StaticManager;
import jhonnyhueller.venetocorsi.rss.RssFeed;
import jhonnyhueller.venetocorsi.rss.RssReader;

/**
 * Created by jhonny
 */
public class UpdateNews extends AsyncTask<Void,Void,Void> {
    private final String TAG = getClass().getSimpleName();
    private DatabaseAdapter database;
    public UpdateNews(DatabaseAdapter database){
        this.database=database;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            RssFeed rssFeed= RssReader.read(new URL(StaticManager.URL_NEWS));
            database.insertRssFeed(rssFeed);
            Log.i(TAG, "News updated successfully");
        } catch (Exception e) {
            Log.e(TAG,"Not Updated");
            e.printStackTrace();
        }
        return null;
    }
}
