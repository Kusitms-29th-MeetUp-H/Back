package com.kusitms29.backendH.domain.sync.domain.service;

import com.kusitms29.backendH.domain.participation.domain.Participation;
import com.kusitms29.backendH.domain.sync.application.controller.dto.response.GraphElement;
import com.kusitms29.backendH.domain.sync.application.controller.dto.response.SyncGraphResponseDto;
import com.kusitms29.backendH.domain.sync.domain.Gender;
import com.kusitms29.backendH.domain.user.domain.User;
import com.kusitms29.backendH.domain.user.domain.service.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SyncManager {
    private final UserReader userReader;
    public SyncGraphResponseDto createGraphElementList(List<Participation> participationList, String graph){
        List<User> users = participationList.stream().map( participation -> userReader.findByUserId(participation.getUser().getId())).toList();
        if(graph.equals("participate"))
            return participateGraph(users);
        else if(graph.equals("gender"))
            return genderGraph(users);
        else if(graph.equals("university"))
            return universityGraph(users);
        else
            return nationalGraph(users);
    }
    private SyncGraphResponseDto participateGraph(List<User> users){

    }
    private SyncGraphResponseDto nationalGraph(List<User> users) {
        int totalUsers = users.size();
        int koreanCount = 0;
        int foreignerCount = 0;

        for (User user : users) {
            if (user.getNationality().equals("한국")) {
                koreanCount++;
            } else {
                foreignerCount++;
            }
        }

        double koreanPercent = (double) koreanCount / totalUsers * 100;
        double foreignerPercent = (double) foreignerCount / totalUsers * 100;

        List<GraphElement> graphElements = new ArrayList<>();
        graphElements.add(GraphElement.of("내국인", (int) Math.round(koreanPercent)));
        graphElements.add(GraphElement.of("외국인", (int) Math.round(foreignerPercent)));

        String status;
        if (koreanPercent < foreignerPercent) {
            status = "내국인에 비해 외국인의 비율이 더 높은 편이에요";
        } else if (koreanPercent > foreignerPercent) {
            status = "외국인에 비해 내국인의 비율이 더 높은 편이에요";
        } else {
            status = "내국인과 외국인의 비율이 동일해요";
        }

        return SyncGraphResponseDto.of(graphElements, status);
    }
    private SyncGraphResponseDto genderGraph(List<User> users) {
        int totalUsers = users.size();
        int manCount = 0;
        int womanCount = 0;
        int secretCount = 0;

        for (User user : users) {
            Gender gender = user.getGender();
            if (gender == Gender.MAN) {
                manCount++;
            } else if (gender == Gender.WOMAN) {
                womanCount++;
            } else if (gender == Gender.SECRET) {
                secretCount++;
            }
        }

        double manPercent = (double) manCount / totalUsers * 100;
        double womanPercent = (double) womanCount / totalUsers * 100;
        double secretPercent = (double) secretCount / totalUsers * 100;

        List<GraphElement> graphElements = new ArrayList<>();
        graphElements.add(GraphElement.of("남성", (int) Math.round(manPercent)));
        graphElements.add(GraphElement.of("여성", (int) Math.round(womanPercent)));
        graphElements.add(GraphElement.of("비공개", (int) Math.round(secretPercent)));

        String status = getHighestParticipationStatus(graphElements);

        return SyncGraphResponseDto.of(graphElements, status);
    }

    private SyncGraphResponseDto universityGraph(List<User> users) {
        Map<String, Integer> universityCountMap = new HashMap<>();

        for (User user : users) {
            String university = user.getUniversity();
            if (university != null) {
                universityCountMap.put(university, universityCountMap.getOrDefault(university, 0) + 1);
            }
        }

        int totalUsers = users.size();
        List<GraphElement> graphElements = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : universityCountMap.entrySet()) {
            String university = entry.getKey();
            int count = entry.getValue();
            double percent = (double) count / totalUsers * 100;
            graphElements.add(GraphElement.of(university, (int) Math.round(percent)));
        }

        String status = getHighestParticipationStatus(graphElements);

        return SyncGraphResponseDto.of(graphElements, status);
    }

    private String getHighestParticipationStatus(List<GraphElement> graphElements) {
        GraphElement highestElement = graphElements.stream()
                .max(Comparator.comparingInt(GraphElement::getPercent))
                .orElse(null);

        if (highestElement != null) {
            return highestElement.getName() + "의 참여율이 가장 높은 편이에요.";
        } else {
            return "참여자가 없습니다.";
        }
    }
}
