package com.fkusztel.space.data.hub.spacedatahub.service;

import com.fkusztel.space.data.hub.spacedatahub.entity.ImageType;
import com.fkusztel.space.data.hub.spacedatahub.entity.Mission;
import com.fkusztel.space.data.hub.spacedatahub.entity.MissionRepository;
import com.fkusztel.space.data.hub.spacedatahub.exception.MissionNotFoundException;
import com.google.common.collect.Lists;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Filip.Kusztelak
 */
@Slf4j
@Service
public class MissionServiceImpl implements MissionService {

  @Autowired MissionRepository missionRepository;

  /**
   * Save mission in database
   *
   * @param mission Given mission entity.
   */
  @Override
  public void saveMission(Mission mission) {
    missionRepository.save(mission);
  }

  /**
   * Find mission with the specified values.
   *
   * @param name Name of the mission.
   * @exception MissionNotFoundException Given mission was not found in database
   */
  @Override
  public Mission findMissionByName(String name) throws MissionNotFoundException {

    // Get missions to list
    List<Mission> missionList = Lists.newArrayList(findAll());

    // Filter and get correct one
    Optional<Mission> result =
        missionList.stream().filter(mission -> mission.getName().equals(name)).findAny();

    return result.orElseThrow(MissionNotFoundException::new);
  }

  /** Find all missions in database - [FOR DEVELOPMENT PURPOSE] */
  @Override
  public Iterable<Mission> findAll() {
    return missionRepository.findAll();
  }

  /**
   * Delete mission from database with the specified values.
   *
   * @param missionId Id of the mission.
   * @exception MissionNotFoundException Given mission was not found in database
   */
  @Override
  public void deleteMission(Long missionId) throws MissionNotFoundException {
    try {
      missionRepository.deleteById(missionId);
    } catch (NoSuchElementException e) {
      throw new MissionNotFoundException();
    }
  }

  /**
   * Create a new Mission with the specified values.
   *
   * @param missionName Name of the new mission.
   * @param imageryType Image type take by this mission (PANCHROMATIC, MULTISPECTRAL, HYPERPECTRAL).
   * @param startDate Starting date of mission (2018-05-07).
   * @param endDate End date of mission (2018-08-04).
   */
  @Override
  public Mission missionCreate(
      String missionName, ImageType imageryType, String startDate, String endDate) {

    // Create mission from given parameters and save it to database
    Mission mission =
        Mission.builder()
            .name(missionName)
            .imageType(imageryType)
            .startDate(LocalDate.parse(startDate))
            .endDate(LocalDate.parse(endDate))
            .build();

    log.info("createMission: {}", mission.toString());
    saveMission(mission);
    return missionRepository.save(mission);
  }

  /**
   * Update a Mission if exists or creates a new one with the specified values.
   *
   * @param missionName Name of the new mission.
   * @param imageryType Image type take by this mission (PANCHROMATIC, MULTISPECTRAL, HYPERPECTRAL).
   * @param startDate Starting date of mission (2018-05-07).
   * @param endDate End date of mission (2018-08-04).
   */
  @Override
  public String updateMission(
      String missionName, ImageType imageryType, String startDate, String endDate) {

    Mission updated;
    try {
      updated = findMissionByName(missionName);
      updated.setName(missionName);
      updated.setImageType(imageryType);
      updated.setStartDate(LocalDate.parse(startDate));
      updated.setEndDate(LocalDate.parse(endDate));

      saveMission(updated);
      return updated.toString() + " updated successfully";
    } catch (MissionNotFoundException e) {
      Mission created =
          Mission.builder()
              .name(missionName)
              .imageType(imageryType)
              .startDate(LocalDate.parse(startDate))
              .endDate(LocalDate.parse(endDate))
              .build();

      saveMission(created);
      return "Mission with name "
          + missionName
          + " was absent and it was created: "
          + created.toString();
    }
  }
}
