package com.fkusztel.space.data.hub.spacedatahub.service;

import com.fkusztel.space.data.hub.spacedatahub.entity.ImageType;
import com.fkusztel.space.data.hub.spacedatahub.entity.Mission;
import com.fkusztel.space.data.hub.spacedatahub.exception.MissionNotFoundException;

/**
 * @author Filip.Kusztelak
 */
public interface MissionService {

  void saveMission(Mission mission);

  Mission findMissionByName(String name) throws MissionNotFoundException;

  Iterable<Mission> findAll();

  void deleteMission(Long missionId) throws MissionNotFoundException;

  Mission missionCreate(
      String missionName, ImageType imageryType, String startDate, String endDate);

  String updateMission(String missionName, ImageType imageryType, String startDate, String endDate);
}
