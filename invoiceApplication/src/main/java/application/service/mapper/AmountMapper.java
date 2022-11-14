package application.service.mapper;

import application.domain.Amount;
import application.domain.Invoice;
import application.dto.AmountDTO;
import application.dto.InvoiceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AmountMapper extends ModelMapper<Amount, AmountDTO> {
}

