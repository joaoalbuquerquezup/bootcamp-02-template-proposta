package br.com.zup.proposta.card;

import br.com.zup.proposta.proposal.Proposal;
import br.com.zup.proposta.proposal.ProposalRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import java.util.List;

import static br.com.zup.proposta.proposal.ProposalStatus.ELIGIBLE;

@Component
public class AssociateCardToProposalScheduler {

    private final ProposalRepository proposalRepository;
    private final CardClient cardClient;

    private final EntityManager entityManager;
    private final TransactionTemplate txTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(AssociateCardToProposalScheduler.class);

    public AssociateCardToProposalScheduler(ProposalRepository proposalRepository,
                                            CardClient cardClient,
                                            EntityManager entityManager,
                                            TransactionTemplate txTemplate) {
        this.proposalRepository = proposalRepository;
        this.cardClient = cardClient;
        this.entityManager = entityManager;
        this.txTemplate = txTemplate;
    }

    /**
     * Verificar se é melhor aproveitar a independência da chamada de criação
     * e leitura do cartão e fazer duas iterações
     *
     * REFATORAR - 10 PTs CDD
     */
    @Scheduled(fixedDelayString = "${card-to-proposal.schedule.fixed-delay}")
    public void schedule() {

        List<Proposal> proposalList = this.proposalRepository.findByStatusAndCardIsNull(ELIGIBLE);

        for (Proposal proposal : proposalList) {

            Card card;

            try {
                CardCreationRequest cardCreationRequest = new CardCreationRequest(proposal);
                this.cardClient.generateCard(cardCreationRequest);

                CardRetrieveResponse cardRetrieveResponse = this.cardClient.retrieveCard(proposal.getId());
                card = new Card(cardRetrieveResponse.getCardNumber());

            } catch (FeignException e) {
                LOGGER.error("Error at card generation | Status code: {}, Body: {}, Message: {}",
                        e.status(),
                        e.contentUTF8(),
                        e.getMessage());

                continue;
            }

            this.txTemplate.execute(txStatus -> {
                proposal.setCard(card);
                this.entityManager.persist(card);
                this.proposalRepository.save(proposal);
                LOGGER.info("Card associated and created | Document: {}", proposal.getDocument());

                return txStatus;
            });
        }
    }

}
