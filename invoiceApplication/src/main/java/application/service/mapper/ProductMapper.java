package application.service.mapper;

import application.domain.Product;
import application.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper extends ModelMapper<Product, ProductDTO> {
}

