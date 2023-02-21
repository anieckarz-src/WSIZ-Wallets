package pl.nieckarz.cryptocurrencyapi.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.nieckarz.cryptocurrencyapi.dtos.TransactionDetailsDto;
import pl.nieckarz.cryptocurrencyapi.dtos.TransactionDto;
import pl.nieckarz.cryptocurrencyapi.services.utils.TokenUtil;
import pl.nieckarz.cryptocurrencyapi.dtos.WalletDto;
import pl.nieckarz.cryptocurrencyapi.models.CreateTransactionRequest;
import pl.nieckarz.cryptocurrencyapi.services.IWalletService;


import java.util.List;


@RestController
@RequestMapping("api/wallet")
public class WalletController {

    private final IWalletService walletService;

    @Autowired
    public WalletController(IWalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping
    public ResponseEntity<?> createWallet(HttpServletRequest request) {
        walletService.createWallet(TokenUtil.getUserIdFromToken(request));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<WalletDto>> getWallets(HttpServletRequest request){
        var wallets = walletService.getAllPrincipalWallets(TokenUtil.getUserIdFromToken(request));
        return new ResponseEntity<>(wallets, HttpStatus.OK);
    }

    @GetMapping("/{walletId}")
    public ResponseEntity<WalletDto> getWallet(@PathVariable(name = "walletId") Long walletId){
        var wallet = walletService.getWallet(walletId);
        return new ResponseEntity<>(wallet, HttpStatus.OK);
    }

    @DeleteMapping("/{walletId}")
    public ResponseEntity<?> deleteWallet(@PathVariable(name = "walletId") Long walletId){
        walletService.deleteWallet(walletId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{walletId}")
    public ResponseEntity<?> addTransaction(@PathVariable(name = "walletId") Long walletId, @RequestBody CreateTransactionRequest request){
        walletService.addTransaction(request.getBoughtPrice(), request.getSymbol(), walletId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("{transactionId}")
    public ResponseEntity<TransactionDetailsDto> getTransaction(@PathVariable(name = "transactionId") Long transactionId) throws Exception {
        var transaction = walletService.getTransaction(transactionId);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @DeleteMapping("/{walletId}/{transactionId}")
    public ResponseEntity<?> removeTransaction(@PathVariable(name = "walletId") Long walletId, @PathVariable(name = "transactionId") Long transactionId){
        walletService.removeTransaction(walletId, transactionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
