package br.com.zup.proposta.proposal.view;

import br.com.zup.proposta.proposal.Proposal;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ProposalView {

    private String document;

    private String email;

    private String name;

    private String address;

    private BigDecimal wage;

    public ProposalView(Proposal proposal) {
        this.document = proposal.getDocument();
        this.email = proposal.getEmail();
        this.name = proposal.getName();
        this.address = proposal.getAddress();
        this.wage = proposal.getWage();
    }
}
