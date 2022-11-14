package application.service.mapper;

import application.domain.Invoice;
import application.dto.InvoiceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InvoiceMapper extends ModelMapper<Invoice, InvoiceDTO> {
}

