package com.fkusztel.space.data.hub.spacedatahub.service;

import com.fkusztel.space.data.hub.spacedatahub.entity.ImageType;
import com.fkusztel.space.data.hub.spacedatahub.entity.Mission;
import com.fkusztel.space.data.hub.spacedatahub.entity.MissionRepository;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Filip.Kusztelak
 */
@Slf4j
@Service
public class MissionServiceImpl implements MissionService {

    @Autowired
    MissionRepository missionRepository;

    //Save mission to database
    @Override
    public void saveMission(Mission mission) {
        missionRepository.save(mission);
    }

    //Find mission by given name
    @Override
    public Optional<Mission> findMissionByName(String name) {

        //Get missions to list
        List<Mission> missionList = Lists.newArrayList(findAll());

        //Filter and get correct one
        Optional<Mission> result = missionList.stream()
                .filter(mission -> mission.getName().equals(name))
                .findAny();

        if (result.isPresent()){
            return result;
        }

        return Optional.empty();
    }

    //Find all Mission objects
    @Override
    public Iterable<Mission> findAll() {
        return missionRepository.findAll();
    }

    //Delete mission by ID
    @Override
    public void deleteMission(Long missionId) {
        missionRepository.deleteById(missionId);
    }

    //Create new mission and add it to database
    @Override
    public Mission missionCreate(String missionName, ImageType imageryType,
                                String startDate, String endDate) {

            //Create mission from given parameters and save it to database
            Mission mission = Mission.builder()
                    .name(missionName)
                    .imageType(imageryType)
                    .startDate(LocalDate.parse(startDate))
                    .endDate(LocalDate.parse(endDate))
                    .build();

            log.info("createMission: {}", mission.toString());
            saveMission(mission);
            return missionRepository.save(mission);
    }

    @Override
    public String updateMission(String missionName, ImageType imageryType,
                                String startDate, String endDate, Optional<Mission> mission) {

        Optional<Mission> result = findMissionByName(missionName);

        if (result.isPresent()) {
            Mission updated = result.get();
            updated.setName(missionName);
            updated.setImageType(imageryType);
            updated.setStartDate(LocalDate.parse(startDate));
            updated.setEndDate(LocalDate.parse(endDate));

            saveMission(updated);
            return updated.toString() + " updated successfully";
        } else {
            Mission created = Mission.builder()
                    .name(missionName)
                    .imageType(imageryType)
                    .startDate(LocalDate.parse(startDate))
                    .endDate(LocalDate.parse(endDate))
                    .build();

            saveMission(created);
            return "Mission with name " + missionName + " was absent and it was created: " + created.toString();
        }
    }
}
