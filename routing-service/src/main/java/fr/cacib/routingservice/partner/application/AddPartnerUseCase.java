package fr.cacib.routingservice.partner.application;

import fr.cacib.routingservice.partner.domain.exceptions.AddPartnerCommandException;
import fr.cacib.routingservice.partner.domain.exceptions.AddPartnerException;
import fr.cacib.routingservice.partner.domain.model.Direction;
import fr.cacib.routingservice.partner.domain.model.Partner;
import fr.cacib.routingservice.partner.domain.model.ProcessedFlowType;
import fr.cacib.routingservice.partner.domain.port.inbound.IAddPartnerUseCase;
import fr.cacib.routingservice.partner.domain.port.outbound.PartnerRepository;
import fr.cacib.routingservice.partner.domain.valueobject.AddPartnerCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Component
public class AddPartnerUseCase implements IAddPartnerUseCase {

	private final PartnerRepository repository;

	public AddPartnerUseCase(PartnerRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public Partner addPartner(AddPartnerCommand command) {
		try {
			Partner partner = Partner.builder()
					.id(UUID.randomUUID())
					.type(command.getType())
					.alias(command.getAlias())
					.application(command.getApplication())
					.processedFlowType(ProcessedFlowType.valueOf(command.getProcessedFlowType()))
					.direction(Direction.valueOf(command.getDirection()))
					.description(command.getDescription())
					.build();
			return repository.save(partner);
		} catch (IllegalArgumentException e) {
			log.error("The command to add a partner raised an exception {}", e.getMessage());
			throw new AddPartnerCommandException(e);
		} catch (Exception e) {
			log.error("Failed to add partner with alias {} due to {}",
					command.getAlias(),
					e.getMessage(),
					e);
			throw new AddPartnerException(e);
		}
	}
}
