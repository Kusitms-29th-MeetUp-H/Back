package com.kusitms29.backendH.domain.sync.repository;

import com.kusitms29.backendH.domain.category.domain.Type;
import com.kusitms29.backendH.domain.sync.domain.Sync;
import com.kusitms29.backendH.domain.sync.domain.SyncType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface SyncRepository extends JpaRepository<Sync, Long> {
    //모임이 하루 안으로 임박한 Sync의 정보 가져오기
    @Query(value = "SELECT u.user_id, u.user_name, s.sync_name " +
            "FROM sync s " +
            "INNER JOIN participation p ON s.sync_id = p.sync_id " +
            "INNER JOIN user u ON p.user_id = u.user_id " +
            "INNER JOIN notification n ON u.user_id = n.user_id " +
            "WHERE s.status != 'DELETED' " +
            "AND n.notification_type = 'SYNC_REMINDER' " +
            "AND n.status = 'ACTIVE' " +
            "AND DATE_SUB(s.date, INTERVAL 1 DAY) <= :currentDate " +
            "AND s.date >= :currentDate ",
            nativeQuery = true)
    List<Map<String, Object>> findHurrySyncInfo(LocalDateTime currentDate);
    List<Sync> findAllBySyncTypeWithTypeWithLocation(SyncType syncType, Type type, String location);
}
