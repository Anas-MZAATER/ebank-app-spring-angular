package net.anas.ebankbackend.exceptions;

public class CustomerAlreadyExistException extends Throwable {
    public CustomerAlreadyExistException(String message) {
        super(message);
    }
}
