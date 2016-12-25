package com.zakharchenko.postindustria;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.zakharchenko.postindustria.rest.RssFeed;

import java.util.ArrayList;

/**
 * Created by Ui-Developer on 25.12.2016.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    ArrayList<RssFeed> feeds = new ArrayList<>();
    ArrayList<RssHostFragment> fragments = new ArrayList<>();
    public PagerAdapter(FragmentManager fm, ArrayList<RssFeed> feeds) {
        super(fm);
        if (feeds != null) {
            this.feeds = feeds;
            for (int i = 0; i < feeds.size(); i++) {
                RssHostFragment rssHostFragment = new RssHostFragment();
                rssHostFragment.setItemsToShow(feeds.get(i));
                fragments.add(rssHostFragment);
            }
        }
    }

    public void addFeed(RssFeed feed) {
        feeds.add(feed);
        RssHostFragment rssHostFragment = new RssHostFragment();
        rssHostFragment.setItemsToShow(feed);
        fragments.add(rssHostFragment);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return feeds.size();
    }
}
