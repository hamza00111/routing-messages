package fr.cacib.routingservice.message.infrastructure.adapters.out;

import fr.cacib.routingservice.message.domain.model.Message;
import fr.cacib.routingservice.message.domain.ports.outbound.MessageRepository;
import fr.cacib.routingservice.message.domain.valueobject.PaginatedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaMessageRepositoryAdapter implements MessageRepository {

	private final JpaMessageRepository jpaMessageRepository;

	public JpaMessageRepositoryAdapter(JpaMessageRepository jpaMessageRepository) {
		this.jpaMessageRepository = jpaMessageRepository;
	}

	@Override
	public void save(Message message) {
		if (message != null && message.getId() != null) {
			this.jpaMessageRepository.save(message);
		}
	}

	@Override
	public PaginatedResponse<Message> getMessages(int offset,
												  int limit) {

		Page<Message> messagePage = jpaMessageRepository.findAll(PageRequest.of(offset,
				limit));

		return PaginatedResponse.<Message>builder()
				.content(messagePage.getContent())
				.pageNumber(messagePage.getNumber())
				.pageSize(messagePage.getSize())
				.totalElements(messagePage.getTotalElements())
				.totalPages(messagePage.getTotalPages())
				.last(messagePage.isLast())
				.first(messagePage.isFirst())
				.build();
	}

	@Override
	public Optional<Message> findById(UUID id) {
		return jpaMessageRepository.findById(id);
	}
}
