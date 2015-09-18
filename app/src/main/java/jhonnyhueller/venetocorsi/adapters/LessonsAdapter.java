package jhonnyhueller.venetocorsi.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import jhonnyhueller.venetocorsi.R;
import jhonnyhueller.venetocorsi.models.Lesson;

/**
 * Created by jhonny
 */
public class LessonsAdapter extends CursorAdapter {
    private final String TAG = getClass().getSimpleName();

    public LessonsAdapter(Context context, Cursor cursor, int i) {
        super(context,cursor,i);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        return layoutInflater.inflate(R.layout.lesson_row,null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Date date;
        try {
            date=DateAdapter.dateJFromFormatDb(cursor.getString(cursor.getColumnIndex(Lesson.INIZIO_LEZIONE)));
            Calendar calendar= Calendar.getInstance(TimeZone.getDefault(),Locale.getDefault());
            calendar.setTime(date);
            ((TextView) view.findViewById(R.id.textView_row_lesson_day)).setText(Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)));
            ((TextView) view.findViewById(R.id.textView_row_lesson_month)).setText(calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ((TextView) view.findViewById(R.id.textView_row_lesson_place)).setText(cursor.getString(cursor.getColumnIndex(Lesson.LUOGO_LEZIONE)));
    }
}
