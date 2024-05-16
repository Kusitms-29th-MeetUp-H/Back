package com.kusitms29.backendH.domain.sync.service;

import com.kusitms29.backendH.domain.category.entity.Type;
import com.kusitms29.backendH.domain.sync.entity.Sync;
import com.kusitms29.backendH.domain.sync.entity.SyncType;
import com.kusitms29.backendH.domain.sync.repository.SyncRepository;
import com.kusitms29.backendH.global.error.ErrorCode;
import com.kusitms29.backendH.global.error.exception.EntityNotFoundException;
import com.kusitms29.backendH.global.error.exception.ListException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SyncReader {
    private final SyncRepository syncRepository;

//    public List<Sync> findAllByAssociateIsExist(SyncType syncType, Type type){
//        return syncRepository.findAllBySyncTypeAndTypeAndAssociateIsExistOrderByDateDesc(syncType, type).orElseThrow(()->new EntityNotFoundException(ErrorCode.SYNC_NOT_FOUND));
//    }
    public List<Sync> findAllByLocationAndDate(String location, LocalDateTime date){
        List<Sync> syncList = syncRepository.findAllByLocationAndDate(location,date);
        return ListException.throwIfEmpty(syncList, () -> new EntityNotFoundException(ErrorCode.SYNC_NOT_FOUND));
    }
    public List<Sync> findAllByAssociateIsExist(SyncType syncType, Type type) {
        Specification<Sync> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.isNotNull(root.get("associate")));
            predicates.add(criteriaBuilder.notEqual(root.get("associate"), ""));

            if (syncType != null) {
                predicates.add(criteriaBuilder.equal(root.get("syncType"), syncType));
            }

            if (type != null) {
                predicates.add(criteriaBuilder.equal(root.get("type"), type));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        List<Sync> syncList = syncRepository.findAll(spec, Sort.by(Sort.Direction.DESC, "date"));
        return ListException.throwIfEmpty(syncList, () -> new EntityNotFoundException(ErrorCode.SYNC_NOT_FOUND));
    }
    public List<Sync> findBySyncTypeWithTypesWithLocation(SyncType syncType, List<Type> types, String location) {
        List<Sync> syncList = new ArrayList<>();

        for (Type type : types) {
            try {
                List<Sync> sync3 = findAllBySyncTypeWithTypeWithLocation(syncType, type, location);
                for (Sync sync : sync3) {
                    if (!syncList.contains(sync)) {
                        syncList.add(sync);
                        if (syncList.size() >= 7) {
                            return syncList.subList(0, 7);
                        }
                    }
                }
                if (!sync3.isEmpty()) {
                    break;
                }
            } catch (RuntimeException e) {
                // 예외 처리 로직 추가 (예: 로깅)
                // 예외를 무시하고 계속 진행
            }
        }

        for (Type type : types) {
            try {
                List<Sync> sync2 = findAllByTwoCondition(syncType, type, location);
                for (Sync sync : sync2) {
                    if (!syncList.contains(sync)) {
                        syncList.add(sync);
                        if (syncList.size() >= 7) {
                            return syncList.subList(0, 7);
                        }
                    }
                }
                if (!sync2.isEmpty()) {
                    break;
                }
            } catch (RuntimeException e) {
                // 예외 처리 로직 추가 (예: 로깅)
                // 예외를 무시하고 계속 진행
            }
        }

        for (Type type : types) {
            try {
                List<Sync> sync1 = findAllByOneCondition(syncType, type, location);
                for (Sync sync : sync1) {
                    if (!syncList.contains(sync)) {
                        syncList.add(sync);
                        if (syncList.size() >= 7) {
                            return syncList.subList(0, 7);
                        }
                    }
                }
                if (!sync1.isEmpty()) {
                    break;
                }
            } catch (RuntimeException e) {
                // 예외 처리 로직 추가 (예: 로깅)
                // 예외를 무시하고 계속 진행
            }
        }

        return syncList;
    }
    public List<Sync> findAllBySyncTypeWithTypeWithLocation(SyncType syncType, Type type, String location){
        List<Sync> syncList = syncRepository.findAllBySyncTypeWithTypeWithLocation(syncType, type, location);
        return ListException.throwIfEmpty(syncList, () -> new EntityNotFoundException(ErrorCode.SYNC_NOT_FOUND));
    }
    private List<Sync> findAllByTwoCondition(SyncType syncType, Type type, String location) {
        List<Sync> syncList = new ArrayList<>();

        // location과 syncType이 일치하는 경우
        syncList.addAll(findAllByLocationAndSyncType(location, syncType));

        // location과 type이 일치하는 경우
        syncList.addAll(findAllByLocationAndType(location, type));

        // syncType과 type이 일치하는 경우
        syncList.addAll(findAllBySyncTypeAndType(syncType, type));

        return syncList;
    }
    public List<Sync> findAllByLocationAndSyncType(String location, SyncType syncType){
        List<Sync> syncList = syncRepository.findAllByLocationAndSyncType(location,syncType);
        return ListException.throwIfEmpty(syncList, () -> new EntityNotFoundException(ErrorCode.SYNC_NOT_FOUND));
    }
    public List<Sync> findAllByLocationAndType(String location, Type type){
        List<Sync> syncList = syncRepository.findAllByLocationAndType(location,type);
        return ListException.throwIfEmpty(syncList, () -> new EntityNotFoundException(ErrorCode.SYNC_NOT_FOUND));
    }

    private List<Sync> findAllByOneCondition(SyncType syncType, Type type, String location) {
        List<Sync> syncList = new ArrayList<>();

        // location만 일치하는 경우
        syncList.addAll(findAllByLocation(location));

        // syncType만 일치하는 경우
        syncList.addAll(findAllBySyncType(syncType));

        // type만 일치하는 경우
        syncList.addAll(findAllByType(type));

        return syncList;
    }
    public List<Sync> findAllByLocation(String location){
        List<Sync> syncList = syncRepository.findAllByLocation(location);
        return ListException.throwIfEmpty(syncList, () -> new EntityNotFoundException(ErrorCode.SYNC_NOT_FOUND));
    }
    public List<Sync> findAllBySyncType(SyncType syncType){
        List<Sync> syncList = syncRepository.findAllBySyncType(syncType);
        return ListException.throwIfEmpty(syncList, () -> new EntityNotFoundException(ErrorCode.SYNC_NOT_FOUND));
    }
    public List<Sync> findAllByType(Type type){
        List<Sync> syncList = syncRepository.findAllByType(type);
        return ListException.throwIfEmpty(syncList, () -> new EntityNotFoundException(ErrorCode.SYNC_NOT_FOUND));
    }
    public List<Sync> findAllBySyncTypeAndType(SyncType syncType, Type type){
        if(type==null)
            return findAllBySyncType(syncType);
        if(syncType==null)
            return findAllByType(type);
        List<Sync> syncList = syncRepository.findAllBySyncTypeAndType(syncType, type);
        return ListException.throwIfEmpty(syncList, () -> new EntityNotFoundException(ErrorCode.SYNC_NOT_FOUND));
    }
    public Sync findById(Long syncId){
        return syncRepository.findById(syncId).orElseThrow(() -> new EntityNotFoundException(ErrorCode.SYNC_NOT_FOUND));
    }
    public List<Sync> findAllByUserId(Long userId){
        List<Sync> syncList = syncRepository.findAllByUserId(userId);
        return ListException.throwIfEmpty(syncList, () -> new EntityNotFoundException(ErrorCode.SYNC_NOT_FOUND));
    }
}