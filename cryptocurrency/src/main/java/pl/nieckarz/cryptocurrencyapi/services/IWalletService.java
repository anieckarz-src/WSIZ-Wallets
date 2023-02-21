package pl.nieckarz.cryptocurrencyapi.services;

import pl.nieckarz.cryptocurrencyapi.dtos.TransactionDetailsDto;
import pl.nieckarz.cryptocurrencyapi.dtos.WalletDto;

import java.io.IOException;
import java.util.List;

public interface IWalletService {
    List<WalletDto> getAllPrincipalWallets(Long ownerId);
    WalletDto getWallet(Long id);
    void createWallet(Long ownerId);
    void deleteWallet(Long walletId);
    void addTransaction(double price, String symbol, Long walletId);
    void removeTransaction(Long walletId, Long transactionId);
    TransactionDetailsDto getTransaction(Long transactionId) throws Exception;
}
