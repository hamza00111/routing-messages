package fr.cacib.routingservice.partner.domain.port.inbound;

import fr.cacib.routingservice.message.domain.valueobject.PaginatedResponse;
import fr.cacib.routingservice.partner.domain.model.Partner;

public interface IFetchPartnersUseCase {

	PaginatedResponse<Partner> fetchPartners(int offset,
											 int limit);
}
