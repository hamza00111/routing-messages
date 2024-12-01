package fr.cacib.routingservice.message.infrastructure.adapters.out;

import fr.cacib.routingservice.message.domain.model.Message;
import fr.cacib.routingservice.message.domain.valueobject.PaginatedResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class JpaMessageRepositoryAdapterTest {

	@Mock
	private JpaMessageRepository jpaMessageRepository;

	@InjectMocks
	private JpaMessageRepositoryAdapter jpaMessageRepositoryAdapter;

	@Test
	void testSave_MessageIsNull_DoesNotInvokeSave() {
		jpaMessageRepositoryAdapter.save(null);
		verify(jpaMessageRepository,
				never()).save(any(Message.class));
	}

	@Test
	void testSave_MessageWithNullId_DoesNotInvokeSave() {
		Message message = new Message();
		message.setId(null);
		jpaMessageRepositoryAdapter.save(message);
		verify(jpaMessageRepository,
				never()).save(any(Message.class));
	}

	@Test
	void testSave_ValidMessage_InvokesSave() {
		Message message = new Message();
		message.setId(UUID.randomUUID());
		jpaMessageRepositoryAdapter.save(message);
		verify(jpaMessageRepository).save(message);
	}

	@Test
	void testGetMessages_ReturnsPaginatedResponse() {
		Message message1 = Message.builder()
				.id(UUID.randomUUID())
				.content("Message 1")
				.build();

		Message message2 = Message.builder()
				.id(UUID.randomUUID())
				.content("Message 2")
				.build();

		Page<Message> page = new PageImpl<>(List.of(message1, message2),
				PageRequest.of(0,
						10),
				10);
		when(jpaMessageRepository.findAll(any(PageRequest.class))).thenReturn(page);

		PaginatedResponse<Message> response = jpaMessageRepositoryAdapter.getMessages(0,
				10);

		assertNotNull(response);
		assertEquals(2,
				response.getContent()
						.size());
		assertEquals(10,
				response.getTotalElements());
		assertEquals(1,
				response.getTotalPages());
		assertTrue(response.isFirst());
		assertTrue(response.isLast());
	}

	@Test
	void testFindById_Found_ReturnsMessage() {
		UUID id = UUID.randomUUID();
		Message message = new Message();
		when(jpaMessageRepository.findById(id)).thenReturn(Optional.of(message));

		Optional<Message> result = jpaMessageRepositoryAdapter.findById(id);

		assertTrue(result.isPresent());
		assertEquals(message,
				result.get());
	}

	@Test
	void testFindById_NotFound_ReturnsEmpty() {
		UUID id = UUID.randomUUID();
		when(jpaMessageRepository.findById(id)).thenReturn(Optional.empty());

		Optional<Message> result = jpaMessageRepositoryAdapter.findById(id);

		assertFalse(result.isPresent());
	}
}
