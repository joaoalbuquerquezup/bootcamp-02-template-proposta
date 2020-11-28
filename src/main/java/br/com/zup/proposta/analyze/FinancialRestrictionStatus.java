package br.com.zup.proposta.analyze;

import br.com.zup.proposta.ProposalStatus;

public enum FinancialRestrictionStatus {

    SEM_RESTRICAO {
        @Override
        public ProposalStatus mapToProposalStatus() {
            return ProposalStatus.ELIGIBLE;
        }
    },

    COM_RESTRICAO {
        @Override
        public ProposalStatus mapToProposalStatus() {
            return ProposalStatus.NOT_ELIGIBLE;
        }
    };

    public abstract ProposalStatus mapToProposalStatus();

}
