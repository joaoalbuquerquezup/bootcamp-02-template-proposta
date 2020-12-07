package br.com.zup.proposta.card;

import br.com.zup.proposta.biometry.Biometry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.UUID;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true, nullable = false)
    private UUID id;

    private String number;

    @OneToMany
    private List<Biometry> biometryList;

    /**
     * Hibernate usage only
     */
    @Deprecated
    protected Card() { }

    public Card(String number) {
        this.number = number;
    }
}
