package br.com.zup.proposta.core.proposal.view;

import br.com.zup.proposta.core.proposal.Proposal;
import br.com.zup.proposta.core.proposal.ProposalRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/proposal")
public class ViewProposalController {

    private final ProposalRepository proposalRepository;

    public ViewProposalController(ProposalRepository proposalRepository) {
        this.proposalRepository = proposalRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProposalView> view(@PathVariable UUID id) {

        Optional<Proposal> optionalProposal = proposalRepository.findById(id);

        Proposal proposal = optionalProposal.orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

        return ResponseEntity.ok(new ProposalView(proposal));
    }
}
