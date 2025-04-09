Smá upplýsingar um hvað hvert file er:

Booking (Domain Model):
– This class holds the data for a single booking (flightID, userID, and a generated bookingID).

BookingRepo (Interface):
– Defines the operations (add and delete) for a storage component without tying you to a specific implementation.

MockBookingRepo (Mock Object):
– Implements BookingRepo as a fake storage component that simply stores data in an in-memory map.
– This allows you to simulate persistence without a real database.

BookingService (Controller):
– This class contains the business logic to create and delete bookings.
– It depends on a BookingRepo to store and remove bookings.
– By using dependency injection (passing in a BookingRepo via the constructor), you cUsaan later substitute the mock (for testing) or a real repository (for production).

BookingServiceTest (JUnit Test Fixture):
– Uses JUnit to set up tests for BookingService.
– In the @BeforeEach method, it creates a MockBookingRepo and injects it into a new BookingService instance.
– Test cases (such as creating or deleting a booking) call BookingService methods and then verify that the expected changes are reflected in the mock repository.





USAGE:
--------
mvn test
--------





