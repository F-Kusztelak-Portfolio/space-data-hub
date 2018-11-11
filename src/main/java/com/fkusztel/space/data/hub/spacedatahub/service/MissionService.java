package com.fkusztel.space.data.hub.spacedatahub.service;

import com.fkusztel.space.data.hub.spacedatahub.entity.Mission;

import java.util.Optional;

/**
 * @author Filip.Kusztelak
 */
public interface MissionService {

    void saveMission (Mission mission);

    Optional<Mission> findMissionByName (String name);

    Iterable<Mission> findAll();

    void deleteMission(Long missionId);

    boolean checkImageType(String imageType);

    String missionCreate (String missionName, String imageryType,
                          String startDate, String endDate);

    String updateMission (String missionName, String imageryType,
                          String startDate, String endDate, Optional<Mission> mission);
}
