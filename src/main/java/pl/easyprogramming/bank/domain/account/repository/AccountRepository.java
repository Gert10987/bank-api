package pl.easyprogramming.bank.domain.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.easyprogramming.bank.domain.account.repository.entity.Account;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);

    @Query("SELECT a.accountNumber FROM Account a WHERE a.id = :id")
    String findAccountNumberById(@Param("id") Long id);
}
