package br.com.zup.proposta.core.proposal.analyze;

import br.com.zup.proposta.core.proposal.ProposalStatus;

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
