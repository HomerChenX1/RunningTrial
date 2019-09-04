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

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.Credentials;
import com.google.photos.library.v1.PhotosLibraryClient;
import com.google.photos.library.v1.PhotosLibrarySettings;

import java.io.File;
import java.io.IOException;

/** A factory class that helps initialize a {@link PhotosLibraryClient} instance. */
public class PhotosLibraryClientFactory {
//  private static final java.io.File DATA_STORE_DIR =
//      new java.io.File(PhotosLibraryClientFactory.class.getResource("/").getPath(), "credentials");
  private static File DATA_STORE_DIR = null;
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  private static final int LOCAL_RECEIVER_PORT = 61984;
  static Credentials credentials;

  private PhotosLibraryClientFactory() { }

  /** Creates a new {@link PhotosLibraryClient} instance with credentials and scopes. */
  public static PhotosLibraryClient createClient() throws IOException{
    PhotosLibrarySettings settings =
        PhotosLibrarySettings.newBuilder()
            .setCredentialsProvider(
                FixedCredentialsProvider.create(
                    // getUserCredentials(credentialsPath, selectedScopes)))
                        credentials))
            .build();
    return PhotosLibraryClient.initialize(settings);
  }

}
