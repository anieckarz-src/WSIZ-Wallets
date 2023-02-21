package pl.nieckarz.cryptocurrencyapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TransactionDetailsDto {

    private Long id;
    private String symbol;
    private Double boughtPrice;
    private Double Pnl;
}
