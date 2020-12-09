package br.com.zup.proposta.core.card;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {

    @EntityGraph(attributePaths = { "biometryList" })
    Optional<Card> getById(UUID id);

    @Query("SELECT c FROM Card c LEFT JOIN FETCH c.digitalWalletList WHERE c.id = :id")
    Optional<Card> findByIdWithDigitalWallet(UUID id);

}
