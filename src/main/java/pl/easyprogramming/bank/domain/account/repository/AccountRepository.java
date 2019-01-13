package pl.easyprogramming.bank.domain.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.easyprogramming.bank.domain.account.repository.entity.AccountEntity;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    Optional<AccountEntity> findByAccountNumber(String accountNumber);

    @Query("SELECT a.accountNumber FROM AccountEntity a WHERE a.id = :id")
    String findAccountNumberById(@Param("id") Long id);
}
