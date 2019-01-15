package com.fkusztel.space.data.hub.spacedatahub.controller;

import com.fkusztel.space.data.hub.spacedatahub.entity.ImageType;
import com.fkusztel.space.data.hub.spacedatahub.entity.Mission;
import com.fkusztel.space.data.hub.spacedatahub.exception.MissionNotFoundException;
import com.fkusztel.space.data.hub.spacedatahub.service.MissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Filip.Kusztelak
 */
@Slf4j
@Controller
@RequestMapping(path = "mission")
public class MissionController {

  @Autowired MissionService missionService;

  /**
   * Create a new Mission with the specified values.
   *
   * @param missionName Name of the new mission.
   * @param imageryType Image type take by this mission (PANCHROMATIC, MULTISPECTRAL, HYPERPECTRAL).
   * @param startDate Starting date of mission (2018-05-07).
   * @param endDate End date of mission (2018-08-04).
   */
  @PostMapping(path = "/create")
  public @ResponseBody Mission createMission(
      @RequestParam String missionName,
      @RequestParam ImageType imageryType,
      @RequestParam String startDate,
      @RequestParam String endDate) {

    return missionService.missionCreate(missionName, imageryType, startDate, endDate);
  }

  /**
   * Find mission in database with the specified values.
   *
   * @param missionName Name of the mission.
   * @exception MissionNotFoundException Given mission was not found in database
   */
  @GetMapping(path = "/read")
  public @ResponseBody Mission readMissionByName(@RequestParam String missionName)
      throws MissionNotFoundException {
    log.info("readMissionByName {}", missionName);
    return missionService.findMissionByName(missionName);
  }

  /** Find all missions in database - [FOR DEVELOPMENT PURPOSE] */
  @GetMapping(path = "/all")
  public @ResponseBody Iterable<Mission> getAllMissions() {
    return missionService.findAll();
  }

  /**
   * Update a Mission if exists or creates a new one with the specified values.
   *
   * @param missionName Name of the new mission.
   * @param imageryType Image type take by this mission (PANCHROMATIC, MULTISPECTRAL, HYPERPECTRAL).
   * @param startDate Starting date of mission (2018-05-07).
   * @param endDate End date of mission (2018-08-04).
   */
  @PutMapping(path = "/update")
  public @ResponseBody String updateMission(
      @RequestParam String missionName,
      @RequestParam ImageType imageryType,
      @RequestParam String startDate,
      @RequestParam String endDate) {

    log.info("updateMission: {}", missionName);

    // Find mission by name and update details
    return missionService.updateMission(missionName, imageryType, startDate, endDate);
  }

  /**
   * Delete mission from database with the specified values.
   *
   * @param missionName Name of the mission.
   * @exception MissionNotFoundException Given mission was not found in database
   */
  @DeleteMapping(path = "/delete")
  public @ResponseBody String deleteMission(@RequestParam String missionName)
      throws MissionNotFoundException {
    log.info("deleteMission with name: {}", missionName);

    // Find mission by name and delete it
    Mission mission = missionService.findMissionByName(missionName);
    missionService.deleteMission(mission.getId());
    return "Mission: " + missionName + " deleted successfully";
  }
}
