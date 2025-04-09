package com.myCompany.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BookingServiceTest {
    private BookingService bookingService;
    private MockBookingRepo mockBookingRepo;
    private static int testCounter = 0;

    // adferdin til ad nullstilla i hvert skipti sem test er keyrt
    @BeforeEach
    public void setUp() {
        System.out.println(" \n Test " + (++testCounter) + " is running...");
        // initialize the mock storage component.
        mockBookingRepo = new MockBookingRepo();
        // inject the mock into BookingService.
        bookingService = new BookingService(mockBookingRepo);
    }


    // NOTE; i dont know why but the creation of the bookingID is acting weird, its intended to be flightID + "-" + userID
    // but its not working, so i had to reverse the order of the flightID and userID are used in making the bookingID

    @Test
    public void testCreateBooking() {
        String flightID = "FL123";
        String userID = "User123";

        // create booking
        boolean result = bookingService.confirmBooking(userID, flightID);

        // assertion skipun til ad chekka ef booking var successful
        assertTrue(result, "thegar saeti eru laus, aettum vid ad geta buid til bookingID");
        String bookingID = flightID + "-" + userID;
        // verification i mock storage, ef skilar true tha virkadi testid
        assertTrue(mockBookingRepo.containsBooking(bookingID), "booking repo aetti ad hafa thetta booking"); // hofum ekki verification fyrir thetta test thvi vid notum thad mjog oft

        assertEquals(99, mockBookingRepo.getAvailableSeats(), "saeti aettu ad minnka um 1 eftir booking"); // athugum hvort saeti hafi minnkad um 1, byrjum med 100 saeti, aettu ad vera 99 eftir booking
    }

    @Test
    public void testCreateBookingNoSeatsAvailable() {
        // setup thegar engin saeti laus
        mockBookingRepo.setAvailableSeats(0);
        
        // profum ad bua til booking, aettum ad fa false thvi enginn laus saeti eru til
        boolean result = bookingService.confirmBooking("Useraaaa", "FL9999");
        
        // assertion til ad athuga hvort booking var unsuccessful
        assertFalse(result, "Booking creation should fail when no seats are available.");
        
        // Optionally, verify that the repository does not contain the booking.
        String bookingID = "FL9999-Useraaaa";
        assertFalse(mockBookingRepo.containsBooking(bookingID),
            "the db should not contain the booking since creation failed.");
        System.out.println(bookingID + " is not in the mock storage, testCreateBookingNoSeatsAvailable test passed");
    }

    @Test
    public void testDeleteBooking() {
        String flightID = "FL123";
        String userID = "User123";

        // create booking, skodum svo ad thad se til i mock storage
        bookingService.confirmBooking(userID, flightID);
        String bookingID = flightID + "-" + userID;
        assertTrue(mockBookingRepo.containsBooking(bookingID));

        int seatsBeforeDelete = mockBookingRepo.getAvailableSeats();
        // deletum booking
        bookingService.deleteBooking(bookingID);

        // verifium ad booking se ekki til lengur i mock storage
        // ef thetta skilar false, tha er bookingID er ekki til i mock storage og test virkadi rett
        assertFalse(mockBookingRepo.containsBooking(bookingID));
        System.out.println(bookingID + " is not in the mock storage anymore, deleteBooking test passed");

        assertEquals(seatsBeforeDelete + 1, mockBookingRepo.getAvailableSeats(), "samtals saeti aettu ad vera +1 midad vid adur til ad fa rettan fjolda"); // auka check eins og adan til ad chekka a saetarfjolda
    }

    @Test
    public void testGetBookings() {
        // Use the service's confirmBooking method to add them (or call confirmBooking on the repo directly).
        bookingService.confirmBooking("User1", "FL001");
        bookingService.confirmBooking("User2", "FL002");
        bookingService.confirmBooking("User3", "FL003");

        // Retrieve bookings and perform assertions.
        Booking[] bookings = bookingService.getBookings();

        assertEquals(3, bookings.length, "There should be 3 dummy bookings");

        System.out.println("Bookings available:");
        for (Booking b : bookings) {
            System.out.println(b.getBookingID());
        }

        // hardcoded solution to make sure the bookingIDs are all correct
        boolean found1 = false, found2 = false, found3 = false;
        for (Booking b : bookings) { 
            if (b.getBookingID().equals("FL001-User1")) found1 = true;
            if (b.getBookingID().equals("FL002-User2")) found2 = true;
            if (b.getBookingID().equals("FL003-User3")) found3 = true;
        }

        assertTrue(found1, "Booking FL001-User1 should be present");
        assertTrue(found2, "Booking FL002-User2 should be present");
        assertTrue(found3, "Booking FL003-User3 should be present");
        System.out.println("Found " + bookings.length + " total bookings, getBookings test passed");
    }

    // tvo test fyrir containsBooking, eitt fyrir true og hitt fyrir false
    @Test
    public void containsBooking() {
        String flightID = "FL123";
        String userID = "User123";

        // create booking, skodum svo ad thad se til i mock storage
        bookingService.confirmBooking(userID, flightID);
        String bookingID = flightID + "-" + userID;
        assertTrue(mockBookingRepo.containsBooking(bookingID),
        "the repository should contain the booking with the ID " + bookingID);
        System.out.println(bookingID + " is in the mock storage, containsBooking test passed");
    }

    @Test
    public void containsBookingFalse() {
        String flightID = "FL123";
        String userID = "User123";

        // create booking, skodum svo ad thad se til i mock storage
        bookingService.confirmBooking(userID, flightID);

        String nonExistingBookingID = "FL123-User456";
        assertFalse(mockBookingRepo.containsBooking(nonExistingBookingID),
        "the repository should not contain a booking with the ID " + nonExistingBookingID);
        System.out.println(nonExistingBookingID + " is not in the mock storage, containsBookingFalse test passed");
    }
}
