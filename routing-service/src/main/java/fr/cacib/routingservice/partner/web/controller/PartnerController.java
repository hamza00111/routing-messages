package fr.cacib.routingservice.partner.web.controller;

import fr.cacib.routingservice.message.domain.valueobject.PaginatedResponse;
import fr.cacib.routingservice.partner.domain.model.Partner;
import fr.cacib.routingservice.partner.domain.port.inbound.IAddPartnerUseCase;
import fr.cacib.routingservice.partner.domain.port.inbound.IDeletePartnerUseCase;
import fr.cacib.routingservice.partner.domain.port.inbound.IFetchPartnersUseCase;
import fr.cacib.routingservice.partner.web.model.PartnerDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/partners")
public class PartnerController {

	private final IAddPartnerUseCase addPartnerUseCase;
	private final IFetchPartnersUseCase fetchPartnersUseCase;
	private final IDeletePartnerUseCase deletePartnerUseCase;

	public PartnerController(IAddPartnerUseCase addPartnerUseCase,
							 IFetchPartnersUseCase fetchPartnersUseCase,
							 IDeletePartnerUseCase deletePartnerUseCase) {
		this.addPartnerUseCase = addPartnerUseCase;
		this.fetchPartnersUseCase = fetchPartnersUseCase;
		this.deletePartnerUseCase = deletePartnerUseCase;
	}

	@PostMapping("/create")
	public ResponseEntity<PartnerDto> addPartner(@RequestBody @Valid PartnerDto partnerDTO) {
		Partner partner = addPartnerUseCase.addPartner(partnerDTO.toCommand());
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(PartnerDto.fromDomain(partner));
	}

	@GetMapping
	public ResponseEntity<PaginatedResponse<PartnerDto>> getPartners(
			@RequestParam(defaultValue = "0") int offset,
			@RequestParam(defaultValue = "10") int limit
	) {
		PaginatedResponse<Partner> partners = fetchPartnersUseCase.fetchPartners(offset,
				limit);

		if (partners.getContent()
				.isEmpty()) {
			return ResponseEntity.noContent()
					.build();
		}

		return ResponseEntity.ok(
				PaginatedResponse.<PartnerDto>builder()
						.content(partners.getContent()
								.stream()
								.map(PartnerDto::fromDomain)
								.toList())
						.pageNumber(partners.getPageNumber())
						.pageSize(partners.getPageSize())
						.last(partners.isLast())
						.first(partners.isFirst())
						.totalPages(partners.getTotalPages())
						.totalElements(partners.getTotalElements())
						.build()
		);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePartner(@PathVariable("id") String id) {
		deletePartnerUseCase.deletePartner(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
