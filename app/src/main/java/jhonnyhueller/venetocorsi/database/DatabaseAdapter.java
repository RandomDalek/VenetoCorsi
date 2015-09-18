package jhonnyhueller.venetocorsi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import jhonnyhueller.venetocorsi.adapters.DateAdapter;
import jhonnyhueller.venetocorsi.interfaces.HomeInterface;
import jhonnyhueller.venetocorsi.models.Course;
import jhonnyhueller.venetocorsi.models.CoursePage;
import jhonnyhueller.venetocorsi.models.Lesson;
import jhonnyhueller.venetocorsi.rss.RssFeed;
import jhonnyhueller.venetocorsi.rss.RssItem;
import jhonnyhueller.venetocorsi.tasks.UpdateAllData;
import jhonnyhueller.venetocorsi.tasks.UpdateCourses;
import jhonnyhueller.venetocorsi.tasks.UpdateNews;

/**
 * Created by jhonny
 */
public class DatabaseAdapter {
    private final String TAG=getClass().getSimpleName();
    private Context context;
    private SQLiteDatabase database;
    private String DESC = " DESC ";
    private HomeInterface homeInterface;

    public DatabaseAdapter(Context context, HomeInterface homeInterface){this.context=context; this.homeInterface=homeInterface;}
    public DatabaseAdapter(Context context){this.context=context;}


    public void open(){
        DatabaseHelper helper=new DatabaseHelper(context);
        database=helper.getWritableDatabase();
    }

    public void close(){database.close();}

    public void insertRssFeed(RssFeed rssFeed){
        for (RssItem i:rssFeed.getRssItems()){
            insertRssItem(i);
        }
    }

    public long insertRssItem(RssItem rssItem){
        Log.i(TAG, "adding RssItem: " + rssItem.getTitle());
        long id;
        ContentValues values;
        try{
            values=createContentValuesRssItem(rssItem);
            id=database.insertWithOnConflict(DatabaseHelper.RSS_ITEMS, null, values,SQLiteDatabase.CONFLICT_IGNORE);
            Log.i(TAG,"success with no errors");
        }catch (Exception e){
            e.printStackTrace();
            id=-1;
        }

        return id;
    }

    public Cursor fetchRssItems(){
        return database.query(DatabaseHelper.RSS_ITEMS,DatabaseHelper.getRssItemColumns(),null,null,null,null,RssItem.PUB_DATE+ DESC);
    }

    public Cursor fetchCourses() {
        return database.query(DatabaseHelper.COURSES,DatabaseHelper.getCoursesColums(),null,null,null,null,Course.STATO+DESC);
    }

    private ContentValues createContentValuesRssItem(RssItem rssItem) {
        ContentValues values=new ContentValues();
        values.put(RssItem.TITLE,rssItem.getTitle());
        values.put(RssItem.LINK,rssItem.getLink());
        values.put(RssItem.DESCRIPTION,rssItem.getDescription());
        values.put(RssItem.PUB_DATE, rssItem.getDatabasePubDate());
        values.put(RssItem.CONTENT, rssItem.getContent());
        return values;
    }

    public void updateAllData() {
        //updateCoursesAsync();
        //updateNewsAsync();
        UpdateAllData updateAllData=new UpdateAllData(this,homeInterface);
        updateAllData.execute();
    }

    private void updateCoursesAsync() {
        UpdateCourses updateCourses=new UpdateCourses(this);
        updateCourses.execute();
    }

    private void updateNewsAsync() {
        UpdateNews updateNews=new UpdateNews(this);
        updateNews.execute();
    }

    public int getNumberNewsToRead() {
        String query=
                " SELECT COUNT(*) " +
                        " FROM "+DatabaseHelper.RSS_ITEMS+" " +
                        " WHERE "+RssItem.READ_FLAG+" = "+RssItem.NOT_READ_FLAG;
        Cursor c= database.rawQuery(query, null);
        c.moveToFirst();
        int count= c.getInt(0);
        c.close();
        return count;
    }

    public RssItem getRss(long id) {
        RssItem item=new RssItem();

        Cursor c=database.query(DatabaseHelper.RSS_ITEMS, DatabaseHelper.getRssItemColumns(), "_id=" + id, null, null, null, null);
        c.moveToFirst();
        item.setTitle(c.getString(c.getColumnIndex(RssItem.TITLE)));
        try {
            item.setPubDate(DateAdapter.dateJFromFormatDb(c.getString(c.getColumnIndex(RssItem.PUB_DATE))));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        item.setLink(c.getString(c.getColumnIndex(RssItem.LINK)));
        item.setDescription(c.getString(c.getColumnIndex(RssItem.DESCRIPTION)));
        item.setContent(c.getString(c.getColumnIndex(RssItem.CONTENT)));
        c.close();
        return item;
    }

    public void setNewsRead(long id) {
        ContentValues contentValues=new ContentValues();
        contentValues.put(RssItem.READ_FLAG, RssItem.ALREADY_READ_FLAG);
        database.update(DatabaseHelper.RSS_ITEMS, contentValues, "_id=" + id, null);
    }

    public void insertCourses(CoursePage coursePage) {
        for (Course c:coursePage.getCourses()){
            insertCourse(c);
        }
    }

    public long insertCourse(Course c) {
        Log.i(TAG, "Course RssItem: " + c.getTitolo_corso());
        long id;
        ContentValues valuesCourse=createContentValuesCourse(c);
        try{
            id=database.insertWithOnConflict(DatabaseHelper.COURSES,null,valuesCourse,SQLiteDatabase.CONFLICT_REPLACE);
            Log.i(TAG,"Course "+c.getTitolo_corso()+" data added");
            insertLessons(c.getLessons(), c.getId_corso());
            Log.i(TAG, "Course " + c.getTitolo_corso() + " lessons added");
            c.toLog();
        }catch (Exception e){
            id=-1;
            e.printStackTrace();
        }
        return id;
    }

    private void insertLessons(ArrayList<Lesson> lessons,long id_corso) {
        for(Lesson l:lessons){
            insertLesson(l,id_corso);
        }
    }

    private long insertLesson(Lesson l, long id_corso) {
        long id;
        ContentValues values=createContentValueLesson(l,id_corso);
        try{
            id=database.insertWithOnConflict(DatabaseHelper.LESSONS,null,values,SQLiteDatabase.CONFLICT_REPLACE);
            Log.i(TAG,"Lesson "+l.getId_lezione()+" added to course "+id_corso);
        }catch (Exception e){
            id=-1;
            e.printStackTrace();
        }

        return id;
    }

    private ContentValues createContentValueLesson(Lesson l, long id_corso) {
        ContentValues values=new ContentValues();
        values.put(Lesson.ID_LEZIONE,l.getId_lezione());
        values.put(Lesson.INIZIO_LEZIONE,DateAdapter.formatDbFromDate(l.getInizio_lezione()));
        values.put(Lesson.FINE_LEZIONE,DateAdapter.formatDbFromDate(l.getFine_lezione()));
        values.put(Lesson.LUOGO_LEZIONE,l.getLuogo_lezione());
        values.put(Course.ID_CORSO,id_corso);
        return values;
    }

    private ContentValues createContentValuesCourse(Course c) {
        ContentValues values=new ContentValues();
        values.put(Course.ID_CORSO,c.getId_corso());
        values.put(Course.TITOLO_CORSO,c.getTitolo_corso());
        values.put(Course.DESCRIZIONE_CORSO,c.getDescrizione_corso());
        values.put(Course.ISCRITTI_CORSO,c.getIscritti_corso());
        values.put(Course.ISCRITTI_MASSIMI,c.getIscritti_massimi());
        values.put(Course.STATO,c.getStato());
        return values;
    }

    public int getNumberCourses() {
        String query=
                " SELECT COUNT(*) " +
                        " FROM "+DatabaseHelper.COURSES;
        Cursor c= database.rawQuery(query, null);
        c.moveToFirst();
        int count= c.getInt(0);
        c.close();
        return count;
    }

    public String[] getCourseTitles() {
        Cursor c=database.query(DatabaseHelper.COURSES,DatabaseHelper.getCoursesColums(),null,null,null,null,Course.TITOLO_CORSO+DESC);
        String[] titles=new String[c.getCount()];
        c.moveToFirst();
        for (int i=0;i<c.getCount();i++,c.moveToNext()){
            titles[i]=c.getString(c.getColumnIndex(Course.TITOLO_CORSO));
        }
        c.close();
        return titles;
    }

    public Course getCourse(long id) {
        Course course=new Course();

        Cursor cursor=database.query(DatabaseHelper.COURSES,DatabaseHelper.getCoursesColums(),"_id="+id,null,null,null,null);
        cursor.moveToFirst();

        course.setTitolo_corso(cursor.getString(cursor.getColumnIndex(Course.TITOLO_CORSO)));
        course.setIscritti_massimi(cursor.getShort(cursor.getColumnIndex(Course.ISCRITTI_MASSIMI)));
        course.setDescrizione_corso(cursor.getString(cursor.getColumnIndex(Course.DESCRIZIONE_CORSO)));
        course.setStato(cursor.getShort(cursor.getColumnIndex(Course.STATO)));
        course.setId_corso(cursor.getLong(cursor.getColumnIndex(Course.ID_CORSO)));
        course.setIscritti_corso(cursor.getShort(cursor.getColumnIndex(Course.ISCRITTI_CORSO)));

        cursor.close();

        course.setLessons(getLessonsCourse(id));

        return course;
    }

    private ArrayList<Lesson> getLessonsCourse(long id) {
        ArrayList<Lesson>lessons=new ArrayList<>();
        Cursor c=database.query(DatabaseHelper.LESSONS,DatabaseHelper.getLessonsColums(),Course.ID_CORSO+"="+id,null,null,null,Lesson.INIZIO_LEZIONE+DESC);
        Log.i(TAG, "number of lesson: " + c.getCount());
        Lesson l;
        Date date;
        while (c.moveToNext()){

            l=new Lesson();
            l.setId_lezione(c.getLong(c.getColumnIndex(Lesson.ID_LEZIONE)));
            try {
                date=DateAdapter.dateJFromFormatDb(c.getString(c.getColumnIndex(Lesson.INIZIO_LEZIONE)));
                l.setInizio_lezione(date);
                date=DateAdapter.dateJFromFormatDb(c.getString(c.getColumnIndex(Lesson.FINE_LEZIONE)));
                l.setFine_lezione(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            l.setLuogo_lezione(c.getString(c.getColumnIndex(Lesson.LUOGO_LEZIONE)));

            lessons.add(l);
        }
        c.close();
        return lessons;
    }

    public Cursor fetchLessonsOfCourse(long id_course) {
        return database.query(DatabaseHelper.LESSONS,DatabaseHelper.getLessonsColums(),Course.ID_CORSO+"="+id_course,null,null,null,Lesson.INIZIO_LEZIONE+DESC);
    }
}
