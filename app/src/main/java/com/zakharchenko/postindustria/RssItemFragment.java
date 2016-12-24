package com.zakharchenko.postindustria;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zakharchenko.postindustria.rest.PictureLoader;
import com.zakharchenko.postindustria.rest.RssItem;

/**
 * Created by Ui-Developer on 24.12.2016.
 */

public class RssItemFragment extends Fragment {
    private View rootView;
    private RssItem itemToShow;

    public void setItemToShow(RssItem itemToShow) {
        this.itemToShow = itemToShow;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(itemToShow == null){
            return null;
        }
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.rss_item, container, false);
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        TextView  title = (TextView) rootView.findViewById(R.id.title);
        title.setText(itemToShow.getTitle());
        TextView  description = (TextView ) rootView.findViewById(R.id.description);
        description.setText(itemToShow.getDescription());

        final ImageView image = (ImageView) rootView.findViewById(R.id.image);
        Log.d("ok", itemToShow.getImageUrl());
        new PictureLoader(itemToShow.getImageUrl(), new PictureLoader.BitmapLoadListener() {
            @Override
            public void onFinish(Bitmap picture) {
                image.setImageBitmap(picture);
            }
        });

        return rootView;
    }
}
