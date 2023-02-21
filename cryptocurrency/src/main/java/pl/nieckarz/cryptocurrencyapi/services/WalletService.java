package pl.nieckarz.cryptocurrencyapi.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.nieckarz.cryptocurrencyapi.dtos.TransactionDetailsDto;
import pl.nieckarz.cryptocurrencyapi.dtos.WalletDto;
import pl.nieckarz.cryptocurrencyapi.entities.Transaction;
import pl.nieckarz.cryptocurrencyapi.entities.Wallet;
import pl.nieckarz.cryptocurrencyapi.exceptions.TransactionNotFoundException;
import pl.nieckarz.cryptocurrencyapi.exceptions.WalletNotFoundException;
import pl.nieckarz.cryptocurrencyapi.repositories.ITransactionRepository;
import pl.nieckarz.cryptocurrencyapi.repositories.IWalletRepository;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WalletService implements IWalletService {

    private final IWalletRepository walletRepository;
    private final ITransactionRepository transactionRepository;
    private final String providerUrl;

    @Autowired
    public WalletService(IWalletRepository walletRepository, ITransactionRepository transactionRepository) throws MalformedURLException {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
        this.providerUrl = "https://api.binance.com/api/v3/ticker/price?symbol=";
    }

    public List<WalletDto> getAllPrincipalWallets(Long ownerId){
        return walletRepository.findByOwnerId(ownerId).stream().map(x-> Mapper.asDto(x)).collect(Collectors.toList());
    }

    public WalletDto getWallet(Long id){

        var wallet = walletRepository.getById(id);

        if(wallet == null){
            throw new WalletNotFoundException(id);
        }

        return Mapper.asDto(wallet);
    }

    public void createWallet(Long ownerId){

        var wallet = new Wallet(ownerId);

        walletRepository.save(wallet);
    }

    public void deleteWallet(Long walletId){

        var wallet = walletRepository.getById(walletId);

        if(wallet == null){
            throw new WalletNotFoundException(walletId);
        }

        walletRepository.delete(wallet);
    }

    public void addTransaction(double price, String symbol, Long walletId){

        var wallet = walletRepository.getById(walletId);

        if(wallet == null){
            throw new WalletNotFoundException(walletId);
        }

        var transaction = new Transaction(symbol, price, wallet);

        transactionRepository.save(transaction);
    }

    public void removeTransaction(Long walletId, Long transactionId){

        var wallet = walletRepository.getById(walletId);

        if(wallet == null){
            throw new WalletNotFoundException(walletId);
        }

        var transaction = wallet.getTransactions().stream().filter(x-> x.getId() == transactionId).findFirst().orElse(null);

        if(transaction == null){
            throw new TransactionNotFoundException(transactionId);
        }

        transactionRepository.deleteById(transactionId);
    }
    
    public TransactionDetailsDto getTransaction(Long transactionId) throws Exception {

        var transaction = transactionRepository.findById(transactionId).get();

        if(transaction == null){
            throw new TransactionNotFoundException(transactionId);
        }

        var price = getPrice(transaction.getSymbol());
        
        return Mapper.asDetailsDto(transaction, price);

    }

    private Double getPrice(String sybmol) throws Exception {

        ObjectMapper mapper = new ObjectMapper();


        var requestUrl = new URL(providerUrl+sybmol+"USDT");

        String url = "https://api.binance.com/api/v3/ticker/price?symbol="+ sybmol +"USDT";
        var response = HttpRequestService.sendGetRequest(url);
        var jsonNode = mapper.readTree(String.valueOf(response));
        var priceNode = jsonNode.get("price");
        
        return priceNode.asDouble(); 
    }


}
