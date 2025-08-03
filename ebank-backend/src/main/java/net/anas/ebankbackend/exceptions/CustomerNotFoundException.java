package net.anas.ebankbackend.exceptions;

//créer une exception surveiller
public class CustomerNotFoundException extends Exception {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
