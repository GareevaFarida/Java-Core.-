package j3_lesson2.Exceptions;

public class NonuniqueUserRegistrationException extends Exception {
    public NonuniqueUserRegistrationException(String username) {
        super("Не удалось зарегистрировать пользователя с именем " + username +", т.к. пользователь с таким именем уже зарегистрирован.");
    }

    public NonuniqueUserRegistrationException() {
    }

}
