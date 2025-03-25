package com.mourinhan.identify_service.mapper;

import com.mourinhan.identify_service.dto.request.UserCreationRequest;
import com.mourinhan.identify_service.dto.request.UserUpdateRequest;
import com.mourinhan.identify_service.dto.response.UserResponse;
import com.mourinhan.identify_service.entities.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
