package br.com.zup.proposta.core.card;

import br.com.zup.proposta.core.card.biometry.Biometry;
import br.com.zup.proposta.core.card.block.BlockCard;
import br.com.zup.proposta.core.card.digitalwallet.DigitalWallet;
import br.com.zup.proposta.core.card.travelnotice.CardTravelNotice;
import br.com.zup.proposta.core.proposal.Proposal;
import br.com.zup.proposta.utils.AssertWithHttpStatus;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static br.com.zup.proposta.core.card.CardStatus.BLOCKED;
import static br.com.zup.proposta.core.card.CardStatus.FREE;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

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

    @OneToOne(cascade = CascadeType.PERSIST)
    private BlockCard blockCard;

    @OneToOne(cascade = CascadeType.PERSIST)
    private CardTravelNotice cardTravelNotice;

    @NotNull
    @OneToOne(optional = false, fetch = FetchType.LAZY, mappedBy = "card")
    private Proposal proposal;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardStatus cardStatus = FREE;

    @OneToMany
    @JoinColumn(name = "card_id")
    private Set<DigitalWallet> digitalWalletList = new HashSet<>();

    /**
     * Hibernate usage only
     */
    @Deprecated
    protected Card() { }

    public Card(String number, Proposal proposal) {
        this.number = number;
        this.proposal = proposal;
    }

    public void addBiometry(Biometry biometry) {
        this.biometryList.add(biometry);
    }

    public void block(BlockCard blockCard) {
        AssertWithHttpStatus.isNull(this.blockCard, UNPROCESSABLE_ENTITY);
        this.blockCard = blockCard;
        this.cardStatus = BLOCKED;
    }

    public Proposal getProposal() {
        return proposal;
    }

    public String getNumber() {
        return number;
    }

    public void informTravelNotice(CardTravelNotice cardTravelNotice) {
        this.cardTravelNotice = cardTravelNotice;
    }

    public boolean addWallet(DigitalWallet digitalwallet) {
        return this.digitalWalletList.add(digitalwallet);
    }
}
