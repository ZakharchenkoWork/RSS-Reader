package com.zakharchenko.postindustria;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by Ui-Developer on 25.12.2016.
 */

public class BrowserFragment extends Fragment {
    private View rootView;
    public static final String URL_TAG = "url";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.browser, container, false);
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        if(savedInstanceState != null) {
            savedInstanceState.getString(URL_TAG);
        }
        WebView webView = (WebView) rootView.findViewById(R.id.webView);
        webView.loadUrl("http://google.com");
        return rootView;
    }
}
