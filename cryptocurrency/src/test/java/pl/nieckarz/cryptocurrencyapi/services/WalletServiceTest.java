package pl.nieckarz.cryptocurrencyapi.services;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.nieckarz.cryptocurrencyapi.entities.Wallet;
import pl.nieckarz.cryptocurrencyapi.repositories.ITransactionRepository;
import pl.nieckarz.cryptocurrencyapi.repositories.IWalletRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class WalletServiceTest {

    @Autowired
    private WalletService underTest;

    @Autowired
    private IWalletRepository walletRepository;

    @Autowired
    private ITransactionRepository transactionRepository;


    @Test
    @Transactional
    void getWallet() {
        //given
        var wallet = new Wallet(1L);
        walletRepository.save(wallet);

        //when
        var result = underTest.getWallet(wallet.getId());

        //then
        assertThat(result.getOwnerId()).isEqualTo(wallet.getOwnerId());

    }

    @Test
    void createWallet() {
        //given
        var ownerId = 1L;

        //when
        underTest.createWallet(ownerId);
        var wallets = underTest.getAllPrincipalWallets(ownerId);

        //then
        assertThat(wallets.stream().count()).isEqualTo(1);
    }

    @Test
    void deleteWallet() {
        //given
        var ownerId = 1L;
        underTest.createWallet(ownerId);

        //when
        underTest.deleteWallet(1L);
        var wallets = underTest.getAllPrincipalWallets(ownerId);

        //then
        assertThat(wallets.stream().count()).isEqualTo(0);
    }

    @Test
    void addTransaction() throws Exception {
        //given
        var ownerId = 1L;
        underTest.createWallet(ownerId);

        //when
        underTest.addTransaction(12345,"BTC", 1L);
        var transaction = underTest.getTransaction(1L);

        //then
        assertThat(transaction).isNotNull();
    }

    @Test
    void getTransaction() throws Exception {
        //given
        var ownerId = 1L;
        underTest.createWallet(ownerId);

        //when
        underTest.addTransaction(12345,"BTC", 1L);
        var transaction = underTest.getTransaction(1L);

        assertThat(transaction).isNotNull();
    }
}