package latice.util;

import java.util.Scanner;

public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
    
    
    public static int readIntWithException(Scanner scanner, String prompt, int min, int max) throws InvalidInputException {
        System.out.print(prompt);
        if (!scanner.hasNextInt()) {
            scanner.nextLine(); // consomme l'entrée invalide
            throw new InvalidInputException("Entrée non valide, ce n’est pas un nombre.");
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // consomme le reste de la ligne
        if (value < min || value > max) {
            throw new InvalidInputException("Entrée hors limites (" + min + " - " + max + ").");
        }
        return value;
    }
}



