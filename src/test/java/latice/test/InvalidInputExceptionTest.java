package latice.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

import latice.util.InvalidInputException;

public class InvalidInputExceptionTest {

    @Test
    void testReadValidNumber() throws InvalidInputException {
        Scanner scanner = new Scanner("3\n");
        assertEquals(3, InvalidInputException.readIntWithException(scanner, "", 1, 5));
    }

    @Test
    void testReadNotANumberThrows() {
        Scanner scanner = new Scanner("abc\n");
        assertThrows(InvalidInputException.class,
                () -> InvalidInputException.readIntWithException(scanner, "", 1, 5));
    }

    @Test
    void testReadOutOfRangeThrows() {
        Scanner scanner = new Scanner("9\n");
        assertThrows(InvalidInputException.class,
                () -> InvalidInputException.readIntWithException(scanner, "", 1, 5));
    }

    @Test
    void testInvalidLineIsConsumed() throws InvalidInputException {
        Scanner scanner = new Scanner("abc\n2\n");
        assertThrows(InvalidInputException.class,
                () -> InvalidInputException.readIntWithException(scanner, "", 1, 5));
        // la ligne invalide est deja consommee, on doit lire 2 directement
        assertEquals(2, InvalidInputException.readIntWithException(scanner, "", 1, 5));
    }
}
