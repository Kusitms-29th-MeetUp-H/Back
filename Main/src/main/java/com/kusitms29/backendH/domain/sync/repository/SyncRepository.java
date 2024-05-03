package com.kusitms29.backendH.domain.sync.repository;

import com.kusitms29.backendH.domain.sync.domain.Sync;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SyncRepository extends JpaRepository<Sync, Long> {

}
