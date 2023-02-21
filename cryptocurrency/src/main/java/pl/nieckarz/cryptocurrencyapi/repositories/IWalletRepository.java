package pl.nieckarz.cryptocurrencyapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.nieckarz.cryptocurrencyapi.entities.Wallet;

import java.util.List;
import java.util.Optional;


@Repository
public interface IWalletRepository extends JpaRepository<Wallet, Long> {
    List<Wallet> findByOwnerId(Long ownerId);
    Optional<Wallet> findById(Long id);
}
