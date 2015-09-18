package jhonnyhueller.venetocorsi.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.webkit.WebView;
import android.widget.TextView;

import jhonnyhueller.venetocorsi.adapters.DateAdapter;
import jhonnyhueller.venetocorsi.database.DatabaseAdapter;
import jhonnyhueller.venetocorsi.R;
import jhonnyhueller.venetocorsi.rss.RssItem;

public class RssActivity extends AppCompatActivity {

    public static final String ID_RSS = "id_rss";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss);
        long id=getIntent().getLongExtra(ID_RSS,-1);
        DatabaseAdapter adapter=new DatabaseAdapter(getApplicationContext());
        adapter.open();
        RssItem rss=adapter.getRss(id);
        TextView title= (TextView) findViewById(R.id.textViewRssTitle);
        TextView link= (TextView) findViewById(R.id.textViewLink);
        TextView date= (TextView) findViewById(R.id.textViewDateRss);
        WebView content= (WebView) findViewById(R.id.webViewContentRss);
        title.setText(rss.getTitle());
        link.setText(Html.fromHtml("<a href=\"" + rss.getLink() + "\">" + getResources().getString(R.string.str_link_rss_detail) + "</a>"));
        link.setMovementMethod(LinkMovementMethod.getInstance());
        date.setText(DateAdapter.formatShortStringFromDate(rss.getPubDate()));
        content.loadDataWithBaseURL(null, "<style>img{display: inline; height: auto; max-width: 100%;}</style>" + rss.getDescription(), null, "UTF-8", null);
        adapter.setNewsRead(id);
        adapter.close();
    }
}
