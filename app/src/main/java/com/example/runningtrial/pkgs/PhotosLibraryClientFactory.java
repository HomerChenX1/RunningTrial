/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.runningtrial.pkgs;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.Credentials;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.UserCredentials;
import com.google.photos.library.v1.PhotosLibraryClient;
import com.google.photos.library.v1.PhotosLibrarySettings;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.List;

/** A factory class that helps initialize a {@link PhotosLibraryClient} instance. */
public class PhotosLibraryClientFactory {
//  private static final java.io.File DATA_STORE_DIR =
//      new java.io.File(PhotosLibraryClientFactory.class.getResource("/").getPath(), "credentials");
  private static File DATA_STORE_DIR = null;
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  private static final int LOCAL_RECEIVER_PORT = 61984;

  private PhotosLibraryClientFactory() { }

  /** Creates a new {@link PhotosLibraryClient} instance with credentials and scopes. */
  public static PhotosLibraryClient createClient(
      // String credentialsPath, List<String> selectedScopes)
      Context context, List<String> selectedScopes)

      throws IOException, GeneralSecurityException {
    PhotosLibrarySettings settings =
        PhotosLibrarySettings.newBuilder()
            .setCredentialsProvider(
                FixedCredentialsProvider.create(
                    // getUserCredentials(credentialsPath, selectedScopes)))
                    getUserCredentials(context, selectedScopes)))
            .build();
    return PhotosLibraryClient.initialize(settings);
  }

  private static Credentials getUserCredentials2(Context context, List<String> selectedScopes)
  //private static Credentials getUserCredentials(String credentialsPath, List<String> selectedScopes)
      throws IOException, GeneralSecurityException {

    if (DATA_STORE_DIR == null) {
          String path = context.getFilesDir() +"/" + "credentials";
          DATA_STORE_DIR = new File(path);
        }

    GoogleClientSecrets clientSecrets =
        GoogleClientSecrets.load(
            // JSON_FACTORY, new InputStreamReader(new FileInputStream(credentialsPath)));
            JSON_FACTORY, new InputStreamReader(
                    context.getAssets().open("webTry1.json")));
    String clientId = clientSecrets.getDetails().getClientId();
    String clientSecret = clientSecrets.getDetails().getClientSecret();

    GoogleAuthorizationCodeFlow flow =
        new GoogleAuthorizationCodeFlow.Builder(
                new com.google.api.client.http.javanet.NetHttpTransport(),
                JSON_FACTORY,
                clientSecrets,
                selectedScopes)
            .setDataStoreFactory(new FileDataStoreFactory(DATA_STORE_DIR))
            .setAccessType("offline")
            .build();
    LocalServerReceiver receiver =
        new LocalServerReceiver.Builder().setPort(LOCAL_RECEIVER_PORT).build();
    Credential credential = new AuthorizationCodeInstalledApp(flow, receiver, new DefaultBrowserNew(context))
            .authorize("user");
    // GoogleAccountCredential credential = signInCredential(context, selectedScopes);

          // try {
              return UserCredentials.newBuilder()
                  .setClientId(clientId)
                  .setClientSecret(clientSecret)
                  .setRefreshToken(credential.getRefreshToken())
                  // .setRefreshToken(credential.getToken())
                  .build();
          // } catch (GoogleAuthException e) {
          //    e.printStackTrace();
          //    return null;
          // }
      }

  private static GoogleAccountCredential signInCredential(Context context, List<String> selectedScopes){
      GoogleAccountCredential credential =
              GoogleAccountCredential.usingOAuth2(
                      context, selectedScopes );
      credential.setSelectedAccount(TestPkgActivity.mAccount);
      // credential.getToken();
      return credential;

//      UserCredentials.fromStream(refreshTokenStream, new HttpTransportFactory() {
//          @Override
//          public HttpTransport create() {
//              return new com.google.api.client.http.javanet.NetHttpTransport();
//          }
//      });
  }

    private static Credentials getUserCredentials(Context context, List<String> selectedScopes)
            throws IOException, GeneralSecurityException {
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(
                        // JSON_FACTORY, new InputStreamReader(new FileInputStream(credentialsPath)));
                        JSON_FACTORY, new InputStreamReader(
                                context.getAssets().open("credentials_signin.json")));
        String clientId = clientSecrets.getDetails().getClientId();
        String clientSecret = clientSecrets.getDetails().getClientSecret();

        GoogleAccountCredential credential =
                GoogleAccountCredential.usingOAuth2(
                        context, selectedScopes );
        credential.setSelectedAccount(TestPkgActivity.mAccount);

        String token = null;
        try {
            token = credential.getToken();
        } catch (GoogleAuthException e) {
            e.printStackTrace();
            Log.d("getUserCredentials", "credential.getToken() fail");
        }

        AccessToken a = new AccessToken(token, null);
        return UserCredentials.newBuilder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setAccessToken(a)
                .build();

//        return UserCredentials.fromStream(context.getAssets().open("webTry1.json"), new HttpTransportFactory() {
//            @Override
//            public HttpTransport create() {
//                return new com.google.api.client.http.javanet.NetHttpTransport();
//            }
//        });

//        val a = AccessToken(token, null)
//        return UserCredentials.newBuilder()
//                .setClientId("your client id")
//                .setClientSecret("your client secret")
//                .setAccessToken(a)
//                .build()
    }
}
