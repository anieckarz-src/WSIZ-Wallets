package pl.nieckarz.cryptocurrencyapi.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.nieckarz.cryptocurrencyapi.exceptions.TransactionNotFoundException;
import pl.nieckarz.cryptocurrencyapi.exceptions.WalletNotFoundException;

@ControllerAdvice
public class TransactionNotFoundHandler {
    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<Object> handleTransactionNotFoundException(TransactionNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
