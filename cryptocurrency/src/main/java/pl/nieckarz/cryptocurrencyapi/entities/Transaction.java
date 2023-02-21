package pl.nieckarz.cryptocurrencyapi.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;
    private Double boughtPrice;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "user_id")
    private Wallet wallet;

    public Transaction(String symbol, Double boughtPrice, Wallet wallet) {
        this.symbol = symbol;
        this.boughtPrice = boughtPrice;
        this.wallet = wallet;
    }
}
