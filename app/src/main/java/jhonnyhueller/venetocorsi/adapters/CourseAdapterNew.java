package jhonnyhueller.venetocorsi.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.zip.Inflater;

import jhonnyhueller.venetocorsi.R;
import jhonnyhueller.venetocorsi.models.Course;

/**
 * Created by jhonny
 */
public class CourseAdapterNew extends CursorAdapter {
    private final String TAG = getClass().getSimpleName();

    public CourseAdapterNew(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.course_row,null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView title= (TextView) view.findViewById(R.id.textView_row_course_title);
        TextView description= (TextView) view.findViewById(R.id.textView_row_course_description);
        ImageView icon= (ImageView) view.findViewById(R.id.imageView_row_course_icon);
        title.setText(cursor.getString(cursor.getColumnIndex(Course.TITOLO_CORSO)));
        description.setText(cursor.getString(cursor.getColumnIndex(Course.DESCRIZIONE_CORSO)));
        icon.setImageResource(R.drawable.doctor);
    }
}
