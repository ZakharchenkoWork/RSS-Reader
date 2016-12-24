package com.zakharchenko.postindustria;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.zakharchenko.postindustria.rest.RssItem;
import com.zakharchenko.postindustria.rest.RssLoader;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.resortByDate) {
            //TODO: resort action
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.addRss) {
           new OneButtonDialog(MainActivity.this, getString(R.string.add_rss_feed_message), OneButtonDialog.HAS_EDIT_TEXT_WITH_HINT+getString(R.string.add_rss_feed_message), R.drawable.icon, new OneButtonDialog.OKListener() {
               @Override
               public void onOKpressed() {
                   new RssLoader("http://feeds.reuters.com/Reuters/worldNews", new RssLoader.OnLoadFinishListener() {
                       @Override
                       public void onFinish(List<RssItem> rssResults) {
                           Log.d("ok", ""+rssResults.size());
                           FragmentTransaction ft = getFragmentManager().beginTransaction();

                           for (int i = 0; i < rssResults.size(); i++) {
                               RssItemFragment itemFragment = new RssItemFragment();
                               itemFragment.setItemToShow(rssResults.get(i));
                               ft.add(R.id.content, itemFragment);
                               Log.d("ok " + i, ""+rssResults.get(i).getTitle());

                           }
                           ft.commitAllowingStateLoss();
                       }
                   });
               }
           });
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
