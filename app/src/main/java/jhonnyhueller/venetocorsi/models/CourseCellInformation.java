package jhonnyhueller.venetocorsi.models;

/**
 * Created by jhonny
 */
public class CourseCellInformation {
    private final String TAG = getClass().getSimpleName();
    private String title;
    private int id_icon;

    public CourseCellInformation() {
    }

    public CourseCellInformation(String title, int id_icon) {
        this.title = title;
        this.id_icon = id_icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId_icon() {
        return id_icon;
    }

    public void setId_icon(int id_icon) {
        this.id_icon = id_icon;
    }
}
