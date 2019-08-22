package com.example.runningtrial.pkgs;


import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runningtrial.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.photos.library.v1.PhotosLibraryClient;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class TestPkgActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    String TAG = getClass().getSimpleName();
    GoogleSignInClient mGoogleSignInClient;
    public static GoogleSignInAccount account;
    public static Account mAccount;
    SignInButton signInButton;
    static String server_client_id = "675637306805-ugd8ogb2gqno8jvmtgklsmubu6ih98or.apps.googleusercontent.com";
    static String client_secret = "Lub9bpouY0L0qCzW-CeNiDJv";
    String serverCode;
    GoogleApiClient mGoogleApiClient;
    String token = null;

    private static final String KEY_ACCOUNT = "key_account"; // Bundle key for account object
    private static final int RC_SIGN_IN = 9001;
    private static final int RC_RECOVERABLE = 9002;
    private static final int  RC_GET_AUTH_CODE = 9003;
    public final static String SCOPE_READONLY = "https://www.googleapis.com/auth/photoslibrary.readonly";
    public final static String SCOPE_SHARING = "https://www.googleapis.com/auth/photoslibrary.sharing";
    public final static String SCOPE_APPENDONLY = "https://www.googleapis.com/auth/photoslibrary.appendonly";
    public final static ArrayList<String> scopes = new ArrayList<>();

    public static HandlerThread handlerThread = null;
    Handler mHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_pkg);

        handlerThread = new HandlerThread(TAG);
        handlerThread.start();
        mHandler = new Handler(handlerThread.getLooper());
        scopes.add(SCOPE_READONLY);
        scopes.add(SCOPE_SHARING);
        scopes.add(SCOPE_APPENDONLY);


        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(this);
		findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.disconnect_button).setOnClickListener(this);

        // Restore instance state
        if (savedInstanceState != null) {
            mAccount = savedInstanceState.getParcelable(KEY_ACCOUNT);
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestServerAuthCode(server_client_id,false)  // need check why false
                .requestScopes(new Scope(SCOPE_READONLY), new Scope(SCOPE_APPENDONLY), new Scope(SCOPE_SHARING))
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // [START on_start_sign_in]
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        account = GoogleSignIn.getLastSignedInAccount(this);
        if (account!=null) {
            Log.d(TAG, "had Sign-in");
            signInButton.setVisibility(View.INVISIBLE);
            // need to check permissions
            checkPhotoApiPermission(account);
            signOut();
        } else {
            // display the Google Sign-in button
            Log.d(TAG, "Sign-in first time");
            signInButton.setVisibility(View.VISIBLE);
        }
        // [END on_start_sign_in]
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed happens but not processed");
    }

    private void checkPhotoApiPermission(GoogleSignInAccount account) {
        if (GoogleSignIn.hasPermissions(account, new Scope(SCOPE_READONLY),
                new Scope(SCOPE_APPENDONLY), new Scope(SCOPE_SHARING))) {
            Log.d(TAG, "There are correct permissions");
        } else {
            Log.d(TAG, "There are wrong permissions");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_ACCOUNT, mAccount);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                // signIn();
                signIn_old();
                break;
			case R.id.sign_out_button:
                signOut();
                break;
            case R.id.disconnect_button:
                revokeAccess();
                break;
        }
    }

    private void signIn_old() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_GET_AUTH_CODE);
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        Log.d(TAG, "signOut");
                        token = null;
                        // [END_EXCLUDE]
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult_old(task);
        }

        // Handling a user-recoverable auth exception
        if (requestCode == RC_RECOVERABLE) {
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "success login");
                signInButton.setVisibility(View.INVISIBLE);
            } else {
                Toast.makeText(this, "RC_RECOVERABLE exception", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == RC_GET_AUTH_CODE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult_old(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            Log.d(TAG, "success login");
            signInButton.setVisibility(View.INVISIBLE);
            checkPhotoApiPermission(account);
            mAccount = account.getAccount();
            serverCode = account.getServerAuthCode();
            Log.d(TAG, "serverCode is: " + serverCode);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    doNext();
                }
            });
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            // Clear the local account
            mAccount = null;
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);
            serverCode = account.getServerAuthCode();
            Log.d(TAG, "serverCode is: " + serverCode);
            doNext();
        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            // Clear the local account
            mAccount = null;
        }
    }
	
	private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        Log.d(TAG, "revokeAccess");
                        // [END_EXCLUDE]
                    }
                });
    }

    PhotosLibraryClient client = null;
    void doNext(){

        try {
            client = PhotosLibraryClientFactory.createClient(this, scopes);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        if (client == null) {
            Log.d(TAG, "something wrong in PhotosLibraryClientFactory");
            return;
        }
        Log.d(TAG, "success in PhotosLibraryClientFactory");
    }
}

/*
class PhotosLibraryClientFactory {
    public PhotosLibraryClientFactory() { }

    public static PhotosLibraryClient createClient(String token, Context context) {
        PhotosLibrarySettings settings = null;
        try {
            settings = PhotosLibrarySettings.newBuilder()
                    .setCredentialsProvider(
                            FixedCredentialsProvider.create(getUserCredentials(token, context)))
                    // .setChannelProvider(channelProvider)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        PhotosLibraryClient photosLibraryClient2 = null;
        try (PhotosLibraryClient photosLibraryClient =
                     PhotosLibraryClient.initialize(settings)) {

            photosLibraryClient2 = photosLibraryClient;
            // Create a new Album  with at title
            Album album = photosLibraryClient.createAlbum("My Album");

            // Get some properties from the album, such as its ID and product URL
            String id = album.getId();
            String url = album.getProductUrl();

            Log.d("PhotosLibraryClient", "Album id: " + id + " url: " + url);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return photosLibraryClient2;
    }

    private static Credentials getUserCredentials2(String token, Context context) {
//        GoogleClientSecrets clientSecrets =
//                GoogleClientSecrets.load(
//                        JSON_FACTORY, new InputStreamReader(new FileInputStream(credentialsPath)));
//        String clientId = clientSecrets.getDetails().getClientId();
//        String clientSecret = clientSecrets.getDetails().getClientSecret();
        String clientId = TestPkgActivity.server_client_id;
        String clientSecret = TestPkgActivity.client_secret;
        GoogleAccountCredential credential =
                GoogleAccountCredential.usingOAuth2(
                        context,
                        Collections.singleton(
                                TestPkgActivity.SCOPE_READONLY)
                );
        credential.setSelectedAccount(TestPkgActivity.mAccount);

        try {
            return UserCredentials.newBuilder()
                    .setClientId(clientId)
                    .setClientSecret(clientSecret)
                    // .setRefreshToken(credential.getRefreshToken())
                    .setRefreshToken(credential.getToken())
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (GoogleAuthException e) {
            e.printStackTrace();
            return null;
        }
    }


}
*/
