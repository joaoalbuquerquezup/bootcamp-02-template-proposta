package br.com.zup.proposta.core.card.digitalwallet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

import static javax.persistence.EnumType.STRING;

@Entity
public class DigitalWallet {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true, nullable = false)
    private UUID id;

    @NotBlank @Email
    @Column(nullable = false)
    private String email;

    @NotNull
    @Column(nullable = false) @Enumerated(STRING)
    private WalletName walletName;

    @NotBlank
    private String walletIdAtExternalSystem;
    
    /**
     * Hibernate usage only
     */
    @Deprecated
    protected DigitalWallet() { }

    public DigitalWallet(@NotBlank @Email String email,
                         @NotNull WalletName walletName,
                         String walletIdAtExternalSystem) {
        this.email = email;
        this.walletName = walletName;
        this.walletIdAtExternalSystem = walletIdAtExternalSystem;
    }

    public UUID getId() {
        return this.id;
    }

    public WalletName getWalletName() {
        return this.walletName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        DigitalWallet that = (DigitalWallet) o;
        return this.walletName == that.walletName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.walletName);
    }

}
