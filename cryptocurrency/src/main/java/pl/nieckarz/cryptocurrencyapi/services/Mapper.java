package pl.nieckarz.cryptocurrencyapi.services;

import org.springframework.stereotype.Service;
import pl.nieckarz.cryptocurrencyapi.dtos.TransactionDetailsDto;
import pl.nieckarz.cryptocurrencyapi.dtos.TransactionDto;
import pl.nieckarz.cryptocurrencyapi.dtos.WalletDto;
import pl.nieckarz.cryptocurrencyapi.entities.Transaction;
import pl.nieckarz.cryptocurrencyapi.entities.Wallet;

import java.util.stream.Collectors;

@Service
public class Mapper {

    public static WalletDto asDto(Wallet wallet){
        return new WalletDto(wallet.getId(), wallet.getOwnerId(),
                wallet.getTransactions().stream()
                        .map(x-> asDto(x)).collect(Collectors.toList()));
    }

    public static TransactionDto asDto(Transaction transaction){
        return new TransactionDto(transaction.getId(), transaction.getSymbol(), transaction.getBoughtPrice());
    }

    public static TransactionDetailsDto asDetailsDto(Transaction transaction, Double price){
        return new TransactionDetailsDto(transaction.getId(), transaction.getSymbol(), transaction.getBoughtPrice(), price/transaction.getBoughtPrice());
    }
}
