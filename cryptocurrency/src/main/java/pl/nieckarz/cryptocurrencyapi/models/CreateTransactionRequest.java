package pl.nieckarz.cryptocurrencyapi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateTransactionRequest {
    private String symbol;
    private double boughtPrice;
}
