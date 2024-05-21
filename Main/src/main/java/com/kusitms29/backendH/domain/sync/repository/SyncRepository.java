package com.kusitms29.backendH.domain.sync.repository;

import com.kusitms29.backendH.domain.category.entity.Type;
import com.kusitms29.backendH.domain.sync.entity.Sync;
import com.kusitms29.backendH.domain.sync.entity.SyncType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface SyncRepository extends JpaRepository<Sync, Long> {
    //모임이 하루 안으로 임박한 Sync의 정보 가져오기
    @Query(value = "SELECT u.user_id, u.user_name, s.sync_name, s.sync_id, s.sync_type, s.regular_day, s.regular_time, " +
            "CASE WHEN s.sync_type = 'LONGTIME' THEN s.routine_date ELSE s.date END AS effective_date " +
            "FROM sync s " +
            "INNER JOIN participation p ON s.sync_id = p.sync_id " +
            "INNER JOIN user u ON p.user_id = u.user_id " +
            "WHERE s.status != 'DELETED' " +
            "AND (CASE WHEN s.sync_type = 'LONGTIME' THEN s.routine_date ELSE s.date END) IS NOT NULL " +
            "AND DATE_SUB((CASE WHEN s.sync_type = 'LONGTIME' THEN s.routine_date ELSE s.date END), INTERVAL 1 DAY) <= :currentDate " +
            "AND (CASE WHEN s.sync_type = 'LONGTIME' THEN s.routine_date ELSE s.date END) >= :currentDate ",
            nativeQuery = true)
    List<Map<String, Object>> findHurrySyncInfo(LocalDateTime currentDate);
    @Query("SELECT s FROM Sync s WHERE s.syncType = :syncType AND s.type = :type AND s.location = :location ORDER BY s.date DESC")
    List<Sync> findAllBySyncTypeWithTypeWithLocation(@Param("syncType") SyncType syncType, @Param("type") Type type, @Param("location") String location);

    @Query("SELECT s FROM Sync s WHERE s.location = :location AND s.syncType = :syncType ORDER BY s.date DESC")
    List<Sync> findAllByLocationAndSyncType(@Param("location") String location, @Param("syncType") SyncType syncType);

    @Query("SELECT s FROM Sync s WHERE s.location = :location AND s.type = :type ORDER BY s.date DESC")
    List<Sync> findAllByLocationAndType(@Param("location") String location, @Param("type") Type type);

    @Query("SELECT s FROM Sync s WHERE s.syncType = :syncType AND s.type = :type ORDER BY s.date DESC")
    List<Sync> findAllBySyncTypeAndType(@Param("syncType") SyncType syncType, @Param("type") Type type);

    @Query("SELECT s FROM Sync s WHERE s.location = :location ORDER BY s.date DESC")
    List<Sync> findAllByLocation(@Param("location") String location);

    @Query("SELECT s FROM Sync s WHERE s.syncType = :syncType ORDER BY s.date DESC")
    List<Sync> findAllBySyncType(@Param("syncType") SyncType syncType);

    @Query("SELECT s FROM Sync s WHERE s.type = :type ORDER BY s.date DESC")
    List<Sync> findAllByType(@Param("type") Type type);
    @Query("SELECT s FROM Sync s WHERE s.associate IS NOT NULL AND s.associate <> '' ORDER BY s.date DESC")
    List<Sync> findAllByAssociateIsExistOrderByDateDesc(SyncType syncType, Type type);
    @Query("SELECT s FROM Sync s WHERE s.syncType = :syncType AND s.type = :type AND s.associate IS NOT NULL AND s.associate <> '' ORDER BY s.date DESC")
    List<Sync> findAllBySyncTypeAndTypeAndAssociateIsExistOrderByDateDesc(SyncType syncType, Type type);
    List<Sync> findAll(Specification<Sync> spec, Sort sort);
    List<Sync> findAllByLocationAndDate(String location, LocalDateTime date);
    List<Sync> findAllByUserId(Long userId);
}
