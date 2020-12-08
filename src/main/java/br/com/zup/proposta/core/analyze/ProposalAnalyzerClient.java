package br.com.zup.proposta.core.analyze;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "financial-restriction", url= "${proposal-analyzer.api-location}")
public interface ProposalAnalyzerClient {

    @PostMapping(value = "/solicitacao")
    CheckFinancialRestrictionResponse check(CheckFinancialRestrictionRequest checkFinancialRestrictionRequest);

}
