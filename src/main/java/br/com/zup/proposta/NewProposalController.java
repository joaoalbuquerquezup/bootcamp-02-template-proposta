package br.com.zup.proposta;

import br.com.zup.proposta.analyze.ProposalAnalyzerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RestController
@RequestMapping("/proposal")
public class NewProposalController {

    private final EntityManager entityManager;
    private final ProposalAnalyzerService proposalAnalyzerService;

    public NewProposalController(EntityManager entityManager,
                                 ProposalAnalyzerService proposalAnalyzerService) {
        this.entityManager = entityManager;
        this.proposalAnalyzerService = proposalAnalyzerService;
    }

    @PostMapping
    @Transactional // tirar
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
        this.entityManager.persist(proposal);

        var proposalStatus = this.proposalAnalyzerService.getProposalStatus(proposal);
        proposal.setStatus(proposalStatus);

        URI uri = uriComponentsBuilder.path("/proposal/{id}").buildAndExpand(proposal.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
