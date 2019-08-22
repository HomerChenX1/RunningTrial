package com.example.runningtrial.pkgs;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;

import java.io.IOException;

public class DefaultBrowserNew implements AuthorizationCodeInstalledApp.Browser {
    String TAG = getClass().getSimpleName();
    Context context;

    public DefaultBrowserNew(Context context) {
        this.context = context;
    }

    @Override
    public void browse(String url) throws IOException {
        // create a WebView to do this
        // Intent intent = new Intent(context, WebViewActivity.class);
        // intent.putExtra("url", url);
        // context.startActivity(intent);

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.android.chrome");
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            // Chrome browser presumably not installed so allow user to choose instead
            // intent.setPackage(null);
            // context.startActivity(intent);
            Log.d(TAG, "Chrome browser presumably not installed so allow user to choose instead");
        }
    }
}
