package com.kusitms29.backendH.domain.sync.domain.service;

import com.kusitms29.backendH.domain.category.domain.Type;
import com.kusitms29.backendH.domain.sync.domain.Sync;
import com.kusitms29.backendH.domain.sync.domain.SyncType;
import com.kusitms29.backendH.domain.sync.repository.SyncRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SyncReader {
    private final SyncRepository syncRepository;

    public List<Sync> findBySyncTypeWithTypesWithLocation(SyncType syncType, List<Type> types, String location) {
        List<Sync> syncList = new ArrayList<>();

        for (Type type : types) {
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
        }

        for (Type type : types) {
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
        }

        for (Type type : types) {
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
        }

        return syncList;
    }
    public List<Sync> findAllBySyncTypeWithTypeWithLocation(SyncType syncType, Type type, String location){
        return syncRepository.findAllBySyncTypeWithTypeWithLocation(syncType, type, location);
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
        return syncRepository.findAllByLocationAndSyncType(location,syncType);
    }
    public List<Sync> findAllByLocationAndType(String location, Type type){
        return syncRepository.findAllByLocationAndType(location,type);
    }
    public List<Sync> findAllBySyncTypeAndType(SyncType syncType, Type type){
        return syncRepository.findAllBySyncTypeAndType(syncType,type);
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
        return syncRepository.findAllByLocation(location);
    }
    public List<Sync> findAllBySyncType(SyncType syncType){
        return syncRepository.findAllBySyncType(syncType);
    }
    public List<Sync> findAllByType(Type type){
        return syncRepository.findAllByType(type);
    }
}