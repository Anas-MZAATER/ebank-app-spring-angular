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

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerAlreadyExistException.class)
    public ResponseEntity<String> handleCustomerAlreadyExistException(CustomerAlreadyExistException ex) {
        log.error("CustomerAlreadyExistException occurred: {}", ex.getMessage(), ex); // Ensure ex is included
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}