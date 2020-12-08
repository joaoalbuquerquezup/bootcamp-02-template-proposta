package br.com.zup.proposta.core.analyze;

import br.com.zup.proposta.core.proposal.Proposal;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CheckFinancialRestrictionRequest {

    private String documento;
    private String nome;
    private UUID idProposta;

    public CheckFinancialRestrictionRequest(Proposal proposal) {
        this.documento = proposal.getDocument();
        this.nome = proposal.getName();
        this.idProposta = proposal.getId();
    }
}
