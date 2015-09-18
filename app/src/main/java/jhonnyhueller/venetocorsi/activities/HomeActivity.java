package jhonnyhueller.venetocorsi.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;

import jhonnyhueller.venetocorsi.adapters.NavDrawerListAdapter;
import jhonnyhueller.venetocorsi.database.DatabaseAdapter;
import jhonnyhueller.venetocorsi.fragments.CourseFragmentList;
import jhonnyhueller.venetocorsi.fragments.HelpFragment;
import jhonnyhueller.venetocorsi.fragments.RssFragment;
import jhonnyhueller.venetocorsi.interfaces.HomeInterface;
import jhonnyhueller.venetocorsi.models.NavDrawerItem;
import jhonnyhueller.venetocorsi.R;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,HomeInterface {
    private String TAG=getClass().getSimpleName();
    private DatabaseAdapter databaseAdapter;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    ArrayList<NavDrawerItem> navDrawerItems;
    private int current_page=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_home);

        databaseAdapter=new DatabaseAdapter(getApplicationContext(),this);
        databaseAdapter.open();

        mTitle=mDrawerTitle=getTitle();
        navMenuTitles=getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons=getResources().obtainTypedArray(R.array.nav_drawer_icons);


        mDrawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList= (ListView) findViewById(R.id.list_slidermenu);

        setDrawerMenu();

        // enabling action bar app icon and behaving it as toggle button
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        mActionBarDrawerToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.app_name,R.string.app_name){
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                if(getActionBar()!=null) {
                    getActionBar().setTitle(mDrawerTitle);
                    // calling onPrepareOptionsMenu() to hide action bar icons
                    invalidateOptionsMenu();
                }
            }
        };
        setDrawerListener(savedInstanceState);
    }

    private void setDrawerListener(Bundle savedInstanceState) {
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        if(savedInstanceState==null){
            displayView(0);
        }
        mDrawerList.setOnItemClickListener(this);
    }

    private void setDrawerMenu() {
        navDrawerItems = populateDrawer();
        NavDrawerListAdapter adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
        mDrawerList.setAdapter(adapter);
    }

    private void updateNews() {
        if(internetIsAvailable()){
            Log.i(TAG,"Internet is available");
            databaseAdapter.updateAllData();
            Toast.makeText(getApplicationContext(),R.string.update_text,Toast.LENGTH_SHORT).show();
        }else {
            Log.e(TAG,"Internet is not available");
            Toast.makeText(getApplicationContext(),R.string.internetNotAvailable,Toast.LENGTH_SHORT).show();
        }
    }

    private boolean internetIsAvailable() {
        ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /***
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public void setTitle(CharSequence title) {
        if(getActionBar()!=null){
            mTitle = title;
            getActionBar().setTitle(mTitle);
        }
    }
    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }
    private ArrayList<NavDrawerItem> populateDrawer() {
        ArrayList<NavDrawerItem> navDrawerItems= new ArrayList<>();
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1],navMenuIcons.getResourceId(1,-1),true,""+databaseAdapter.getNumberNewsToRead())); //news
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1), true,""+databaseAdapter.getNumberCourses())); //courses
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1))); //help
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1))); //update
        return navDrawerItems;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        current_page=position;
        displayView(position);

    }

    private void displayView(int position) {
        Fragment fragment=null;
        switch (position){
            case 0:
                Log.i(TAG,"news");
                fragment= RssFragment.newInstance();
                break;
            case 1:
                Log.i(TAG,"courses");
                fragment= CourseFragmentList.newInstance();
                break;
            case 2:
                Log.i(TAG,"help");
                fragment= HelpFragment.newInstance();
                break;
            case 3:
                updateNews();
                break;
            default:
                Log.e(TAG, "not an option");
                break;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_layout_home, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else if (position!=3){
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void rssRowClick(long id) {
        Intent intent=new Intent(getApplicationContext(),RssActivity.class);
        intent.putExtra(RssActivity.ID_RSS,id);
        startActivity(intent);
    }

    @Override
    public void courseCellClick(long id) {
        Log.i(TAG,"cliched course "+id);
        Intent intent=new Intent(getApplicationContext(),CourseActivity.class);
        intent.putExtra(CourseActivity.ID_COURSE,id);
        startActivity(intent);
    }

    @Override
    public void refreshHome() {
        finish();
        startActivity(getIntent());
    }

}
