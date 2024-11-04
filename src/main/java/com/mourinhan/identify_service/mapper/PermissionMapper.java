package com.mourinhan.identify_service.mapper;

import com.mourinhan.identify_service.dto.request.PermissionRequest;
import com.mourinhan.identify_service.dto.response.PermissionResponse;
import com.mourinhan.identify_service.entities.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}
