package pl.nieckarz.cryptocurrencyapi.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.nieckarz.cryptocurrencyapi.entities.Transaction;

@Repository
public interface ITransactionRepository extends JpaRepository<Transaction, Long> {
}
