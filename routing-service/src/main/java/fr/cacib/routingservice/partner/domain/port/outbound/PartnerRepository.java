package fr.cacib.routingservice.partner.domain.port.outbound;

import fr.cacib.routingservice.message.domain.valueobject.PaginatedResponse;
import fr.cacib.routingservice.partner.domain.model.Partner;

import java.util.UUID;

public interface PartnerRepository {

	Partner save(Partner partner);

	PaginatedResponse<Partner> findAll(int offset, int limit);

	void delete(UUID id);
}
