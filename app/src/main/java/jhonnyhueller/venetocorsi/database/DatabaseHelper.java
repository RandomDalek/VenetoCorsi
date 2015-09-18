package jhonnyhueller.venetocorsi.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import jhonnyhueller.venetocorsi.models.Course;
import jhonnyhueller.venetocorsi.models.Lesson;
import jhonnyhueller.venetocorsi.rss.RssItem;


/**
 * Created by jhonny
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private final String TAG=getClass().getSimpleName();
    private static final String DATABASE_NAME="VenetoCorsi.db";
    private static final int DATABASE_VERSION=5;
    private final String CREATE=" CREATE TABLE IF NOT EXISTS ";
    public static final String RSS_ITEMS=" rss_items ";
    public static final String COURSES=" courses ";
    public static final String LESSONS =" lessons ";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableRssItems(db);
        createTableCourses(db);
        createTableLessons(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG,"upgrading...");
        onDelete(db);
        onCreate(db);
        Log.i(TAG, "upgraded from " + oldVersion + " to " + newVersion);
    }

    private void onDelete(SQLiteDatabase db) {
        String DROP = " DROP TABLE IF EXISTS ";
        db.execSQL(DROP + RSS_ITEMS);
        db.execSQL(DROP + COURSES);
        db.execSQL(DROP + LESSONS);
    }
    public static String[] getRssItemColumns() {
        return new String[]{"_id", RssItem.TITLE,RssItem.LINK,RssItem.READ_FLAG,RssItem.PUB_DATE,RssItem.DESCRIPTION,RssItem.CONTENT};
    }

    private void createTableRssItems(SQLiteDatabase db) {
        Log.i(TAG, "creating tables...");
        String CREATE_TABLE_RSS_STATEMENT = CREATE + RSS_ITEMS + "(" +
                " _id INTEGER PRIMARY KEY, " +
                RssItem.LINK + " TEXT UNIQUE, " +
                RssItem.TITLE + " TEXT, " +
                RssItem.DESCRIPTION + " TEXT, " +
                RssItem.PUB_DATE + " TEXT," +
                RssItem.CONTENT + " TEXT, " +
                RssItem.READ_FLAG + " INTEGER DEFAULT " + RssItem.NOT_READ_FLAG + " );";
        db.execSQL(CREATE_TABLE_RSS_STATEMENT);
        Log.i(TAG, CREATE_TABLE_RSS_STATEMENT);
        Log.i(TAG, "table:" + RSS_ITEMS + "created");
    }

    public static String[] getCoursesColums() {
        return new String[]{"_id",Course.ID_CORSO,Course.TITOLO_CORSO,Course.DESCRIZIONE_CORSO,Course.ISCRITTI_CORSO,Course.ISCRITTI_MASSIMI,Course.STATO};
    }

    private void createTableCourses(SQLiteDatabase db) {
        Log.i(TAG, "creating tables...");
        String CREATE_TABLE_COURSES=CREATE+COURSES+"("+
                " _id INTEGER PRIMARY KEY, " +
                Course.ID_CORSO+" INTEGER UNIQUE, " +
                Course.TITOLO_CORSO+" TEXT, "+
                Course.DESCRIZIONE_CORSO+" TEXT, " +
                Course.ISCRITTI_CORSO+" INTEGER, " +
                Course.ISCRITTI_MASSIMI+" INTEGER, "+
                Course.STATO+" INTEGER); ";
        db.execSQL(CREATE_TABLE_COURSES);
        Log.i(TAG, CREATE_TABLE_COURSES);
        Log.i(TAG,"table:"+COURSES+"created");
    }

    public static String[] getLessonsColums() {
        return new String[]{"_id",Lesson.ID_LEZIONE,Lesson.FINE_LEZIONE,Lesson.INIZIO_LEZIONE,Lesson.LUOGO_LEZIONE,Course.ID_CORSO};
    }

    private void createTableLessons(SQLiteDatabase db) {
        Log.i(TAG, "creating tables...");
        String CREATE_TABLE_LESSONS=CREATE+ LESSONS +"("+
                " _id INTEGER PRIMARY KEY, "+
                Lesson.ID_LEZIONE+" INTEGER UNIQUE, "+
                Lesson.INIZIO_LEZIONE+" TEXT, "+
                Lesson.FINE_LEZIONE+" TEXT, "+
                Lesson.LUOGO_LEZIONE+" TEXT," +
                Course.ID_CORSO+" INTEGER," +
                "FOREIGN KEY("+Course.ID_CORSO+") REFERENCES "+COURSES+"("+Course.ID_CORSO+")); ";
        db.execSQL(CREATE_TABLE_LESSONS);
        Log.i(TAG, CREATE_TABLE_LESSONS);
        Log.i(TAG, "table:" + LESSONS + "created");
    }

}
