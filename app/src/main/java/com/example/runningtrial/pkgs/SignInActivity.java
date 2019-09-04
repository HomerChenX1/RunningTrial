package com.example.runningtrial.pkgs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runningtrial.R;
import com.example.runningtrial.base.Utils;
import com.google.android.gms.common.SignInButton;
import com.google.photos.library.v1.PhotosLibraryClient;
import com.google.photos.library.v1.internal.InternalPhotosLibraryClient;
import com.google.photos.types.proto.Album;

import java.io.IOException;
import java.util.ArrayList;

public class SignInActivity extends AppCompatActivity {
    String TAG = getClass().getSimpleName();
    public final static String SCOPE_READONLY = "https://www.googleapis.com/auth/photoslibrary.readonly";
    public final static String SCOPE_SHARING = "https://www.googleapis.com/auth/photoslibrary.sharing";
    public final static String SCOPE_APPENDONLY = "https://www.googleapis.com/auth/photoslibrary.appendonly";

    SignInButton signInButton;
    GoogleSignInBase googleSignInBase = new GoogleSignInBase();
    int checkSignInClientReadyTimeout = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        TestPkgActivity.mHandler.post(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> strings = googleSignInBase.getScopes();
                strings.add(SCOPE_READONLY);
                strings.add(SCOPE_APPENDONLY);
                strings.add(SCOPE_SHARING);
                googleSignInBase.onCreate(SignInActivity.this);
            }
        });

        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(onClickListener);
        findViewById(R.id.sign_out_button).setOnClickListener(onClickListener);
        findViewById(R.id.disconnect_button).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.sign_in_button:
                    // signIn();
                    googleSignInBase.signIn(SignInActivity.this);
                    break;
                case R.id.sign_out_button:
                    googleSignInBase.signOut(SignInActivity.this);
                    break;
                case R.id.disconnect_button:
                    googleSignInBase.revokeAccess(SignInActivity.this);
                    break;
            }
        }
    };

    void checkSignInClientReady(){
        if (checkSignInClientReadyTimeout == 0) {
            Toast.makeText(SignInActivity.this,
                    "GoogleSignInClient fail. Timeout",
                    Toast.LENGTH_SHORT).show();
        }
        if (googleSignInBase.mGoogleSignInClient == null){
            Toast.makeText(SignInActivity.this,
                    "GoogleSignInClient not ready, please wait.",
                    Toast.LENGTH_SHORT).show();

            TestPkgActivity.mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkSignInClientReadyTimeout--;
                    checkSignInClientReady();
                }
            }, 1000);
        } else {
            if (googleSignInBase.checkUserSigned(SignInActivity.this)) {
                googleSignInBase.signOut(SignInActivity.this);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    signInButton.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkSignInClientReady();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        googleSignInBase.onActivityResult(requestCode, resultCode, data, new Utils.CallBackBase() {
            @Override
            public void callBack(int status, Object msg) {
                if (msg == null) {
                    Toast.makeText(SignInActivity.this,
                            "User reject or No Api permission.",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(SignInActivity.this,
                            "GoogleSignIn finish.",
                            Toast.LENGTH_SHORT).show();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            signInButton.setVisibility(View.INVISIBLE);
                        }
                    });
                    startPhotoLibraryApi();
                }
            }
        });
    }

    private void startPhotoLibraryApi() {
        try {
            PhotosLibraryClient photosLibraryClient = PhotosLibraryClientFactory.createClient();
            InternalPhotosLibraryClient.ListAlbumsPagedResponse albums = photosLibraryClient.listAlbums();
            for (Album album : albums.iterateAll()) {
                // Get some properties of an album
                String id = album.getId();
                String title = album.getTitle();
                String productUrl = album.getProductUrl();
                String coverPhotoBaseUrl = album.getCoverPhotoBaseUrl();
                // The cover photo media item id field may be empty
                String coverPhotoMediaItemId = album.getCoverPhotoMediaItemId();
                boolean isWritable = album.getIsWriteable();
                long mediaItemsCount = album.getMediaItemsCount();
                Log.d(TAG, "Album is : " + title);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
