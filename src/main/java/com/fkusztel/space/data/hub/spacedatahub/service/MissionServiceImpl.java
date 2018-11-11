package com.fkusztel.space.data.hub.spacedatahub.service;

import com.fkusztel.space.data.hub.spacedatahub.config.Constants;
import com.fkusztel.space.data.hub.spacedatahub.entity.Mission;
import com.fkusztel.space.data.hub.spacedatahub.entity.MissionRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Filip.Kusztelak
 */
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
}
