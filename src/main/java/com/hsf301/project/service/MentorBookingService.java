package com.hsf301.project.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsf301.project.exception.BookingConflictException;
import com.hsf301.project.model.mentorBooking.MentorBooking;
import com.hsf301.project.model.mentorData.MentorData;
import com.hsf301.project.model.request.BookingRequest;
import com.hsf301.project.model.request.GetBookingRequest;
import com.hsf301.project.model.request.MentorBookUpdateStatusRequest;
import com.hsf301.project.model.response.BookingResponse;
import com.hsf301.project.model.response.BookingResponseContainer;
import com.hsf301.project.model.response.MentorBookingResponse;
import com.hsf301.project.model.user.User;
import com.hsf301.project.repository.MentorBookingRepository;
import com.hsf301.project.repository.MentorDataRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MentorBookingService {
    @Autowired
    private MentorBookingRepository mentorBookingRepository;

    @Autowired
    private UserService userService;

    public List<MentorBooking> getAllBookingsByStatus(User mentor, String status) {
        return mentorBookingRepository.findByCustomerAndStatus(mentor, status);
    }

    @Autowired
    private MentorDataRepository mentorDataRepository;

    public void createBooking(BookingRequest bookingRequest) {
        Long durationInMinus = (long) (bookingRequest.getDuration() * 60);
        LocalDateTime endTime = bookingRequest.getStartTime().plusMinutes(durationInMinus);
        LocalDateTime startTime = bookingRequest.getStartTime();

        List<MentorBooking> bookings = mentorBookingRepository
                .findByMentor(userService.findByUserId(bookingRequest.getMentorId()));

        boolean conflict = bookings.stream().anyMatch(booking -> {
            long bookingDurationInMinutes = (long) (booking.getDuration() * 60);
            LocalDateTime bookingEndTime = booking.getStartTime().plusMinutes(bookingDurationInMinutes);
            LocalDateTime bookingStartTime = booking.getStartTime();

            return (startTime.isBefore(bookingEndTime) && endTime.isAfter(bookingStartTime)) ||
                    (startTime.equals(bookingStartTime) || endTime.equals(bookingEndTime));
        });

        if (conflict) {
            throw new BookingConflictException("There is already a booking at the requested time.");
        }

        User customer = userService.findByUserId(bookingRequest.getUserId());
        User mentor = userService.findByUserId(bookingRequest.getMentorId());
        MentorData mentorData = mentorDataRepository.findByMentor(mentor);

        MentorBooking newBooking = new MentorBooking();
        newBooking.setCustomer(customer);
        newBooking.setMentor(mentor);
        newBooking.setStartTime(bookingRequest.getStartTime());
        newBooking.setDuration(bookingRequest.getDuration());
        newBooking.setDescription(bookingRequest.getDescription());
        newBooking.setCost(BigDecimal.valueOf(bookingRequest.getDuration() * mentorData.getMentorPrice()));

        mentorBookingRepository.save(newBooking);
    }

    public List<MentorBookingResponse> getBookingsByMentorAndMonth(Long mentorId, int month, int year) {
        List<MentorBooking> bookings = mentorBookingRepository.findBookingsByMentorAndMonth(mentorId, month, year);
        List<MentorBookingResponse> mentorBookingResponses = new ArrayList<>();

        for (MentorBooking booking : bookings) {
            mentorBookingResponses.add(
                    new MentorBookingResponse(
                            booking.getBookingId(),
                            booking.getCustomer(),
                            booking.getMentor(),
                            booking.getStartTime(),
                            booking.getDuration()));
        }

        return mentorBookingResponses;
    }

    public List<MentorBookingResponse> getBookingsByMentorAndWeek(Long mentorId, LocalDateTime startOfWeek,
            LocalDateTime endOfWeek) {
        List<MentorBooking> bookings = mentorBookingRepository.findBookingsByMentorAndWeek(mentorId, startOfWeek,
                endOfWeek);
        List<MentorBookingResponse> mentorBookingResponses = new ArrayList<>();

        for (MentorBooking booking : bookings) {
            mentorBookingResponses.add(
                    new MentorBookingResponse(
                            booking.getBookingId(),
                            booking.getCustomer(),
                            booking.getMentor(),
                            booking.getStartTime(),
                            booking.getDuration()));
        }

        return mentorBookingResponses;
    }

    public List<MentorBookingResponse> getBookingsByMentorAndDays(Long mentorId, String date) {
        LocalDate dateTime = LocalDate.parse(date);
        List<MentorBooking> bookings = mentorBookingRepository.findBookingsByMentorAndDate(mentorId, dateTime);
        List<MentorBookingResponse> mentorBookingResponses = new ArrayList<>();

        for (MentorBooking booking : bookings) {
            mentorBookingResponses.add(
                    new MentorBookingResponse(
                            booking.getBookingId(),
                            booking.getCustomer(),
                            booking.getMentor(),
                            booking.getStartTime(),
                            booking.getDuration()));
        }

        return mentorBookingResponses;
    }

    public BookingResponseContainer getBookingsData(GetBookingRequest request) {
        User user = userService.findByUserId(request.getUserId());
        List<MentorBooking> bookings;
        List<BookingResponse> mentorBookingResponses = new ArrayList<>();

        // Lấy danh sách booking dựa trên vai trò của người dùng
        if (user.getRole().equalsIgnoreCase("mentor")) {
            bookings = mentorBookingRepository.findByMentor(user);
        } else {
            bookings = mentorBookingRepository.findByCustomer(user);
        }

        System.out.println(request.getSearchValue());
        if (request.getSearchValue() != null && !request.getSearchValue().isEmpty()) {
            String searchValueLower = request.getSearchValue().toLowerCase().trim().replaceAll("\\s+", "");
        
            bookings = bookings.stream()
                    .filter(booking -> {
                        String customerName = booking.getCustomer().getFullName().toLowerCase().replaceAll("\\s+", "");
                        String mentorName = booking.getMentor().getFullName().toLowerCase().replaceAll("\\s+", "");
                        String bookingIdStr = String.valueOf(booking.getBookingId());
                        
                        System.out.println("Customer Name: " + customerName + ", Mentor Name: " + mentorName + ", Search Value: " + searchValueLower);
                        
                        return customerName.contains(searchValueLower) ||
                               mentorName.contains(searchValueLower) ||
                               bookingIdStr.contains(searchValueLower);
                    })
                    .collect(Collectors.toList());
        }
        
        
    
        ZoneId zone = ZoneId.of("GMT+7");
        LocalDateTime currentDateTime = LocalDateTime.now(zone);

        List<MentorBooking> futureBookings = new ArrayList<>();
        List<MentorBooking> pastBookings = new ArrayList<>();

        for (MentorBooking booking : bookings) {
            booking.getBookingId();
            if (booking.getStartTime().isAfter(currentDateTime) || booking.getStartTime().isEqual(currentDateTime)) {
                futureBookings.add(booking);
            } else {
                pastBookings.add(booking);
            }
        }

        futureBookings.sort(Comparator.comparing(MentorBooking::getStartTime));

        pastBookings.sort(Comparator.comparing(MentorBooking::getStartTime).reversed());

        List<MentorBooking> sortedBookings = new ArrayList<>();
        sortedBookings.addAll(futureBookings);
        sortedBookings.addAll(pastBookings);

        int totalFound = sortedBookings.size();
        int pageSize = request.getResultCount();
        int fromIndex = Math.min(request.getPage() * pageSize, totalFound);
        int toIndex = Math.min(fromIndex + pageSize, totalFound);

        List<MentorBooking> paginatedBookings = sortedBookings.subList(fromIndex, toIndex);

        for (MentorBooking booking : paginatedBookings) {
            mentorBookingResponses.add(
                    new BookingResponse(
                            booking.getBookingId(),
                            booking.getCustomer(),
                            booking.getMentor(),
                            booking.getBookingDate(),
                            booking.getStartTime(),
                            booking.getDuration(),
                            booking.getStatus(),
                            booking.getMeetUrl(),
                            booking.getDescription(),
                            booking.getCost()));
        }

        Double totalPages = Math.floor((double) totalFound / pageSize);
        BookingResponseContainer bookingResponseContainer = new BookingResponseContainer(
                totalPages,
                mentorBookingResponses);

        return bookingResponseContainer;
    }

    @Autowired
    GoogleService googleService;

    public void setMeetUrl(Long bookingId, String meetUrl) {
        MentorBooking booking = mentorBookingRepository.findByBookingId(bookingId);
        if (booking != null) {
            booking.setMeetUrl(meetUrl);
            mentorBookingRepository.save(booking);
        } else {
            throw new EntityNotFoundException("Booking not found for ID: " + bookingId);
        }

    }

    public void updateBookingStatus(MentorBookUpdateStatusRequest data) {
        MentorBooking booking = mentorBookingRepository.findByBookingId(data.getBookingId());
        if (booking == null) {
            throw new EntityNotFoundException("Booking not found for ID: " + data.getBookingId());
        }

        if (!isValidStatus(data.getStatus())) {
            throw new IllegalArgumentException("Invalid status. Accepted statuses are: pending, accepted, rejected, finish.");
        }

        booking.setStatus(data.getStatus().toLowerCase());
        mentorBookingRepository.save(booking);
    }

    private boolean isValidStatus(String status) {
        return status.equalsIgnoreCase("pending") ||
               status.equalsIgnoreCase("accepted") ||
               status.equalsIgnoreCase("rejected") ||
               status.equalsIgnoreCase("finish");
    }
}
