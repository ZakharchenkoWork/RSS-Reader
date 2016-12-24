package com.zakharchenko.postindustria.rest;

/**
 * Created by Android-Developer on 24.12.2016.
 */

        import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.net.HttpURLConnection;
import java.util.List;

/**
 * Created by kostya on 04.05.2016.
 */
public class RssLoader extends AsyncTask<Void, Void, String> {
    private List<RssItem> rssResults;
    private final String dataSource;
    private HttpURLConnection urlConnection = null;
    //Listener to be called after all data is loaded
    private OnLoadFinishListener onLoadFinishListener = new OnLoadFinishListener() {
        @Override
        public void onFinish(List<RssItem> rssResults) {
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
    protected String doInBackground(Void... params) {
        String resultJson = "";
            try {
                RssReader rssReader = new RssReader(dataSource);
                rssResults = rssReader.getItems();


            } catch (Exception e) {
                e.printStackTrace();
            }

            return resultJson;

    }

    @Override
    protected void onPostExecute(String strJson) {
        super.onPostExecute(strJson);
        //call for Owerided method in ancestor
        onLoadFinishListener.onFinish(rssResults);

    }

    public OnLoadFinishListener getOnLoadFinishListener() {
        return onLoadFinishListener;
    }

    public interface OnLoadFinishListener {
        void onFinish(List<RssItem> rssResults);
    }

}
