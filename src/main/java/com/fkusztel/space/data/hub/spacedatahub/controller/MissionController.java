package com.fkusztel.space.data.hub.spacedatahub.controller;

import com.fkusztel.space.data.hub.spacedatahub.entity.Mission;
import com.fkusztel.space.data.hub.spacedatahub.service.MissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * @author Filip.Kusztelak
 */
@Slf4j
@Controller
@RequestMapping(path = "mission")
public class MissionController {

    @Autowired
    MissionService missionService;

    @PostMapping(path = "/create")
    public @ResponseBody
    String createMission(@RequestParam String missionName, @RequestParam String imageryType,
                         @RequestParam String startDate, @RequestParam String endDate) {

        if (missionService.checkImageType(imageryType)){
        //Create mission from given parameters and save it to database
        Mission mission = Mission.builder()
                .name(missionName)
                .imageType(imageryType)
                .startDate(LocalDate.parse(startDate))
                .endDate(LocalDate.parse(endDate))
                .build();

        log.info("createMission: {}", mission.toString());
        missionService.saveMission(mission);
        return "Created " + mission.toString();
        }
        return "";
    }

    @GetMapping(path = "/read")
    public @ResponseBody
    Mission readMissionByName(@RequestParam String name) {
        log.info("readMissionByName {}", name);
        return missionService.findMissionByName(name).get();
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Mission> getAllMissions() {
        return missionService.findAll();
    }

    @PutMapping(path = "/update")
    public @ResponseBody
    String updateMission(@RequestParam String missionName, @RequestParam String imageryType,
                         @RequestParam String startDate, @RequestParam String endDate) {

        log.info("updateMission: {}" , missionName);

        //Find mission by name and update details
        Mission mission = missionService.findMissionByName(missionName).get();
        mission.setImageType(imageryType);
        mission.setStartDate(LocalDate.parse(startDate));
        mission.setEndDate(LocalDate.parse(endDate));

        missionService.saveMission(mission);
        return mission.toString() + " updated successfully";
    }

    @DeleteMapping(path = "/delete")
    public @ResponseBody
    String deleteMission(@RequestParam String missionName) {
        log.info("deleteMission with name: {}" , missionName);

        //Find mission by name and delete it
        Mission mission = missionService.findMissionByName(missionName).get();
        missionService.deleteMission(mission.getId());

        return "Mission: " + missionName + " deleted successfully";
    }
}
