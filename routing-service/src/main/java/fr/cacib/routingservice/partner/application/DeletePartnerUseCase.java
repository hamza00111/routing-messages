package fr.cacib.routingservice.partner.application;

import fr.cacib.routingservice.common.NotUUIDException;
import fr.cacib.routingservice.partner.domain.port.inbound.IDeletePartnerUseCase;
import fr.cacib.routingservice.partner.domain.port.outbound.PartnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@Slf4j
public class DeletePartnerUseCase implements IDeletePartnerUseCase {

	private final PartnerRepository repository;

	public DeletePartnerUseCase(PartnerRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public void deletePartner(String id) {
		UUID uuid;
		try {
			uuid = UUID.fromString(id);
		} catch (IllegalArgumentException e) {
			log.error("Invalid partner id: {}", id);
			throw new NotUUIDException(id);
		}
		try {
			log.info("Deleting partner with id {}", uuid);
			repository.delete(uuid);
			log.info("Partner with id {} deleted", uuid);
		} catch (Exception e) {
			log.error("Error deleting partner with id {}", uuid, e);
			throw new RuntimeException(e);
		}
	}
}
