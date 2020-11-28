package br.com.zup.proposta.analyze;

import br.com.zup.proposta.Proposal;
import br.com.zup.proposta.ProposalStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import feign.FeignException;
import org.springframework.stereotype.Service;

import static br.com.zup.proposta.utils.StaticUtils.JSON_MAPPER;

@Service
public class ProposalAnalyzerService {

    private final ProposalAnalyzerClient proposalAnalyzerClient;

    public ProposalAnalyzerService(ProposalAnalyzerClient proposalAnalyzerClient) {
        this.proposalAnalyzerClient = proposalAnalyzerClient;
    }

    // TODO: configs de resiliencia
    public ProposalStatus getProposalStatus(Proposal proposal) {

        try {
            var checkFinancialRestrictionRequest = new CheckFinancialRestrictionRequest(proposal);
            var financialRestrictionResponse = this.proposalAnalyzerClient.check(checkFinancialRestrictionRequest);
            return financialRestrictionResponse.getResultadoSolicitacao().mapToProposalStatus();

        } catch (FeignException.UnprocessableEntity unprocessableEntity) {
            try {
                return JSON_MAPPER
                        .readValue(unprocessableEntity.contentUTF8(), CheckFinancialRestrictionResponse.class)
                        .getResultadoSolicitacao()
                        .mapToProposalStatus();

            } catch (JsonProcessingException jsonProcessingException) {
                throw new RuntimeException(jsonProcessingException);
            }
        }

    }
}
