package com.mourinhan.identify_service.repositories;

import com.mourinhan.identify_service.entities.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
}
