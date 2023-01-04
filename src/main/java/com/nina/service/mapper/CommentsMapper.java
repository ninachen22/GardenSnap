package com.nina.service.mapper;

import com.nina.domain.Comments;
import com.nina.domain.Plant;
import com.nina.service.dto.CommentsDTO;
import com.nina.service.dto.PlantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Comments} and its DTO {@link CommentsDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommentsMapper extends EntityMapper<CommentsDTO, Comments> {
    @Mapping(target = "plant", source = "plant", qualifiedByName = "plantId")
    CommentsDTO toDto(Comments s);

    @Named("plantId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PlantDTO toDtoPlantId(Plant plant);
}
