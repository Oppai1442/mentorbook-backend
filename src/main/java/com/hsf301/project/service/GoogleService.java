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
import com.hsf301.project.model.request.bookingGenerateMeetUrlRequest;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.springframework.stereotype.Service;

@Service
public class GoogleService {
        private final String CLIENT_SECRET_PATH = "credentials2.json";
        private final String APPLICATION_NAME = "Google Calendar API Java";
        private final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

        public String getAuthorizationUrl(bookingGenerateMeetUrlRequest data)
                        throws GeneralSecurityException, IOException {
                GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                                new InputStreamReader(new FileInputStream(CLIENT_SECRET_PATH)));

                GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                                GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, clientSecrets,
                                Collections.singleton(CalendarScopes.CALENDAR))
                                .setAccessType("offline")
                                .build();

                String redirectToGetMeetRoom = "http://localhost:8080/api/bookings/get-meet-room";

                // Chỉ mã hóa redirectUri một lần
                String encodedRedirectUri = URLEncoder.encode(data.getRedirectUri(), StandardCharsets.UTF_8.toString());
                String bookingId = data.getBookingId().toString(); // Lấy bookingId

                // Tạo state chứa cả redirectUri và bookingId
                String state = "redirectUri=" + encodedRedirectUri + "&bookingId=" + bookingId;

                return flow.newAuthorizationUrl()
                                .setRedirectUri(redirectToGetMeetRoom)
                                .set("state", URLEncoder.encode(state, StandardCharsets.UTF_8.toString())) // Mã hóa
                                                                                                           // toàn bộ
                                                                                                           // state
                                .build();
        }

        public Credential exchangeCodeForToken(String code) throws IOException, GeneralSecurityException {
                GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                                new InputStreamReader(new FileInputStream(CLIENT_SECRET_PATH)));

                GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                                GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, clientSecrets,
                                Collections.singleton(CalendarScopes.CALENDAR))
                                .setAccessType("offline")
                                .build();

                // Sử dụng URI chuyển hướng đã cấu hình trong console
                String redirectToGetMeetRoom = "http://localhost:8080/api/bookings/get-meet-room";

                // Gọi yêu cầu mã thông báo với mã xác thực và URI chuyển hướng
                GoogleTokenResponse tokenResponse = flow.newTokenRequest(code)
                                .setRedirectUri(redirectToGetMeetRoom) // Đảm bảo rằng URI này đã được cấu hình trong
                                                                       // Google Cloud
                                .execute();

                // Tạo và lưu trữ credential để sử dụng sau này
                return flow.createAndStoreCredential(tokenResponse, "user");
        }

        public String createGoogleMeetEvent(Credential credential) throws IOException, GeneralSecurityException {
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
                                .setAttendees(Collections
                                                .singletonList(new EventAttendee().setEmail("attendee@example.com")))
                                .setConferenceData(new ConferenceData().setCreateRequest(
                                                new CreateConferenceRequest().setRequestId("sample123")
                                                                .setConferenceSolutionKey(
                                                                                new ConferenceSolutionKey().setType(
                                                                                                "hangoutsMeet"))));

                Event createdEvent = service.events().insert("primary", event).setConferenceDataVersion(1).execute();

                return createdEvent.getHangoutLink();
        }
}
