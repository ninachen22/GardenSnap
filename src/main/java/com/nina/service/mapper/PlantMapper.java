package com.nina.service.mapper;

import com.nina.domain.Plant;
import com.nina.domain.User;
import com.nina.service.dto.PlantDTO;
import com.nina.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Plant} and its DTO {@link PlantDTO}.
 */
@Mapper(componentModel = "spring")
public interface PlantMapper extends EntityMapper<PlantDTO, Plant> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    PlantDTO toDto(Plant s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
