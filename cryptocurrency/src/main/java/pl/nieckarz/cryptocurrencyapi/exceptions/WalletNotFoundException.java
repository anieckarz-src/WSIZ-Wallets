package pl.nieckarz.cryptocurrencyapi.exceptions;

public class WalletNotFoundException extends RuntimeException{

    public WalletNotFoundException(Long id) {
        super("wallet with id:" + id + " not found");
    }
}
