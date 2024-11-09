package com.hsf301.project.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class OAuthService {
    private static final String CLIENT_SECRET_PATH = "credentials.json";
    private static final String REDIRECT_URI = "http://localhost:8888/oauth2/callback"; // URI chuyển hướng

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    public static String getAuthorizationUrl() throws GeneralSecurityException, IOException {
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(new FileInputStream(CLIENT_SECRET_PATH)));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, clientSecrets,
                Collections.singleton("https://www.googleapis.com/auth/calendar"))
                .setAccessType("offline")
                .build();

        // Tạo URL authorization
        return flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();
    }

    public static void main(String[] args) throws IOException, GeneralSecurityException {
        String meetUrl = getAuthorizationUrl();
        System.out.println("Authorize URL: " + meetUrl);
    }
}
