package com.myCompany.app;

public interface BookingRepo {
    void confirmBooking(Booking booking);
    void deleteBooking(String bookingID);
    void updateBooking(Booking booking);
    boolean containsBooking(String bookingID);
    Booking[] getBookings(); // aetti ad na i allar current bookings i databaseinu

    // availableSeat logic til ad hondla frjals saeti
    int getAvailableSeats();
    void decrementAvailableSeats(); // adferd til ad draga 1 saeti fra available seats
    void incrementAvailableSeats(); // adferd til ad auka 1 saeti fyrir available seats
    void setAvailableSeats(int seats); // test setup adferd
}
