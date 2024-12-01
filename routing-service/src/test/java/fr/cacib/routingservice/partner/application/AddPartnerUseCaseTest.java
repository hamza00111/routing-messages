package fr.cacib.routingservice.partner.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import fr.cacib.routingservice.partner.domain.exceptions.AddPartnerCommandException;
import fr.cacib.routingservice.partner.domain.exceptions.AddPartnerException;
import fr.cacib.routingservice.partner.domain.model.Direction;
import fr.cacib.routingservice.partner.domain.model.Partner;
import fr.cacib.routingservice.partner.domain.model.ProcessedFlowType;
import fr.cacib.routingservice.partner.domain.port.outbound.PartnerRepository;
import fr.cacib.routingservice.partner.domain.valueobject.AddPartnerCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class AddPartnerUseCaseTest {

	@Mock
	private PartnerRepository repository;

	@InjectMocks
	private AddPartnerUseCase addPartnerUseCase;

	@Test
	void shouldAddPartnerSuccessfully() {
		AddPartnerCommand command = AddPartnerCommand.builder()
				.alias("alias1")
				.type("type1")
				.direction("INBOUND")
				.application("application1")
				.processedFlowType("MESSAGE")
				.description("description1")
				.build();

		Partner mockPartner = Partner.builder()
				.id(UUID.randomUUID())
				.alias(command.getAlias())
				.type(command.getType())
				.application(command.getApplication())
				.processedFlowType(ProcessedFlowType.MESSAGE)
				.direction(Direction.INBOUND)
				.description(command.getDescription())
				.build();

		when(repository.save(any(Partner.class))).thenReturn(mockPartner);

		Partner result = addPartnerUseCase.addPartner(command);

		assertEquals(command.getAlias(),
				result.getAlias());
		assertEquals(command.getType(),
				result.getType());
		assertEquals(command.getApplication(),
				result.getApplication());
		assertEquals(ProcessedFlowType.MESSAGE,
				result.getProcessedFlowType());
		assertEquals(Direction.INBOUND,
				result.getDirection());
		assertEquals(command.getDescription(),
				result.getDescription());

		verify(repository,
				times(1)).save(any(Partner.class));
	}

	@Test
	void shouldThrowAddPartnerCommandExceptionWhenInvalidEnumValue() {
		AddPartnerCommand command = AddPartnerCommand.builder()
				.alias("alias1")
				.type("type1")
				.direction("INVALID_DIRECTION")
				.application("application1")
				.processedFlowType("MESSAGE")
				.description("description1")
				.build();

		assertThrows(AddPartnerCommandException.class,
				() -> addPartnerUseCase.addPartner(command));
		verify(repository,
				never()).save(any(Partner.class));
	}

	@Test
	void shouldThrowAddPartnerExceptionWhenRepositoryFails() {
		AddPartnerCommand command = AddPartnerCommand.builder()
				.alias("alias1")
				.type("type1")
				.direction("INBOUND")
				.application("application1")
				.processedFlowType("MESSAGE")
				.description("description1")
				.build();

		when(repository.save(any(Partner.class))).thenThrow(new RuntimeException("Database error"));

		assertThrows(AddPartnerException.class,
				() -> addPartnerUseCase.addPartner(command));

		verify(repository,
				times(1)).save(any(Partner.class));
	}
}
