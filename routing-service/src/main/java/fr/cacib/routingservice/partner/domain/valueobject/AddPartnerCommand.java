package fr.cacib.routingservice.partner.domain.valueobject;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddPartnerCommand {

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
}
