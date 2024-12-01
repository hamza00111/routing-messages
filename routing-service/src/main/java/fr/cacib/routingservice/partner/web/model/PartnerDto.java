package fr.cacib.routingservice.partner.web.model;

import fr.cacib.routingservice.partner.domain.model.Partner;
import fr.cacib.routingservice.partner.domain.valueobject.AddPartnerCommand;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PartnerDto {

	private String id;

	@NotNull
	private String alias;

	@NotNull
	private String type;

	@NotNull
	private String direction;

	private String application;

	@NotNull
	private String processedFlowType;

	@NotNull
	private String description;

	public static PartnerDto fromDomain(Partner partner) {
		return PartnerDto.builder()
				.id(partner.getId().toString())
				.alias(partner.getAlias())
				.type(partner.getType())
				.description(partner.getDescription())
				.application(partner.getApplication())
				.direction(partner.getDirection().toString())
				.processedFlowType(partner.getProcessedFlowType().toString())
				.build();
	}

	public AddPartnerCommand toCommand() {
		return AddPartnerCommand.builder()
				.alias(getAlias())
				.type(getType())
				.description(getDescription())
				.application(getApplication())
				.direction(getDirection())
				.processedFlowType(getProcessedFlowType())
				.build();
	}
}
