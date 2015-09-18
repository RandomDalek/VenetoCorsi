package jhonnyhueller.venetocorsi.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import jhonnyhueller.venetocorsi.models.NavDrawerItem;
import jhonnyhueller.venetocorsi.R;

/**
 * Created by jhonny
 */
public class NavDrawerListAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<NavDrawerItem> navDrawerItems;

    public NavDrawerListAdapter(Context context,ArrayList<NavDrawerItem> navDrawerItems){
        this.context=context;
        this.navDrawerItems=navDrawerItems;
    }


    @Override
    public int getCount() {
        return navDrawerItems.size();
    }

    @Override
    public Object getItem(int position) {
        return navDrawerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            LayoutInflater mLayoutInflater= (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView=mLayoutInflater.inflate(R.layout.drawer_list_item,null);
        }
        ImageView imageView= (ImageView) convertView.findViewById(R.id.icon);
        TextView textViewtitle=(TextView)convertView.findViewById(R.id.title);
        TextView textViewCount=(TextView)convertView.findViewById(R.id.counter);
        imageView.setImageResource(navDrawerItems.get(position).getIcon());
        textViewtitle.setText(navDrawerItems.get(position).getTitle());

        if (navDrawerItems.get(position).isCounterVisible()){
            textViewCount.setText(navDrawerItems.get(position).getCount());
        }else {
            textViewCount.setVisibility(View.GONE);
        }
        return convertView;
    }

}
