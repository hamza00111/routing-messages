package fr.cacib.routingservice.partner.application;

import fr.cacib.routingservice.message.domain.valueobject.PaginatedResponse;
import fr.cacib.routingservice.partner.domain.model.Partner;
import fr.cacib.routingservice.partner.domain.port.inbound.IFetchPartnersUseCase;
import fr.cacib.routingservice.partner.domain.port.outbound.PartnerRepository;
import org.springframework.stereotype.Component;

@Component
public class FetchPartnersUseCase implements IFetchPartnersUseCase {

	private final PartnerRepository repository;

	public FetchPartnersUseCase(PartnerRepository repository) {
		this.repository = repository;
	}

	@Override
	public PaginatedResponse<Partner> fetchPartners(int offset,
													int limit) {
		return repository.findAll(offset,
				limit);
	}
}
