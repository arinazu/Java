
package my.lab1java;

public class InvalidNumberException extends Exception {
    public InvalidNumberException() {
        super("Invalid number");
    }
    // Конструктор с сообщением
    public InvalidNumberException(String message) {
        super(message);
    }
}
