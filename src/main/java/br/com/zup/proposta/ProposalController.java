package br.com.zup.proposta;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/proposal")
public class ProposalController {

    private final EntityManager entityManager;

    public ProposalController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> create(@Valid @RequestBody NewProposalRequest newProposalRequest,
                                       UriComponentsBuilder uriComponentsBuilder) {

        Proposal proposal = newProposalRequest.toModel();
        this.entityManager.persist(proposal);

        URI uri = uriComponentsBuilder.path("/proposal/{id}").buildAndExpand(proposal.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
