package pl.nieckarz.cryptocurrencyapi.exceptions;

public class TransactionNotFoundException extends RuntimeException{

    public TransactionNotFoundException(Long id) {
        super("transaction with id:" + id + " not found");
    }
}
