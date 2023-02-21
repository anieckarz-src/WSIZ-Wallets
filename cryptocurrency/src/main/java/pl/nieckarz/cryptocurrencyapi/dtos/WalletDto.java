package pl.nieckarz.cryptocurrencyapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.nieckarz.cryptocurrencyapi.entities.Transaction;


import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class WalletDto {

    private Long id;
    private Long ownerId;
    private List<TransactionDto> transactions;
}
