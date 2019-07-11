package com.example.runningtrial.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.runningtrial.R;
import com.example.runningtrial.base.FragmentBasic;

public class ShowHtmlFragment extends FragmentBasic {
    public String TAG = getClass().getSimpleName();
    private static final String assetHtmlNameKey = "assetHtmlName";
    private String assetHtmlName;
    WebView wvShowHtml;

    public ShowHtmlFragment() {
        // Required empty public constructor
    }

    public static ShowHtmlFragment newInstance(String assetHtmlName) {
        ShowHtmlFragment fragment = new ShowHtmlFragment();
        Bundle args = new Bundle();
        args.putString(assetHtmlNameKey, assetHtmlName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            assetHtmlName = getArguments().getString(assetHtmlNameKey);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_show_html, container, false);

        wvShowHtml = rootView.findViewById(R.id.wvShowHtml);
        WebSettings webSetting = wvShowHtml.getSettings();
        webSetting.setBuiltInZoomControls(true);
        webSetting.setJavaScriptEnabled(true);
        wvShowHtml.setWebViewClient(new WebViewClient());

        wvShowHtml.loadUrl("file:///android_asset/" + assetHtmlName);


        return rootView;
    }
}
