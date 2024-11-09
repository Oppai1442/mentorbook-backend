package com.hsf301.project.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.auth.oauth2.Credential;
import com.hsf301.project.model.request.BookingRequest;
import com.hsf301.project.model.request.GetBookingRequest;
import com.hsf301.project.model.request.MentorBookUpdateStatusRequest;
import com.hsf301.project.model.request.bookingGenerateMeetUrlRequest;
import com.hsf301.project.model.response.ApiResponse;
import com.hsf301.project.model.response.BookingResponseContainer;
import com.hsf301.project.model.response.MentorBookingResponse;
import com.hsf301.project.model.response.Response;
import com.hsf301.project.service.GoogleService;
import com.hsf301.project.service.MentorBookingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/bookings")
public class MentorBookingController {

    @Autowired
    private MentorBookingService mentorBookingService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Response>> createBooking(@RequestBody BookingRequest bookingRequest) {
        mentorBookingService.createBooking(bookingRequest);
        return ResponseEntity.ok(new ApiResponse<Response>(new Response("Booking created successfully")));
    }

    @GetMapping("/getBookings/get-mentor-bookings-by-month")
    public ResponseEntity<ApiResponse<List<MentorBookingResponse>>> getMentorBookingsByMonth(
            @RequestParam("mentorId") Long mentorId, @RequestParam("month") int month, @RequestParam("year") int year) {
        List<MentorBookingResponse> bookingList = mentorBookingService.getBookingsByMentorAndMonth(mentorId, month,
                year);
        return ResponseEntity.ok(new ApiResponse<>(bookingList));
    }

    @GetMapping("/getBookings/get-mentor-bookings-by-week")
    public ResponseEntity<ApiResponse<List<MentorBookingResponse>>> getMentorBookingsByWeek(
            @RequestParam("mentorId") Long mentorId, @RequestParam("startOfWeek") String startOfWeek,
            @RequestParam("endOfWeek") String endOfWeek) {
        LocalDateTime start = LocalDateTime.parse(startOfWeek);
        LocalDateTime end = LocalDateTime.parse(endOfWeek);
        List<MentorBookingResponse> bookingList = mentorBookingService.getBookingsByMentorAndWeek(mentorId, start, end);
        return ResponseEntity.ok(new ApiResponse<>(bookingList));
    }

    @GetMapping("/getBookings/get-mentor-bookings-by-date")
    public ResponseEntity<ApiResponse<List<MentorBookingResponse>>> getMentorBookingsInDay(
            @RequestParam("mentorId") Long mentorId, @RequestParam("date") String date) {
        List<MentorBookingResponse> bookingList = mentorBookingService.getBookingsByMentorAndDays(mentorId, date);

        return ResponseEntity.ok(new ApiResponse<List<MentorBookingResponse>>(bookingList));
    }

    @PostMapping("/getBookings")
    public ResponseEntity<ApiResponse<BookingResponseContainer>> getUserBookings(
            @RequestBody GetBookingRequest request) {
        BookingResponseContainer bookingResponseContainer = mentorBookingService.getBookingsData(request);

        return ResponseEntity.ok(new ApiResponse<BookingResponseContainer>(bookingResponseContainer));
    }

    /////////////////// TESTING///////////////////////////////
    /////////////////// TESTING///////////////////////////////
    /////////////////// TESTING///////////////////////////////
    /////////////////// TESTING///////////////////////////////
    /////////////////// TESTING///////////////////////////////
    /////////////////// TESTING///////////////////////////////
    /////////////////// TESTING///////////////////////////////
    @Autowired
    GoogleService googleService;

    @PostMapping("/create-meet-room")
    public ResponseEntity<ApiResponse<String>> createMeetRoom(@RequestBody bookingGenerateMeetUrlRequest data) {
        System.out.println("Received booking ID: " + data.getBookingId());
        try {
            String authorizationUrl = googleService.getAuthorizationUrl(data);
            System.out.println("Generated authorization URL: " + authorizationUrl);

            // Gắn bookingId vào URL để truyền đến get-meet-room
            String fullUrlWithBookingId = authorizationUrl + "&bookingId=" + data.getBookingId();
            System.out.println("Full URL with booking ID: " + fullUrlWithBookingId);

            return ResponseEntity.ok(new ApiResponse<>(fullUrlWithBookingId));
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error generating authorization URL"));
        }
    }

    @GetMapping("/get-meet-room")
    public ResponseEntity<String> getMeetRoom(@RequestParam("code") String code, @RequestParam("state") String state) {
        try {
            System.out.println("Received code: " + code);
            System.out.println("Received state: " + state);

            // Giải mã state để lấy redirectUri và bookingId
            String decodedState = URLDecoder.decode(state, StandardCharsets.UTF_8);
            System.out.println("Decoded state: " + decodedState);

            String[] params = decodedState.split("&");
            String redirectUri = null;
            Long bookingId = null;

            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    if ("redirectUri".equals(keyValue[0])) {
                        redirectUri = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8); // Giải mã lại redirectUri
                        System.out.println("Extracted redirectUri: " + redirectUri);
                    } else if ("bookingId".equals(keyValue[0])) {
                        bookingId = Long.valueOf(keyValue[1]);
                        System.out.println("Extracted bookingId: " + bookingId);
                    }
                }
            }

            if (bookingId == null || redirectUri == null) {
                System.out.println("Missing required parameters in the state.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Missing required parameters in the state.");
            }

            // Xác thực mã code và lấy Credential
            Credential credential = googleService.exchangeCodeForToken(code);
            System.out.println("Credential acquired successfully.");

            // Tạo sự kiện Google Meet và nhận link
            String meetUrl = googleService.createGoogleMeetEvent(credential);
            System.out.println("Generated Google Meet URL: " + meetUrl);

            // Lưu meetUrl vào booking trong MentorBooking
            mentorBookingService.setMeetUrl(bookingId, meetUrl);

            // Giải mã redirectUri để tránh các ký tự không hợp lệ
            URI decodedRedirectUri = URI.create(URLDecoder.decode(redirectUri, StandardCharsets.UTF_8));

            // Chuyển hướng với URI đã được giải mã
            try {
                URI redirectWithParams = new URI(decodedRedirectUri.toString() + "?meetUrl="
                        + URLEncoder.encode(meetUrl, StandardCharsets.UTF_8) + "&bookingId=" + bookingId);
                System.out.println("Redirecting to: " + redirectWithParams);

                return ResponseEntity.status(HttpStatus.FOUND)
                        .location(redirectWithParams)
                        .build();
            } catch (URISyntaxException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error creating redirect URI");
            }

        } catch (IOException | GeneralSecurityException | IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating Google Meet event");
        }
    }

    @PostMapping("/update-status")
    public ResponseEntity<String> updateBookingStatus(@RequestBody MentorBookUpdateStatusRequest data) {
        try {
            mentorBookingService.updateBookingStatus(data);
            return ResponseEntity.ok("Booking status updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the booking status.");
        }
    }

}
