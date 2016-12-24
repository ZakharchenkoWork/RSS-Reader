package com.zakharchenko.postindustria.rest;

/**
 * Created by Android-Developer on 24.12.2016.
 */

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;


import java.net.URL;

import java.util.List;

/**
 * Created by kostya on 04.05.2016.
 */
public class RssLoader extends AsyncTask<Void, Void, RssFeed> {

    private final String dataSource;
    //Listener to be called after all data is loaded
    private OnLoadFinishListener onLoadFinishListener = new OnLoadFinishListener() {
        @Override
        public void onFinish(RssFeed feed) {
            Log.w("OnLoadFinishListener", "JSONLoader: Custom listener is not defined");
        }
    };


    /**
     * @param dataURL              link to the JSON file.
     * @param onLoadFinishListener implement this interface to get results in place
     *                             where you create object of this class
     */
    public RssLoader(@NonNull String dataURL, @NonNull OnLoadFinishListener onLoadFinishListener) {

        this.onLoadFinishListener = onLoadFinishListener;
        this.dataSource = dataURL;
        this.execute();
    }


    @Override
    protected RssFeed doInBackground(Void... params) {
        RssFeed feed = null;
        try {
            feed = RssReader.read(new URL(dataSource));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return feed;

    }

    @Override
    protected void onPostExecute(RssFeed feed) {
        super.onPostExecute(feed);
        onLoadFinishListener.onFinish(feed);

    }

    public OnLoadFinishListener getOnLoadFinishListener() {
        return onLoadFinishListener;
    }

    public interface OnLoadFinishListener {
        void onFinish(RssFeed feed);
    }

}
