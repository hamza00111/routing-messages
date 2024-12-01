package fr.cacib.routingservice.partner.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "partner")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Partner {

	@Id
	@Column(name = "id")
	private UUID id;

	@NotNull
	@Column(name = "alias")
	private String alias;

	@NotNull
	@Column(name = "type")
	private String type;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "direction")
	private Direction direction;

	@Column(name = "application")
	private String application;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "processed_flow_type")
	private ProcessedFlowType processedFlowType;

	@NotNull
	@Column(name = "description")
	private String description;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Partner partner = (Partner) o;
		return id.equals(partner.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
