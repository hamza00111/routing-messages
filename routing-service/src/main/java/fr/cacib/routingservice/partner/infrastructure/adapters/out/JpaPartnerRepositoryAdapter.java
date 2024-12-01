package fr.cacib.routingservice.partner.infrastructure.adapters.out;

import fr.cacib.routingservice.message.domain.valueobject.PaginatedResponse;
import fr.cacib.routingservice.partner.domain.model.Partner;
import fr.cacib.routingservice.partner.domain.port.outbound.PartnerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class JpaPartnerRepositoryAdapter implements PartnerRepository {

	private final JpaPartnerRepository jpaPartnerRepository;

	public JpaPartnerRepositoryAdapter(JpaPartnerRepository jpaPartnerRepository) {
		this.jpaPartnerRepository = jpaPartnerRepository;
	}

	@Override
	public Partner save(Partner partner) {
		return jpaPartnerRepository.save(partner);
	}

	@Override
	public PaginatedResponse<Partner> findAll(int offset,
											  int limit) {
		Page<Partner> partnerPage = jpaPartnerRepository.findAll(PageRequest.of(offset,
				limit, Sort.by(Sort.Direction.ASC, "alias")));

		return PaginatedResponse.<Partner>builder()
				.content(partnerPage.getContent())
				.pageNumber(partnerPage.getNumber())
				.pageSize(partnerPage.getSize())
				.totalElements(partnerPage.getTotalElements())
				.totalPages(partnerPage.getTotalPages())
				.last(partnerPage.isLast())
				.first(partnerPage.isFirst())
				.build();
	}

	@Override
	public void delete(UUID id) {
		jpaPartnerRepository.deleteById(id);
	}
}
