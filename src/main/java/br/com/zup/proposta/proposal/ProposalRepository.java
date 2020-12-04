package br.com.zup.proposta.proposal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, UUID> {

    List<Proposal> findByStatusAndCardIsNull(ProposalStatus status);
}
