package application.service.mapper;

import application.domain.User;
import application.dto.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends ModelMapper<User, UserResponseDTO> {
}

