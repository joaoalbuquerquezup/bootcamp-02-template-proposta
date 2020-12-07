package br.com.zup.proposta.card;

import br.com.zup.proposta.card.biometry.Biometry;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true, nullable = false)
    private UUID id;

    private String number;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "card_id")
    private List<Biometry> biometryList = new ArrayList<>();

    /**
     * Hibernate usage only
     */
    @Deprecated
    protected Card() { }

    public Card(String number) {
        this.number = number;
    }

    public void addBiometry(Biometry biometry) {
        this.biometryList.add(biometry);
    }
}
