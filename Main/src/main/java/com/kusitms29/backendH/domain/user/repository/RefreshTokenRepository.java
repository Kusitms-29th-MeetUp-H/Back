package com.kusitms29.backendH.domain.user.repository;


import com.kusitms29.backendH.domain.user.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
}

