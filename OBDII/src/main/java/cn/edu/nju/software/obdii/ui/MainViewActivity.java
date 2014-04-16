package cn.edu.nju.software.obdii.ui;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import cn.edu.nju.software.obdii.R;

/**
 * Created by rogers on 4/15/14.
 */
public class MainViewActivity extends Activity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private int[] mDrawerIcons;
    private String[] mDrawerOptions;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainview);
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerList = (ListView)findViewById(R.id.left_drawer);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerIcons = new int[] { //set icon of each drawer item
                R.drawable.drawer_car_route,
                R.drawable.drawer_obd_data,
                R.drawable.drawer_travel_info,
                R.drawable.drawer_alert_check,
                R.drawable.drawer_statistic,
                R.drawable.drawer_statistic,
                R.drawable.drawer_statistic,
                R.drawable.drawer_statistic
                };
        mDrawerOptions = new String[] { //set title of each drawer item
                getString(R.string.car_route),
                getString(R.string.OBD_data),
                getString(R.string.travel_info),
                getString(R.string.check_alert),
                getString(R.string.statistics),
                getString(R.string.oil_consume_average),
                getString(R.string.speed_average),
                getString(R.string.mileage),

        };
        mDrawerList.setAdapter(new MyAdapter(
                getActionBar().getThemedContext(), R.layout.drawer_item_title,
                mDrawerIcons, mDrawerOptions));

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.apptheme_ic_navigation_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    public class MyAdapter extends ArrayAdapter<String> {
        private int[] mImgs;
        private String[] mTexts;
        private int mViewSourceId;
        private LayoutInflater mInflater;
        public MyAdapter(Context context, int viewResourceId, int[] imgs, String[] texts) {
            super(context, viewResourceId, texts);
            mImgs = imgs;
            mTexts = texts;
            mViewSourceId = viewResourceId;
            mInflater = (LayoutInflater)context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent){

            if(position > 4) {
                convertView = mInflater.inflate(R.layout.drawer_item_subtitle, null);
                TextView textView = (TextView)convertView.findViewById(R.id.subtitle_text);
                textView.setText(mTexts[position]);
            }
            else {
                convertView = mInflater.inflate(mViewSourceId, null);
                ImageView imageView = (ImageView)convertView.findViewById(R.id.option_icon);
                imageView.setImageResource(mImgs[position]);
                TextView textView = (TextView)convertView.findViewById(R.id.option_text);
                textView.setText(mTexts[position]);
            }

            return convertView;
        }
    }
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        mDrawerList.setItemChecked(position, true);
        setTitle(mDrawerOptions[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
