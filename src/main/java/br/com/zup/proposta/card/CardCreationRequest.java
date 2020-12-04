package br.com.zup.proposta.card;

import br.com.zup.proposta.proposal.Proposal;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CardCreationRequest {

    @JsonProperty("documento")
    private String document;

    @JsonProperty("nome")
    private String name;

    @JsonProperty("idProposta")
    private UUID idProposal;

    public CardCreationRequest(Proposal proposal) {
        this.document = proposal.getDocument();
        this.name = proposal.getName();
        this.idProposal = proposal.getId();
    }
}
