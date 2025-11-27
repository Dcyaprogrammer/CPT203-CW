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

//    Todo:Initialize a fixed LocalDateTime value (e.g., a baseline "now")
    @BeforeEach
    void setUp() {
        now = LocalDateTime.of(2025, 12, 12, 0, 0);
        spot = new ParkingSpot(1, ParkingSpot.Status.AVAILABLE);
    }

//    Todo:Reset objects used in the test
    @AfterEach
    void tearDown() {
        res = null;
        spot = null;
        now = null;
    }

//    Todo: Test case 1 with proper name
//          When startTime >= endTime, the method should throw IllegalArgumentException
    @DisplayName("Invalid time check")
    @Test
    void InvalidTime(){
        System.out.println("Start time = End time");
        res = new Reservation(1, 1, spot, now, now);
        assertThrows(IllegalArgumentException.class, () -> {
            res.processReservation();
        });
        System.out.println("Test Success");

        System.out.println("Start time > End time");
        res = new Reservation(1, 1, spot, now.plusHours(2), now);
        assertThrows(IllegalArgumentException.class, () -> {
            res.processReservation();
        });
        System.out.println("Test Success");
    }

//    Todo: Test case 2 with proper name
//          When the parking spot is not AVAILABLE, the method should throw IllegalStateException
    @DisplayName("Unavailable spot check")
    @Test
    void UnavailableSpot(){
        System.out.println("Unavailable Spot Check");
        spot.setStatus(ParkingSpot.Status.RESERVED);
        res = new Reservation(1, 1, spot, now, now.plusHours(2));

        assertThrows(IllegalStateException.class, () -> {
            res.processReservation();
        });
        System.out.println("Test Success");
    }

//    Todo: Test case 3 with proper name and finish within 100 millisecond
//          The method returns true and the reservation becomes confirmed
    @DisplayName("Timeout check")
    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    void ReturnsTrueAndConfirmsReservation(){
        System.out.println("Timeout Check");
        res = new Reservation(1, 1, spot, now, now.plusHours(2));

        boolean result = res.processReservation();

        assertTrue(result);
        assertTrue(res.isConfirmed());
        assertEquals(ParkingSpot.Status.RESERVED, spot.getStatus());

        System.out.println("Test Success");
    }

}