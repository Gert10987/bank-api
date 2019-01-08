package pl.easyprogramming.bank.domain.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.easyprogramming.bank.domain.account.repository.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
