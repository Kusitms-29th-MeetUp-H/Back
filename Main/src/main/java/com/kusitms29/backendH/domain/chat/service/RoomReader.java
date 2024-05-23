package com.kusitms29.backendH.domain.chat.service;

import com.kusitms29.backendH.domain.chat.entity.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomReader {
    private final MongoTemplate mongoTemplate;
    public Room getRoomBySyncName(String syncName){
        Query query = new Query();
        query.addCriteria(Criteria.where("syncName").is(syncName));
        return mongoTemplate.findOne(query, Room.class);
    }
}
