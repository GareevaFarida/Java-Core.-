package j3_lesson2.Exceptions;

public class RegistrationException extends Exception {
    public RegistrationException() {
    }

    public RegistrationException(String message) {
        super(message);
    }
}
