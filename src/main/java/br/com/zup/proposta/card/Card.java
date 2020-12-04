package br.com.zup.proposta.card;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true, nullable = false)
    private UUID id;

    private String number;

    /**
     * Hibernate usage only
     */
    @Deprecated
    protected Card() { }

    public Card(String number) {
        this.number = number;
    }
}
