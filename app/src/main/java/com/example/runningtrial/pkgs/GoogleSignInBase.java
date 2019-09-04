package com.example.runningtrial.pkgs;


import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runningtrial.base.Utils;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.auth.Credentials;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.UserCredentials;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *  Purpose : use as Google SignIn service helper
 *  This is an asynchronous routines. Do not run in UI thread
 *  need an Activity/Fragment to implement
 *      SignInButton signInButton;
 *      onCreate()
 *      onStart()
 *      onActivityResult()
 *      signIn()
 *      signOut()
 *      revokeAccess()
 *
 *      @see <a href="https://developers.google.com/identity/sign-in/android/sign-in">Integrating Google Sign-In into Your Android App</a>
 * @see <a ref="https://developers.google.com/identity/sign-in/android/offline-access">Enabling Server-Side Access</a>
 */
public class GoogleSignInBase {
    String TAG = getClass().getSimpleName();
    private static final int RC_SIGN_IN = 9001;
    private static final int RC_RECOVERABLE = 9002;
    private static final int  RC_GET_AUTH_CODE = 9003;
    private GoogleClientSecrets clientSecrets = null;
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private final ArrayList<String> scopes = new ArrayList<>();

    public GoogleSignInClient mGoogleSignInClient;
    private static GoogleSignInAccount account;
    private static Account mAccount;
    private String clientId = null;
    private String clientSecret = null;

    // bormally working in UI
//    public void setSignInButton(SignInButton signInButton) {
//        this.signInButton = signInButton;
//        this.signInButton = findViewById(R.id.sign_in_button);
//        this.signInButton.setSize(SignInButton.SIZE_STANDARD);
//        this.signInButton.setOnClickListener(this);
//    }

    public GoogleClientSecrets getClientSecrets() {
        return clientSecrets;
    }

    public void setClientSecrets(Context context) {
        setClientSecrets(context, "credentials_signin.json");
    }

    public void setClientSecrets(Context context, String assetFileName) {
        // this.clientSecrets = clientSecrets;
        try {
            clientSecrets =
                    GoogleClientSecrets.load(
                            // JSON_FACTORY, new InputStreamReader(new FileInputStream(credentialsPath)));
                            JSON_FACTORY, new InputStreamReader(
                                    context.getAssets().open(assetFileName)));
            clientId = clientSecrets.getDetails().getClientId();
            clientSecret = clientSecrets.getDetails().getClientSecret();
        } catch (IOException err) {
            clientSecrets = null;
            clientId = null;
            clientSecret = null;
        }
    }

    public ArrayList<String> getScopes() {
        return scopes;
    }

    public GoogleSignInBase() { }

    public void onCreate(Context context){
        // use getScopes() to setup scopes

        setClientSecrets(context);
        GoogleSignInOptions.Builder builder = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().requestServerAuthCode(clientId,false);  // need check why false
        for (String s : scopes) {
            builder.requestScopes(new Scope(s));
        }
        GoogleSignInOptions gso = builder.build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
    }

    public boolean checkUserSigned(Context context){
        boolean isSignIn = false;
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        account = GoogleSignIn.getLastSignedInAccount(context);
        if (account != null) {
            // sign in
            Log.d(TAG, "had Sign-in");
            isSignIn = true;
        }
        return isSignIn;
    }

    public void signIn(AppCompatActivity activity) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void signOut(AppCompatActivity activity) {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(activity, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        Log.d(TAG, "signOut");
                        // token = null;
                        // [END_EXCLUDE]
                    }
                });
    }

    public void revokeAccess(AppCompatActivity activity) {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(activity, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        Log.d(TAG, "revokeAccess");
                        // [END_EXCLUDE]
                    }
                });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data, Utils.CallBackBase listenser) {
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task, listenser);
        }
    }

    private Scope [] convertScopes() {
        if (scopes.size() == 0)
            return null;
        Scope [] Scopes = new Scope[scopes.size()];
        for (int i=0;i<scopes.size(); i++){
            Scopes[i] = new Scope(scopes.get(i));
        }
        return Scopes;
    }

    private void checkScopes() {
        Scope [] Scopes = convertScopes();
        if (GoogleSignIn.hasPermissions(account, Scopes)) {
            Log.d(TAG, "There are correct permissions");
        } else {
            Log.d(TAG, "There are wrong permissions");
            // GoogleSignIn.requestPermissions(activity, requestCode, account, Scopes);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask, Utils.CallBackBase listenser) {
        try {
            account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            Log.d(TAG, "success login");
            // signInButton.setVisibility(View.INVISIBLE);
            checkScopes();
            mAccount = account.getAccount();
            Log.d(TAG, "serverCode is: " + account.getServerAuthCode());
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            // Clear the local account, normally is user reject or no assigned API permissions
            mAccount = null;
        }
        if (listenser!=null)
            listenser.callBack(0, mAccount);
    }

    public Credentials getUserCredentials(Context context) {
        GoogleAccountCredential credential =
                GoogleAccountCredential.usingOAuth2(
                        context, scopes );
        credential.setSelectedAccount(TestPkgActivity.mAccount);

        String token = null;
        try {
            token = credential.getToken();
        } catch (GoogleAuthException e) {
            // e.printStackTrace();
            Log.d("getUserCredentials", "credential.getToken() fail : GoogleAuthException");
        } catch (IOException e) {
            // e.printStackTrace();
            Log.d("getUserCredentials", "credential.getToken() fail: IOException");
        }

        AccessToken a = new AccessToken(token, null);
        return UserCredentials.newBuilder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setAccessToken(a)
                .build();
    }

//    void doNext(){
//
//        try {
//            client = PhotosLibraryClientFactory.createClient(this, scopes);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (GeneralSecurityException e) {
//            e.printStackTrace();
//        }
//        if (client == null) {
//            Log.d(TAG, "something wrong in PhotosLibraryClientFactory");
//            return;
//        }
//        Log.d(TAG, "success in PhotosLibraryClientFactory");
//        InternalPhotosLibraryClient.ListAlbumsPagedResponse albums = client.listAlbums();
//        Log.d(TAG, "listAlbums");
//    }
}
