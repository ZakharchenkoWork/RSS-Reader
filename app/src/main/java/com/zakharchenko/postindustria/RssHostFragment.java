package com.zakharchenko.postindustria;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zakharchenko.postindustria.rest.RssFeed;

/**
 * Created by kostya on 24.12.2016.
 */

public class RssHostFragment extends Fragment {
    private View rootView;
    private RssFeed itemsToShow;

    public void setItemsToShow(RssFeed itemsToShow) {
        this.itemsToShow = itemsToShow;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.rss_host, container, false);
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }

        }

        if(getActivity() == null || getFragmentManager() == null || itemsToShow == null){
            return null;
        }
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        for (int i = 0; i < itemsToShow.getRssItems().size(); i++) {
            RssItemFragment itemFragment = new RssItemFragment();
            itemFragment.setItemToShow(itemsToShow.getRssItems().get(i));
            ft.add(R.id.content, itemFragment);
        }
        ft.commitAllowingStateLoss();
        return rootView;
    }

}
