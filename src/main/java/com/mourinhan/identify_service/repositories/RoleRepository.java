package com.mourinhan.identify_service.repositories;

import com.mourinhan.identify_service.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<Role, String> {
}
