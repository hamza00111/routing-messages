package fr.cacib.routingservice.message.infrastructure.adapters.out;

import fr.cacib.routingservice.message.domain.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaMessageRepository extends JpaRepository<Message, UUID> {
}
