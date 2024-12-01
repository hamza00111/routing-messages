package fr.cacib.routingservice.partner.infrastructure.adapters.out;

import fr.cacib.routingservice.message.domain.valueobject.PaginatedResponse;
import fr.cacib.routingservice.partner.domain.model.Partner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JpaPartnerRepositoryAdapterTest {

	@Mock
	private JpaPartnerRepository jpaPartnerRepository;

	@InjectMocks
	private JpaPartnerRepositoryAdapter adapter;

	@Test
	void shouldSavePartner() {
		Partner partner = Partner.builder()
				.id(UUID.randomUUID())
				.alias("Test Partner")
				.build();

		when(jpaPartnerRepository.save(partner)).thenReturn(partner);

		Partner savedPartner = adapter.save(partner);

		assertEquals(partner,
				savedPartner);
		verify(jpaPartnerRepository,
				times(1)).save(partner);
	}

	@Test
	void shouldFindAllPartners() {
		int offset = 0;
		int limit = 10;
		List<Partner> partners = List.of(new Partner(),
				new Partner());

		Page<Partner> mockPage = new PageImpl<>(partners,
				PageRequest.of(offset,
						limit,
						Sort.by(Sort.Direction.ASC,
								"alias")),
				partners.size());

		when(jpaPartnerRepository.findAll(PageRequest.of(offset,
				limit,
				Sort.by(Sort.Direction.ASC,
						"alias"))))
				.thenReturn(mockPage);

		PaginatedResponse<Partner> response = adapter.findAll(offset,
				limit);

		assertEquals(partners,
				response.getContent());
		assertEquals(offset,
				response.getPageNumber());
		assertEquals(limit,
				response.getPageSize());
		assertEquals(partners.size(),
				response.getTotalElements());
		verify(jpaPartnerRepository,
				times(1)).findAll(PageRequest.of(offset,
				limit,
				Sort.by(Sort.Direction.ASC,
						"alias")));
	}

	@Test
	void shouldDeletePartnerById() {
		UUID partnerId = UUID.randomUUID();

		doNothing().when(jpaPartnerRepository)
				.deleteById(partnerId);

		adapter.delete(partnerId);

		verify(jpaPartnerRepository,
				times(1)).deleteById(partnerId);
	}
}
