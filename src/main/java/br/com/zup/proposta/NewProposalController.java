package br.com.zup.proposta;

import br.com.zup.proposta.analyze.ProposalAnalyzerService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.net.URI;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RestController
@RequestMapping("/proposal")
public class NewProposalController {

    private final EntityManager entityManager;
    private final TransactionTemplate txTemplate;

    private final ProposalAnalyzerService proposalAnalyzerService;

    public NewProposalController(EntityManager entityManager,
                                 ProposalAnalyzerService proposalAnalyzerService,
                                 TransactionTemplate txTemplate) {
        this.entityManager = entityManager;
        this.txTemplate = txTemplate;
        this.proposalAnalyzerService = proposalAnalyzerService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody NewProposalRequest newProposalRequest,
                                       UriComponentsBuilder uriComponentsBuilder) {

        boolean proposalWithThisDocumentAlreadyExists = this.entityManager
                .createQuery("select 1 from Proposal where document = :document")
                .setParameter("document", newProposalRequest.getDocument())
                .getResultList()
                .size() > 0;

        // https://stackoverflow.com/a/62743197/8303951 -> Conseguir esse comportamento usando os validadores que eu queria
        if (proposalWithThisDocumentAlreadyExists) throw new ResponseStatusException(UNPROCESSABLE_ENTITY);

        Proposal proposal = newProposalRequest.toModel();

        txTemplate.execute(txStatus -> {
            this.entityManager.persist(proposal);
            return txStatus;
        });

        ProposalStatus proposalStatus = this.proposalAnalyzerService.getProposalStatus(proposal);

        txTemplate.execute(txStatus -> {
            proposal.setStatus(proposalStatus);
            this.entityManager.merge(proposal);
            return txStatus;
        });

        URI uri = uriComponentsBuilder.path("/proposal/{id}").buildAndExpand(proposal.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
