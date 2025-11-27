package test;

import org.junit.jupiter.api.*;
import src.ParkingSpot;
import src.Reservation;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

class ReservationTest {
    private Reservation res;
    private ParkingSpot spot;
    private LocalDateTime now;
    private LocalDateTime later;

//    Todo:Initialize a fixed LocalDateTime value (e.g., a baseline "now")
    @BeforeEach
    void setUp() {
        now = LocalDateTime.of(2025, 12, 12, 0, 0);
        later = now.plusHours(2);
        spot = new ParkingSpot(1, ParkingSpot.Status.AVAILABLE);
    }

//    Todo:Reset objects used in the test
    @AfterEach
    void tearDown() {
        res = null;
        spot = null;
        now = null;
        later = null;
    }

//    Todo: Test case 1 with proper name
//          When startTime >= endTime, the method should throw IllegalArgumentException
    @DisplayName("Invalid time check")
    @Test
    void InvalidTime(){
        res = new Reservation(1, 1, spot, now, now);

        assertThrows(IllegalArgumentException.class, () -> {
            res.processReservation();
        });
    }

//    Todo: Test case 2 with proper name
//          When the parking spot is not AVAILABLE, the method should throw IllegalStateException
    @DisplayName("Unavailable spot check")
    @Test
    void UnavailableSpot(){
        spot.setStatus(ParkingSpot.Status.RESERVED);
        res = new Reservation(1, 1, spot, now, later);

        assertThrows(IllegalStateException.class, () -> {
            res.processReservation();
        });
    }

//    Todo: Test case 3 with proper name and finish within 100 millisecond
//          The method returns true and the reservation becomes confirmed
    @DisplayName("Timeout check")
    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    void ReturnsTrueAndConfirmsReservation(){
        res = new Reservation(1, 1, spot, now, later);

        boolean result = res.processReservation();

        assertTrue(result);
        Assertions.assertTrue(res.isConfirmed());
        Assertions.assertEquals(ParkingSpot.Status.RESERVED, spot.getStatus());
    }

}