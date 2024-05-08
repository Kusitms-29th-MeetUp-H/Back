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
            List<Sync> sync3 = syncRepository.findAllBySyncTypeWithTypeWithLocation(syncType, type, location);
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
    public List<Sync> findAllBySyncType(SyncType syncType){
        return syncRepository.findAllBySyncType(syncType);
    }

    private List<Sync> findAllByTwoCondition(SyncType syncType, Type type, String location) {
        List<Sync> syncList = new ArrayList<>();

        // location과 syncType이 일치하는 경우
        syncList.addAll(syncRepository.findAllByLocationAndSyncType(location, syncType));

        // location과 type이 일치하는 경우
        syncList.addAll(syncRepository.findAllByLocationAndType(location, type));

        // syncType과 type이 일치하는 경우
        syncList.addAll(syncRepository.findAllBySyncTypeAndType(syncType, type));

        return syncList;
    }

    private List<Sync> findAllByOneCondition(SyncType syncType, Type type, String location) {
        List<Sync> syncList = new ArrayList<>();

        // location만 일치하는 경우
        syncList.addAll(syncRepository.findAllByLocation(location));

        // syncType만 일치하는 경우
        syncList.addAll(syncRepository.findAllBySyncType(syncType));

        // type만 일치하는 경우
        syncList.addAll(syncRepository.findAllByType(type));

        return syncList;
    }
}