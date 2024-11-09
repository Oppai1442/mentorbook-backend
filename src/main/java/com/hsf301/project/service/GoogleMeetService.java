package com.hsf301.project.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.*;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class GoogleMeetService {

    private static final String APPLICATION_NAME = "Google Calendar API Java";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String CREDENTIALS_FILE_PATH = "credentials.json";
    private static final String REDIRECT_URI = "http://localhost:8888/oauth2/callback";

    public static Credential exchangeCodeForToken(String code) throws IOException, GeneralSecurityException {
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(new FileInputStream(CREDENTIALS_FILE_PATH)));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, clientSecrets,
                Collections.singleton(CalendarScopes.CALENDAR))
                .setAccessType("offline")
                .build();

        GoogleTokenResponse tokenResponse = flow.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();
        return flow.createAndStoreCredential(tokenResponse, "user");
    }

    public static String createGoogleMeetEvent(Credential credential) throws IOException, GeneralSecurityException {
        Calendar service = new Calendar.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();

        Event event = new Event()
                .setSummary("Meeting with team")
                .setDescription("Discussing project updates")
                .setStart(new EventDateTime().setDateTime(new DateTime("2024-11-08T10:00:00-07:00"))
                        .setTimeZone("America/Los_Angeles"))
                .setEnd(new EventDateTime().setDateTime(new DateTime("2024-11-08T11:00:00-07:00"))
                        .setTimeZone("America/Los_Angeles"))
                .setAttendees(Collections.singletonList(new EventAttendee().setEmail("attendee@example.com")))
                .setConferenceData(new ConferenceData().setCreateRequest(
                        new CreateConferenceRequest().setRequestId("sample123").setConferenceSolutionKey(
                                new ConferenceSolutionKey().setType("hangoutsMeet"))));

        Event createdEvent = service.events().insert("primary", event).setConferenceDataVersion(1).execute();

        return createdEvent.getHangoutLink();
    }

    public static void main(String[] args) throws IOException, GeneralSecurityException {
        // Sử dụng mã `code` từ URL callback để trao đổi lấy `access token`.
        String code = "4/0AVG7fiT0Z9jUlSEhazn30cXzrC8bCk3bijQFPyC3P_AZ1_lOrAc_ViOZiW5aLXXZe4s34A";
        Credential credential = exchangeCodeForToken(code);

        // Tạo phòng họp Google Meet.
        String meetUrl = createGoogleMeetEvent(credential);
        System.out.println("Generated Google Meet URL: " + meetUrl);
    }
}
