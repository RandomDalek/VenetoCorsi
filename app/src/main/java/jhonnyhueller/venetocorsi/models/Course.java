package jhonnyhueller.venetocorsi.models;

import android.util.Log;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

/**
 * Created by jhonny
 */
@Root
public class Course {
    private final String TAG=getClass().getSimpleName();
    @Element
    private long id_corso;
    public static final String ID_CORSO="id_corso";
    @Element
    private String titolo_corso;
    public static final String TITOLO_CORSO="titolo_corso";
    @Element
    private String descrizione_corso;
    public static final String DESCRIZIONE_CORSO="descrizione_corso";
    @Element
    private short iscritti_corso;
    public static final String ISCRITTI_CORSO="iscritti_corso";
    @Element
    private short iscritti_massimi;
    public static final String ISCRITTI_MASSIMI="iscritti_massimi";
    @Element
    private short stato;
    public static final String STATO="stato";
    @ElementList
    private ArrayList<Lesson> lessons;

    public static int COURSE_STATUS_PLANNED = 0;
    public static int COURSE_STATUS_AVIABLE = 1;
    public static int COURSE_STATUS_CLOSED = 2;
    public static int COURSE_STATUS_STARTED = 3;
    public static int COURSE_STATUS_ENDED = 4;
    public static int COURSE_STATUS_SUSPENDED = 5;

    public Course() {
    }

    public long getId_corso() {
        return id_corso;
    }

    public void setId_corso(long id_corso) {
        this.id_corso = id_corso;
    }

    public String getTitolo_corso() {
        return titolo_corso;
    }

    public void setTitolo_corso(String titolo_corso) {
        this.titolo_corso = titolo_corso;
    }

    public String getDescrizione_corso() {
        return descrizione_corso;
    }

    public void setDescrizione_corso(String descrizione_corso) {
        this.descrizione_corso = descrizione_corso;
    }

    public short getIscritti_corso() {
        return iscritti_corso;
    }

    public void setIscritti_corso(short iscritti_corso) {
        this.iscritti_corso = iscritti_corso;
    }

    public short getIscritti_massimi() {
        return iscritti_massimi;
    }

    public void setIscritti_massimi(short iscritti_massimi) {
        this.iscritti_massimi = iscritti_massimi;
    }

    public short getStato() {
        return stato;
    }

    public void setStato(short stato) {
        this.stato = stato;
    }

    public void setLessons(ArrayList<Lesson> lessons) {
        this.lessons = lessons;
    }

    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    public void toLog() {
        Log.i(TAG,"Course: "+id_corso+", "+titolo_corso+", "+descrizione_corso+", "+iscritti_corso+", "+iscritti_massimi+", "+stato+", lessons "+lessons.size());
        for (Lesson l: lessons){
            l.toLog();
        }
    }

}
