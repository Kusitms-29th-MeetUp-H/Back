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
    public List<Sync> findByTypeWithInterestWithLocation(SyncType syncType, Type type, String location){
        List<Sync> syncList = new ArrayList<>();
        List<Sync> sync3 = syncRepository.findAllBySyncTypeWithTypeWithLocation(syncType,type,location);
        List<Sync> sync2 = syncRepository.findAllBySyncTypeWithTypeWithLocation(syncType,type,location);
        List<Sync> sync1 = syncRepository.findAllBySyncTypeWithTypeWithLocation(syncType,type,location);
        if(!sync3.isEmpty()){
            sync3.stream().map(sync -> syncList.add(sync));
        }
    }
}
