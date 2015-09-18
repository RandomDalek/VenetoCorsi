package jhonnyhueller.venetocorsi.adapters;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import jhonnyhueller.venetocorsi.R;
import jhonnyhueller.venetocorsi.models.Course;

/**
 * Created by jhonny
 */
public class CourseAdapter extends CursorAdapter {
    private final String TAG = getClass().getSimpleName();

    public CourseAdapter(Context context, Cursor cursor,int flags) {
        super(context,cursor,flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        return layoutInflater.inflate(R.layout.course_cell,null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView title= (TextView) view.findViewById(R.id.textView_cell_title_course);
        title.setText(cursor.getString(cursor.getColumnIndex(Course.TITOLO_CORSO)));
    }
}
