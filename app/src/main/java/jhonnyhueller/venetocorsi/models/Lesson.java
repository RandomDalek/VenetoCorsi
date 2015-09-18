package jhonnyhueller.venetocorsi.models;

import android.util.Log;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.Date;

/**
 * Created by jhonny
 */
@Root
public class Lesson {
    private final String TAG=getClass().getSimpleName();
    @Element
    private long id_lezione;
    public static final String ID_LEZIONE="id_lezione";
    @Element
    private Date inizio_lezione;
    public static final String INIZIO_LEZIONE="inizio_lezione";
    @Element
    private Date fine_lezione;
    public static final String FINE_LEZIONE="fine_lezione";
    @Element
    private String luogo_lezione;
    public static final String LUOGO_LEZIONE="luogo_lezione";

    public Lesson() {
    }

    public long getId_lezione() {
        return id_lezione;
    }

    public void setId_lezione(long id_lezione) {
        this.id_lezione = id_lezione;
    }

    public Date getInizio_lezione() {
        return inizio_lezione;
    }

    public void setInizio_lezione(Date inizio_lezione) {
        this.inizio_lezione = inizio_lezione;
    }

    public Date getFine_lezione() {
        return fine_lezione;
    }

    public void setFine_lezione(Date fine_lezione) {
        this.fine_lezione = fine_lezione;
    }

    public String getLuogo_lezione() {
        return luogo_lezione;
    }

    public void setLuogo_lezione(String luogo_lezione) {
        this.luogo_lezione = luogo_lezione;
    }

    public void toLog() {
        Log.i(TAG,"Lesson: "+id_lezione+", "+inizio_lezione.toString()+", "+fine_lezione.toString()+", "+luogo_lezione);
    }
}
