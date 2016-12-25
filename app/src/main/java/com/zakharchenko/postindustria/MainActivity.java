package com.zakharchenko.postindustria;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.zakharchenko.postindustria.rest.RssFeed;
import com.zakharchenko.postindustria.rest.RssLoader;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;

    int menuItemLastId = 100;
    PagerAdapter adapter;
    TabLayout tabLayout;
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

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final HackyViewPager viewPager = (HackyViewPager) findViewById(R.id.pager);

        adapter = new PagerAdapter(getFragmentManager(), null);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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

    final String TABS_TAG = "TAG";

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.addRss) {
            new OneButtonDialog(MainActivity.this, getString(R.string.add_rss_feed_message), OneButtonDialog.HAS_EDIT_TEXT_WITH_HINT + getString(R.string.add_rss_feed_message), R.drawable.icon, new OneButtonDialog.OKListener() {
                @Override
                public void onOKpressed(final String data) {
                    new RssLoader("http://www.economist.com/sections/culture/rss.xml", new RssLoader.OnLoadFinishListener() {
                        @Override
                        public void onFinish(final RssFeed feed) {
                           // Bundle bundle = new Bundle();
                            //bundle.putParcelable("feed", feed);
                            //FragmentTabHost host = (FragmentTabHost) findViewById(R.id.tabHost);
                            addNewItem(feed.getTitle());
                            adapter.addFeed(feed);
                            tabLayout.addTab(tabLayout.newTab().setText(feed.getTitle()));

                        }
                    });
                }
            });
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    TabHost.TabContentFactory tabFactory = new TabHost.TabContentFactory() {
        @Override
        public View createTabContent(String tag) {
            View tab = getLayoutInflater().inflate(R.layout.tab_item_layout, null);

            return tab;
        }
    };

    public boolean addNewItem(String itemName) {

        int id = ++menuItemLastId;
        final Menu menu = navigationView.getMenu();
        final MenuItem item = menu.add(R.id.rssFeedsList, id, id, "");
        View layout = getLayoutInflater().inflate(R.layout.menu_item, null);
        TextView title = (TextView) layout.findViewById(R.id.title);
        title.setText(itemName);
        item.setActionView(layout);

        layout.findViewById(R.id.icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.removeItem(item.getItemId());
            }
        });

        return true;
    }


}
