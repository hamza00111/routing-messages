package fr.cacib.routingservice.partner.application;

import fr.cacib.routingservice.message.domain.valueobject.PaginatedResponse;
import fr.cacib.routingservice.partner.domain.model.Partner;
import fr.cacib.routingservice.partner.domain.port.outbound.PartnerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FetchPartnersUseCaseTest {

	@Mock
	private PartnerRepository repository;

	@InjectMocks
	private FetchPartnersUseCase fetchPartnersUseCase;

	@Test
	void shouldFetchPartnersSuccessfully() {
		int offset = 0;
		int limit = 10;

		PaginatedResponse<Partner> mockResponse = PaginatedResponse.<Partner>builder()
				.content(List.of(new Partner(),
						new Partner()))
				.pageNumber(offset)
				.pageSize(limit)
				.totalElements(2L)
				.totalPages(1)
				.first(true)
				.last(true)
				.build();

		when(repository.findAll(offset,
				limit)).thenReturn(mockResponse);

		PaginatedResponse<Partner> result = fetchPartnersUseCase.fetchPartners(offset,
				limit);

		assertEquals(2,
				result.getContent()
						.size());
		assertEquals(offset,
				result.getPageNumber());
		assertEquals(limit,
				result.getPageSize());
		verify(repository,
				times(1)).findAll(offset,
				limit);
	}
}
