package jhonnyhueller.venetocorsi.adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.text.ParseException;

import jhonnyhueller.venetocorsi.R;
import jhonnyhueller.venetocorsi.rss.RssItem;


/**
 * Created by jhonny
 */
public class RssAdapter extends CursorAdapter {
    private final String TAG = getClass().getSimpleName();

    public RssAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater layoutInflater= LayoutInflater.from(viewGroup.getContext());
        return layoutInflater.inflate(R.layout.rss_row,null);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView title= (TextView) view.findViewById(R.id.textViewTitleRssRow);
        TextView date= (TextView) view.findViewById(R.id.textViewDateRssRow);
        title.setText(cursor.getString(cursor.getColumnIndex(RssItem.TITLE)));
        try {
            date.setText(DateAdapter.formatShortStringFromDate(DateAdapter.dateJFromFormatDb(cursor.getString(cursor.getColumnIndex(RssItem.PUB_DATE)))));
        } catch (ParseException e) {
            Log.i(TAG,"date: "+cursor.getString(cursor.getColumnIndex(RssItem.PUB_DATE)));
            date.setText("unparseable");
            e.printStackTrace();
        }
    }
}
