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

    @Override
    public void saveMission(Mission mission) {
        missionRepository.save(mission);
    }

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

    @Override
    public Iterable<Mission> findAll() {
        return missionRepository.findAll();
    }

    @Override
    public void deleteMission(Long missionId) {
        missionRepository.deleteById(missionId);
    }

    @Override
    public boolean checkImageType(String imageType) {
        //Check if imageType has proper value
        if (imageType.equals(Constants.ImageType.HYPERPECTRAL)
                || imageType.equals(Constants.ImageType.MULTISPECTRAL)
                || imageType.equals(Constants.ImageType.PANCHROMATIC)){
            return true;
        }
        return false;
    }
}
