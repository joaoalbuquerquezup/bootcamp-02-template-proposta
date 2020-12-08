package br.com.zup.proposta.core.analyze;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CheckFinancialRestrictionResponse {

    private FinancialRestrictionStatus resultadoSolicitacao;

    public FinancialRestrictionStatus getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }
}
