import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HippodromeTest {
    @Test
    void testConstructorHippodromeWithParameterIsNull() {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(null)
        );

        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    void testConstructorHippodromeWithEmptyCollections() {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(Collections.emptyList())
        );
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    void getHorses() {
        List<Horse> originalHorses = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            originalHorses.add(new Horse("Horse" + i, i * 2.0, i * 5.0));
        }

        Hippodrome hippodrome = new Hippodrome(originalHorses);
        List<Horse> hippodromeHorses = hippodrome.getHorses();

        assertEquals(originalHorses.size(), hippodromeHorses.size());

        for (int i = 0; i < hippodromeHorses.size(); i++) {
            assertSame(originalHorses.get(i), hippodromeHorses.get(i));
        }
    }

    @Test
    void move() {
        List<Horse> originalHorses = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            originalHorses.add(Mockito.spy(new Horse("Horse" + i, i * 2.0, i * 5.0)));
        }

        Hippodrome hippodrome = new Hippodrome(originalHorses);
        hippodrome.move();

        for (Horse horse : hippodrome.getHorses()) {
            Mockito.verify(horse).move();
        }
    }

    @Test
    void getWinner() {
        List<Horse> originalHorses = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            originalHorses.add(Mockito.spy(new Horse("Horse" + i, i * 2.0, i * 5.0)));
        }

        Hippodrome hippodrome = new Hippodrome(originalHorses);
        double distanceMax = 0.0;

        for (Horse horse : hippodrome.getHorses()) {
            double distance = horse.getDistance();
            if (distance > distanceMax) {
                distanceMax = distance;
            }
        }

        assertEquals(distanceMax, hippodrome.getWinner().getDistance());
    }
}