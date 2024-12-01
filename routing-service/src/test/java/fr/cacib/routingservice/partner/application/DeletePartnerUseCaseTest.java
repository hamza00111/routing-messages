package fr.cacib.routingservice.partner.application;

import fr.cacib.routingservice.common.NotUUIDException;
import fr.cacib.routingservice.partner.domain.port.outbound.PartnerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeletePartnerUseCaseTest {

	@Mock
	private PartnerRepository repository;

	@InjectMocks
	private DeletePartnerUseCase deletePartnerUseCase;

	@Test
	void shouldDeletePartnerSuccessfully() {
		UUID validUUID = UUID.randomUUID();

		doNothing().when(repository)
				.delete(validUUID);

		deletePartnerUseCase.deletePartner(validUUID.toString());

		verify(repository,
				times(1)).delete(validUUID);
	}

	@Test
	void shouldThrowNotUUIDExceptionWhenInvalidUUID() {
		String invalidId = "invalid-uuid";

		assertThrows(NotUUIDException.class,
				() -> deletePartnerUseCase.deletePartner(invalidId));

		verify(repository,
				never()).delete(any(UUID.class));
	}

	@Test
	void shouldThrowRuntimeExceptionWhenRepositoryFails() {
		UUID validUUID = UUID.randomUUID();

		doThrow(new RuntimeException("Database error")).when(repository)
				.delete(validUUID);

		assertThrows(RuntimeException.class,
				() -> deletePartnerUseCase.deletePartner(validUUID.toString()));

		verify(repository).delete(validUUID);
	}
}

