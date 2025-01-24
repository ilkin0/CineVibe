package com.be001.cinevibe.mapper;

import com.be001.cinevibe.dto.UserProfile;
import com.be001.cinevibe.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    UserProfile toProfile(User user);

}
