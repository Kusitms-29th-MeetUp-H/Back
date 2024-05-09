package com.kusitms29.backendH.domain.sync.repository;

import com.kusitms29.backendH.domain.category.domain.Type;
import com.kusitms29.backendH.domain.sync.domain.Sync;
import com.kusitms29.backendH.domain.sync.domain.SyncType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    @Query("SELECT s FROM Sync s WHERE s.syncType = :syncType AND s.type = :type AND s.location = :location")
    Optional<List<Sync>> findAllBySyncTypeWithTypeWithLocation(@Param("syncType") SyncType syncType, @Param("type") Type type, @Param("location") String location);

    @Query("SELECT s FROM Sync s WHERE s.location = :location AND s.syncType = :syncType")
    Optional<List<Sync>> findAllByLocationAndSyncType(@Param("location") String location, @Param("syncType") SyncType syncType);

    @Query("SELECT s FROM Sync s WHERE s.location = :location AND s.type = :type")
    Optional<List<Sync>> findAllByLocationAndType(@Param("location") String location, @Param("type") Type type);

    @Query("SELECT s FROM Sync s WHERE s.syncType = :syncType AND s.type = :type")
    Optional<List<Sync>> findAllBySyncTypeAndType(@Param("syncType") SyncType syncType, @Param("type") Type type);

    @Query("SELECT s FROM Sync s WHERE s.location = :location")
    Optional<List<Sync>> findAllByLocation(@Param("location") String location);

    @Query("SELECT s FROM Sync s WHERE s.syncType = :syncType")
    Optional<List<Sync>> findAllBySyncType(@Param("syncType") SyncType syncType);

    @Query("SELECT s FROM Sync s WHERE s.type = :type")
    Optional<List<Sync>> findAllByType(@Param("type") Type type);
    @Query("SELECT s FROM Sync s WHERE s.associate IS NOT NULL AND s.associate <> '' ORDER BY s.date DESC")
    Optional<List<Sync>> findAllByAssociateIsExistOrderByDateDesc();
}
