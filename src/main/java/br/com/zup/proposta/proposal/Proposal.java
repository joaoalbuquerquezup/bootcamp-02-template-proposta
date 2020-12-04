package br.com.zup.proposta.proposal;

import br.com.zup.proposta.card.Card;
import br.com.zup.proposta.validation.CpfCnpj;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

import static br.com.zup.proposta.proposal.ProposalStatus.PENDNING;
import static javax.persistence.EnumType.STRING;

@Entity
public class Proposal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true, nullable = false)
    private UUID id;

    @NotBlank @CpfCnpj
    @Column(nullable = false, unique = true)
    private String document;

    @NotBlank @Email
    @Column(nullable = false)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String address;

    @NotNull @Positive
    @Column(nullable = false)
    private BigDecimal wage;

    @Enumerated(STRING)
    private ProposalStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    private Card card;

    /**
     * Hibernate usage only
     */
    @Deprecated
    protected Proposal() { }

    public Proposal(@NotBlank String document,
                    @NotBlank @Email String email,
                    @NotBlank String name,
                    @NotBlank String address,
                    @NotNull @Positive BigDecimal wage) {
        this.document = document;
        this.email = email;
        this.name = name;
        this.address = address;
        this.wage = wage;
        this.status = PENDNING;
    }

    public UUID getId() {
        return id;
    }

    public String getDocument() {
        return document;
    }

    public String getName() {
        return name;
    }

    public void setStatus(ProposalStatus status) {
        this.status = status;
    }

    public ProposalStatus getStatus() {
        return status;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public BigDecimal getWage() {
        return wage;
    }
}
