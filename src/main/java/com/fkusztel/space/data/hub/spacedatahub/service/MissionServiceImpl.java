package com.fkusztel.space.data.hub.spacedatahub.service;

import com.fkusztel.space.data.hub.spacedatahub.config.Constants;
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

    //Check if imageType has proper value
    @Override
    public boolean checkImageType(String imageType) {
        if (imageType.equalsIgnoreCase(Constants.ImageType.HYPERPECTRAL)
                || imageType.equalsIgnoreCase(Constants.ImageType.MULTISPECTRAL)
                || imageType.equalsIgnoreCase(Constants.ImageType.PANCHROMATIC)){
            return true;
        }
        return false;
    }

    //Create new mission and add it to database
    @Override
    public String missionCreate(String missionName, String imageryType,
                                String startDate, String endDate) {

        if (checkImageType(imageryType)){
            //Create mission from given parameters and save it to database
            Mission mission = Mission.builder()
                    .name(missionName)
                    .imageType(imageryType)
                    .startDate(LocalDate.parse(startDate))
                    .endDate(LocalDate.parse(endDate))
                    .build();

            log.info("createMission: {}", mission.toString());
            saveMission(mission);
            return "Created " + mission.toString();
        }
        return "400 Bad Request";
    }

    @Override
    public String updateMission(String missionName, String imageryType,
                                String startDate, String endDate, Optional<Mission> mission) {

        if (mission.isPresent()) {
            Mission result = mission.get();
            result.setImageType(imageryType);
            result.setStartDate(LocalDate.parse(startDate));
            result.setEndDate(LocalDate.parse(endDate));

            saveMission(result);
            return result.toString() + " updated successfully";
        }
        return "Mission: " + missionName + " update failed, no such mission available";
    }
}
