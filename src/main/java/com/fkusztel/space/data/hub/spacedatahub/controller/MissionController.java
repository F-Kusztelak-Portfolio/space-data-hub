package com.fkusztel.space.data.hub.spacedatahub.controller;

import com.fkusztel.space.data.hub.spacedatahub.entity.ImageType;
import com.fkusztel.space.data.hub.spacedatahub.entity.Mission;
import com.fkusztel.space.data.hub.spacedatahub.exception.MissionNotFoundException;
import com.fkusztel.space.data.hub.spacedatahub.service.MissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @author Filip.Kusztelak
 */
@Slf4j
@Controller
@RequestMapping(path = "mission")
public class MissionController {

    @Autowired
    MissionService missionService;

    //Create new mission and add it to database
    @PostMapping(path = "/create")
    public @ResponseBody
    Mission createMission(@RequestParam String missionName, @RequestParam ImageType imageryType,
                         @RequestParam String startDate, @RequestParam String endDate) {

        return missionService.missionCreate(missionName, imageryType,
                startDate, endDate);
    }

    //Find mission with given name
    @GetMapping(path = "/read")
    public @ResponseBody
    Mission readMissionByName(@RequestParam String name) throws MissionNotFoundException {
        log.info("readMissionByName {}", name);

        try {
            return missionService.findMissionByName(name).get();
        } catch(NoSuchElementException e) {
            throw new MissionNotFoundException();
        }
    }

    //Find all missions in database - [FOR DEVELOPMENT PURPOSE]
    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Mission> getAllMissions() {
        return missionService.findAll();
    }

    //Update mission with given name
    @PutMapping(path = "/update")
    public @ResponseBody
    String updateMission(@RequestParam String missionName, @RequestParam ImageType imageryType,
                         @RequestParam String startDate, @RequestParam String endDate) {

        log.info("updateMission: {}" , missionName);

        //Find mission by name and update details
        Optional<Mission> mission = missionService.findMissionByName(missionName);
        return missionService.updateMission(missionName, imageryType,
                startDate, endDate, mission);
    }

    //Delete mission with given name
    @DeleteMapping(path = "/delete")
    public @ResponseBody
    String deleteMission(@RequestParam String missionName) throws MissionNotFoundException{
        log.info("deleteMission with name: {}" , missionName);

        //Find mission by name and delete it
        try {
            Mission mission = missionService.findMissionByName(missionName).get();
            missionService.deleteMission(mission.getId());
            return "Mission: " + missionName + " deleted successfully";
        } catch(NoSuchElementException e) {
            throw new MissionNotFoundException();
        }
    }
}
