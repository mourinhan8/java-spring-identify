package com.mourinhan.identify_service.repositories;

import com.mourinhan.identify_service.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, String> {
}
