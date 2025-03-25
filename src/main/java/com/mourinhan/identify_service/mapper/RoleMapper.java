package com.mourinhan.identify_service.mapper;

import com.mourinhan.identify_service.dto.request.RoleRequest;
import com.mourinhan.identify_service.dto.response.RoleResponse;
import com.mourinhan.identify_service.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);
    RoleResponse toRoleResponse(Role role);
}
