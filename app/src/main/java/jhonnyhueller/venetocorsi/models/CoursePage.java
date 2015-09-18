package jhonnyhueller.venetocorsi.models;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

/**
 * Created by jhonny
 */
@Root (name = "courses")
public class CoursePage {
    @ElementList (inline = true)
    private ArrayList<Course>courses;
    public CoursePage(){}
    public CoursePage(ArrayList<Course> courses){
        this.courses=courses;
    }
    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }
}
