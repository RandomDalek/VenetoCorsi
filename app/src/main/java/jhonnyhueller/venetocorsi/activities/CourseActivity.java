package jhonnyhueller.venetocorsi.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import jhonnyhueller.venetocorsi.R;
import jhonnyhueller.venetocorsi.database.DatabaseAdapter;
import jhonnyhueller.venetocorsi.fragments.LessonsFragment;
import jhonnyhueller.venetocorsi.interfaces.CourseInterface;
import jhonnyhueller.venetocorsi.models.Course;

public class CourseActivity extends AppCompatActivity implements CourseInterface{
    private final String TAG=getClass().getSimpleName();
    public static final String ID_COURSE = "id_course";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        long id=getIntent().getLongExtra(ID_COURSE,-1);
        DatabaseAdapter adapter=new DatabaseAdapter(getApplicationContext());
        adapter.open();
        Course course=adapter.getCourse(id);
        course.toLog();
        ((TextView)findViewById(R.id.textView_activity_title_course)).setText(course.getTitolo_corso());
        ((TextView)findViewById(R.id.textView_activity_course_description)).setText(course.getDescrizione_corso());
        ((TextView)findViewById(R.id.textView_activity_course_number_subscribes)).setText(Integer.toString(course.getIscritti_corso()));
        ((TextView)findViewById(R.id.textView_activity_course_number_max_sub)).setText(Integer.toString(course.getIscritti_massimi()));
        ((TextView)findViewById(R.id.textView_activity_course_state)).setText(getResources().getStringArray(R.array.course_states)[course.getStato()]);
        getFragmentManager().beginTransaction().add(R.id.frameLayout_activity_course_lesson_list,LessonsFragment.newInstance(course.getId_corso())).commit();
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);

    }

    @Override
    public void lessonClick(long id) {
        Log.i(TAG, "lesson " + id + " clicked");
    }
}
