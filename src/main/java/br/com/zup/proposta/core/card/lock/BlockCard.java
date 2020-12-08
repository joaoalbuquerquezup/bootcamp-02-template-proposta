package br.com.zup.proposta.core.card.lock;

import br.com.zup.proposta.utils.AssertWithHttpStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Entity
public class BlockCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true, nullable = false)
    private UUID id;

    private String userAgent;

    private String ipAddress;

    private LocalDateTime blockedAt = LocalDateTime.now();

    /**
     * Hibernate usage only
     */
    @Deprecated
    protected BlockCard() { }

    public BlockCard(String userAgent, String ipAddress) {
        AssertWithHttpStatus.notNull(userAgent, BAD_REQUEST);
        AssertWithHttpStatus.notNull(ipAddress, BAD_REQUEST);

        this.userAgent = userAgent;
        this.ipAddress = ipAddress;
    }
}
