package com.myCompany.app;

public class FlightService {
    private FlightRepo flightRepo;

    public FlightService(FlightRepo flightRepo) {
        this.flightRepo = flightRepo;
    }

    public int getAvailableSeats(String flightID) {
        Flight flight = flightRepo.getFlightById(flightID);
        if (flight != null) {
            return flight.getAvailableSeats();
        }
        return 0;
    }

    public void decrementAvailableSeats(String flightID) {
        Flight flight = flightRepo.getFlightById(flightID);
        if (flight != null) {
            flight.occupySeat(); // decrement available seats by 1

        }
    }

    public void incrementAvailableSeats(String flightID) {
        Flight flight = flightRepo.getFlightById(flightID);
        if (flight != null) {
            flight.freeSeat(); // increment available seats by 1

        }
    }

    public Flight[] getFlights() {
        return flightRepo.getFlights(); // aetti ad na i allar current bookings i databaseinu
    }
}