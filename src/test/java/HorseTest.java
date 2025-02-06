import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class HorseTest {

    @Test
    void testConstructorHorseWithFirstParameterIsNull() {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(null, 25.0, 5.0)
        );

        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   ", "\t", "\n", "\r\n"})
    void testConstructorHorseWithEmptyOrWhitespaceName(String invalidName) {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(invalidName, 21.0, 2.0)
        );
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    void testConstructorHorseWithNegativeSpeed() {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse("Hidalgo", -25.0, 3.0)
        );

        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    void testConstructorHorseWithNegativeDistance() {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse("Hidalgo", 23.0, -5.0)
        );

        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    void getName() {
        Horse horse = new Horse("Hidalgo", 15.0, 4.0);
        assertEquals("Hidalgo", horse.getName());
    }

    @Test
    void getSpeed() {
        Horse horse = new Horse("Hidalgo", 15.0, 4.0);
        assertEquals(15.0, horse.getSpeed());
    }

    @Test
    void getDistance() {
        Horse horse = new Horse("Hidalgo", 15.0, 4.0);
        Horse horseSpirit = new Horse("Spirit", 21.0);

        assertEquals(4.0, horse.getDistance());
        assertEquals(0.0, horseSpirit.getDistance());
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.5, 0.7, 0.9})
    void move(double randomValue) {
        Horse horse = new Horse("Hidalgo", 15.0, 4.0);
        double distanceBeforeMove = horse.getDistance();

        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomValue);

            double expectedDistance = distanceBeforeMove + (horse.getSpeed() * randomValue);

            assertEquals(randomValue, Horse.getRandomDouble(0.2, 0.9));
            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
            assertTrue(expectedDistance > distanceBeforeMove);
        }
    }

}