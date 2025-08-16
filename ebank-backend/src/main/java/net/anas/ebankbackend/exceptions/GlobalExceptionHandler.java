package net.anas.ebankbackend.exceptions;


//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(CustomerAlreadyExistException.class)
//    public ResponseEntity<String> handleCustomerAlreadyExistException(CustomerAlreadyExistException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
//    }
//}
//==================================================================================
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@ControllerAdvice
//
//public class GlobalExceptionHandler {
//
//    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
//
//    @ExceptionHandler(CustomerAlreadyExistException.class)
//    public ResponseEntity<String> handleCustomerAlreadyExistException(CustomerAlreadyExistException ex) {
//        logger.error("CustomerAlreadyExistException occurred: {}", ex.getMessage());
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
//    }
//}






import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // Gestion des exceptions métier spécifiques
    @ExceptionHandler({
            BalanceNotSufficientException.class,
            BankAccountNotFoundException.class,
            CustomerNotFoundException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<String> handleBusinessExceptions(Exception ex) {
        log.warn("Business exception occurred: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());

    }

    @ExceptionHandler(CustomerAlreadyExistException.class)
    public ResponseEntity<String> handleCustomerAlreadyExistException(CustomerAlreadyExistException ex) {
        log.error("CustomerAlreadyExistException occurred: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

    // Gestion des exceptions techniques
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        log.error("Unexpected error occurred", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred. Please contact support.");
    }


    // (Optionnel) Réponse enrichie avec des métadonnées
//    private Record ErrorResponse(String message, HttpStatus status, LocalDateTime timestamp) {
//        return new Record(message, status.value(), timestamp);
//    }
}