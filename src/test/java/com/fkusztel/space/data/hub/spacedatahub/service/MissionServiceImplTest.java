package com.fkusztel.space.data.hub.spacedatahub.service;

import com.fkusztel.space.data.hub.spacedatahub.config.TestObjectFactory;
import com.fkusztel.space.data.hub.spacedatahub.entity.ImageType;
import com.fkusztel.space.data.hub.spacedatahub.entity.Mission;
import com.fkusztel.space.data.hub.spacedatahub.entity.MissionRepository;
import com.fkusztel.space.data.hub.spacedatahub.exception.MissionNotFoundException;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

/**
 * @author Filip.Kusztelak
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MissionServiceImplTest.class, MissionServiceImpl.class})
public class MissionServiceImplTest {

	@Autowired
	private MissionServiceImpl missionService;

	@MockBean
	private MissionRepository missionRepository;

	@Test
	public void missionCreate_ProperImageType() {
		Mission mission = TestObjectFactory.NewMission.mission;

		Mockito.when(missionRepository.save(any(Mission.class))).thenReturn(mission);

		Mission result = missionService.missionCreate(
						TestObjectFactory.MissionCreate.MISSION_NAME,
						TestObjectFactory.MissionCreate.PROPER_IMAGE_TYPE,
						TestObjectFactory.MissionCreate.START_DATE,
						TestObjectFactory.MissionCreate.END_DATE);

		Assert.assertEquals(mission, result);
	}

	@Test(expected = IllegalArgumentException.class )
	public void missionCreate_WrongImageType() {
		Mission mission = missionService.missionCreate(TestObjectFactory.MissionCreate.MISSION_NAME,
				ImageType.valueOf("WRONG"),
				TestObjectFactory.MissionCreate.START_DATE,
				TestObjectFactory.MissionCreate.END_DATE);

		Mockito.when(missionRepository.save(any(Mission.class))).thenReturn(mission);

		Mission result = missionService.missionCreate(
				TestObjectFactory.MissionCreate.MISSION_NAME,
				TestObjectFactory.MissionCreate.PROPER_IMAGE_TYPE,
				TestObjectFactory.MissionCreate.START_DATE,
				TestObjectFactory.MissionCreate.END_DATE);

		Assert.assertEquals(mission, result);
	}

	@Test
	public void missionUpdate_Success() {
		Mission missionResult = TestObjectFactory.NewMission.mission;

		List<Mission> missionList = Lists.newArrayList(missionResult);

		Mockito.when(missionRepository.findAll()).thenReturn(missionList);

		Mockito.when(missionRepository.save(any(Mission.class))).thenReturn(missionList.get(0));

		String result = missionService.updateMission(
				TestObjectFactory.MissionCreate.MISSION_NAME,
				TestObjectFactory.MissionCreate.PROPER_IMAGE_TYPE,
				TestObjectFactory.MissionCreate.START_DATE,
				TestObjectFactory.MissionCreate.END_DATE);

		String exceptedResult = missionResult.toString() + " updated successfully";

		Assert.assertEquals(exceptedResult, result);
	}

	@Test
	public void missionUpdateFailed_createNewMission() {
		Mission mission = TestObjectFactory.NewMission.mission;

		List<Mission> missionList = Lists.newArrayList();

		Mockito.when(missionRepository.findAll()).thenReturn(missionList);

		String result = missionService.updateMission(
				TestObjectFactory.MissionCreate.MISSION_NAME,
				TestObjectFactory.MissionCreate.PROPER_IMAGE_TYPE,
				TestObjectFactory.MissionCreate.START_DATE,
				TestObjectFactory.MissionCreate.END_DATE);

		String exceptedResult = "Mission with name "
				+ TestObjectFactory.MissionCreate.MISSION_NAME
				+ " was absent and it was created: " + mission.toString();

		Assert.assertEquals(exceptedResult, result);
	}

	@Test
	public void findAllMissions_success() {
		Mission mission = TestObjectFactory.NewMission.mission;
		Mission differentMission = TestObjectFactory.NewMission.differentMission;
		List<Mission> missionListExcepted = new ArrayList<>(Lists.newArrayList(mission));
		missionListExcepted.add(differentMission);

		Mockito.when(missionRepository.findAll()).thenReturn(missionListExcepted);

		List<Mission> missionListResult = (List<Mission>)missionService.findAll();

		Assert.assertEquals(missionListExcepted, missionListResult);
	}

	@Test
	public void findAllMissions_emptyList() {
		List<Mission> missionListExcepted = Lists.newArrayList();

		Mockito.when(missionRepository.findAll()).thenReturn(missionListExcepted);

		List<Mission> missionListResult = (List<Mission>)missionService.findAll();

		Assert.assertEquals(missionListExcepted, missionListResult);
	}

	@Test
	public void findMissionByName_success() throws MissionNotFoundException {
		Mission mission = TestObjectFactory.NewMission.mission;
		Mission missionExcepted = TestObjectFactory.NewMission.differentMission;
		List<Mission> missionList = new ArrayList<>(Lists.newArrayList(mission));
		missionList.add(missionExcepted);

		Mockito.when(missionRepository.findAll()).thenReturn(missionList);

		Mission result = missionService.findMissionByName(TestObjectFactory.MissionCreate.DIFFERENT_MISSION_NAME);

		Assert.assertEquals(missionExcepted, result);
	}

	@Test(expected = MissionNotFoundException.class)
	public void findMissionByName_exception() throws MissionNotFoundException {
		List<Mission> missionListExcepted = Lists.newArrayList();

		Mockito.when(missionRepository.findAll()).thenReturn(missionListExcepted);

		missionService.findMissionByName(TestObjectFactory.MissionCreate.DIFFERENT_MISSION_NAME);
	}
}
