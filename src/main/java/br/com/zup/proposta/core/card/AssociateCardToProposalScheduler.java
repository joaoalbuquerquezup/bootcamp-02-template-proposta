package br.com.zup.proposta.core.card;

import br.com.zup.proposta.core.proposal.Proposal;
import br.com.zup.proposta.core.proposal.ProposalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static br.com.zup.proposta.core.proposal.ProposalStatus.ELIGIBLE;

@Component
public class AssociateCardToProposalScheduler {

    private final ProposalRepository proposalRepository;
    private final CardService cardService;

    private final EntityManager entityManager;
    private final TransactionTemplate txTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(AssociateCardToProposalScheduler.class);

    public AssociateCardToProposalScheduler(ProposalRepository proposalRepository,
                                            CardService cardService,
                                            EntityManager entityManager,
                                            TransactionTemplate txTemplate) {
        this.proposalRepository = proposalRepository;
        this.cardService = cardService;
        this.entityManager = entityManager;
        this.txTemplate = txTemplate;
    }

    @Scheduled(fixedDelayString = "${card-to-proposal.schedule.fixed-delay}")
    public void schedule() {

        List<Proposal> proposalList = this.proposalRepository.findByStatusAndCardIsNull(ELIGIBLE);

        proposalList.forEach(proposal -> {

            Optional<Card> optionalCard = this.cardService.retrieveCardByProposal(proposal);
            optionalCard.ifPresent(card -> {

                this.txTemplate.execute(txStatus -> {
                    this.entityManager.persist(card);

                    proposal.setCard(card);
                    this.proposalRepository.save(proposal);

                    LOGGER.info("Card associated and created | Document: {}", proposal.getDocument());

                    return txStatus;
                });
            });
        });
    }
}
