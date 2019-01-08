package pl.easyprogramming.bank.domain.user.repository.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class ExpireToken {

    @Id
    private UUID id;

    public ExpireToken() {
    }

    public ExpireToken(UUID id) {
        this.id = id;
    }
}
