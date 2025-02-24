package com.be001.cinevibe.mapper;

import com.be001.cinevibe.dto.UserProfileDTO;
import com.be001.cinevibe.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.context.annotation.Primary;

@Primary
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ProfileMapper {

    UserProfileDTO toProfile(User user);

    User toEntity(@MappingTarget User entity, UserProfileDTO profile);
}