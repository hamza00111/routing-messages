package fr.cacib.routingservice.partner.infrastructure.adapters.out;

import fr.cacib.routingservice.partner.domain.model.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaPartnerRepository extends JpaRepository<Partner, UUID> {
}
