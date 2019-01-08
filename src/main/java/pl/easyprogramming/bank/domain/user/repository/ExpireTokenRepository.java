package pl.easyprogramming.bank.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.easyprogramming.bank.domain.user.repository.entity.ExpireToken;

import java.util.UUID;

@Repository
public interface ExpireTokenRepository extends JpaRepository<ExpireToken, UUID> {
}
