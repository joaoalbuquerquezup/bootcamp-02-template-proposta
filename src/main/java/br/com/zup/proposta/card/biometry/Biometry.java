package br.com.zup.proposta.card.biometry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@Entity
public class Biometry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true, nullable = false)
    private UUID id;

    @Column(updatable =false, nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private byte[] fingerprint;

    /**
     * Hibernate usage only
     */
    @Deprecated
    protected Biometry() { }

    /**
     * @param fingerprintBase64 dados da biometria codificados em base64
     */
    public Biometry(String fingerprintBase64) {
        byte[] decodedFingerprint = Base64.getDecoder().decode(fingerprintBase64);
        this.fingerprint = decodedFingerprint;
    }

    public UUID getId() {
        return id;
    }
}
