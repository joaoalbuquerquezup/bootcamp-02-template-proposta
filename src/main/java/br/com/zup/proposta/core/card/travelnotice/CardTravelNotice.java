package br.com.zup.proposta.core.card.travelnotice;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class CardTravelNotice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true, nullable = false)
    private UUID id;

    @NotBlank
    @Column(nullable = false)
    private String destiny;

    @NotBlank
    @Column(nullable = false)
    private String userAgent;

    @NotBlank
    @Column(nullable = false)
    private String ipAddress;

    @Column(nullable = false)
    private LocalDate finishDate;

    @Future
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * Hibernate usage only
     */
    @Deprecated
    protected CardTravelNotice() { }

    public CardTravelNotice(@NotBlank String destiny,
                            @NotBlank String userAgent,
                            @NotBlank String ipAddress,
                            LocalDate finishDate) {
        this.destiny = destiny;
        this.userAgent = userAgent;
        this.ipAddress = ipAddress;
        this.finishDate = finishDate;
    }
}
