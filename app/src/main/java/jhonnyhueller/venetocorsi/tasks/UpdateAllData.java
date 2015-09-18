package jhonnyhueller.venetocorsi.tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import jhonnyhueller.venetocorsi.adapters.DateAdapter;
import jhonnyhueller.venetocorsi.adapters.XmlDateFormatAdapter;
import jhonnyhueller.venetocorsi.database.DatabaseAdapter;
import jhonnyhueller.venetocorsi.interfaces.HomeInterface;
import jhonnyhueller.venetocorsi.managers.StaticManager;
import jhonnyhueller.venetocorsi.models.CoursePage;
import jhonnyhueller.venetocorsi.rss.RssFeed;
import jhonnyhueller.venetocorsi.rss.RssReader;

/**
 * Created by jhonny
 */
public class UpdateAllData extends AsyncTask<Void,Void,Void> {
    private final String TAG = getClass().getSimpleName();
    private DatabaseAdapter database;
    private HomeInterface homeInterface;

    public UpdateAllData(DatabaseAdapter database, HomeInterface homeInterface){
        this.database=database;
        this.homeInterface = homeInterface;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {

            URL link=new URL(StaticManager.URL_COURSES);
            DateFormat format = new SimpleDateFormat(DateAdapter.XML_COURSE_DATE_FORMAT, Locale.ITALY);
            RegistryMatcher m= new RegistryMatcher();
            m.bind(Date.class, new XmlDateFormatAdapter(format));
            Serializer serializer=new Persister(m);
            CoursePage coursePage=serializer.read(CoursePage.class,link.openStream());
            database.insertCourses(coursePage);
            Log.i(TAG, "Corsi inseriti correttamente");
            RssFeed rssFeed= RssReader.read(new URL(StaticManager.URL_NEWS));
            database.insertRssFeed(rssFeed);
            Log.i(TAG, "News updated successfully");
            homeInterface.refreshHome();
        }catch (Exception e){
            Log.e(TAG,"aggiornamento non completato");
            e.printStackTrace();
        }
        return null;
    }
}
