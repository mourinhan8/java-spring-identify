package com.mourinhan.identify_service.mapper;

import com.mourinhan.identify_service.dto.request.UserCreationRequest;
import com.mourinhan.identify_service.dto.request.UserUpdateRequest;
import com.mourinhan.identify_service.dto.response.UserResponse;
import com.mourinhan.identify_service.entities.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

//    @Mapping(source = "firstName", target = "lastName")
//    @Mapping(target = "lastName", ignore = true)
    @Mapping(target = "roles", ignore = true)
    UserResponse toUserResponse(User user);

    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
