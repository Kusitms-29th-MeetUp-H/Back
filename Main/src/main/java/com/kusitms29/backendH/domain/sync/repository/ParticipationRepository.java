package com.kusitms29.backendH.domain.sync.repository;

import com.kusitms29.backendH.domain.sync.entity.Participation;
import com.kusitms29.backendH.domain.sync.entity.Sync;
import com.kusitms29.backendH.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    Optional<Participation> findByUserAndSync(User user, Sync sync);
    List<Participation> findBySync(Sync sync);
    @Query("SELECT COUNT(p) FROM Participation p WHERE p.sync.id = :syncId")
    int countBySyncId(@Param("syncId") Long syncId);
    List<Participation> findAllBySyncId(Long syncId);
    List<Participation> findAllByUserId(Long userId);
}
