package application.service.mapper;

import application.domain.Amount;
import application.domain.Customer;
import application.dto.AmountDTO;
import application.dto.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper extends ModelMapper<Customer, CustomerDTO> {
}

