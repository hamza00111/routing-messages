package fr.cacib.routingservice.partner.domain.port.inbound;

import fr.cacib.routingservice.partner.domain.model.Partner;
import fr.cacib.routingservice.partner.domain.valueobject.AddPartnerCommand;

public interface IAddPartnerUseCase {

	Partner addPartner(AddPartnerCommand command);
}
