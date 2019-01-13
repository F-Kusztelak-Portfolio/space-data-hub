package com.fkusztel.space.data.hub.spacedatahub.service;

import com.fkusztel.space.data.hub.spacedatahub.config.TestObjectFactory;
import com.fkusztel.space.data.hub.spacedatahub.entity.Mission;
import com.fkusztel.space.data.hub.spacedatahub.entity.MissionRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

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
		Mission mission = Mission.builder()
				.name(TestObjectFactory.MissionCreate.MISSION_NAME)
				.imageType(TestObjectFactory.MissionCreate.PROPER_IMAGE_TYPE)
				.startDate(LocalDate.parse(TestObjectFactory.MissionCreate.START_DATE))
				.endDate(LocalDate.parse(TestObjectFactory.MissionCreate.END_DATE))
				.build();

		Mockito.when(missionRepository.save(any(Mission.class))).thenReturn(mission);

		Mission result = missionService.missionCreate(
						TestObjectFactory.MissionCreate.MISSION_NAME,
						TestObjectFactory.MissionCreate.PROPER_IMAGE_TYPE,
						TestObjectFactory.MissionCreate.START_DATE,
						TestObjectFactory.MissionCreate.END_DATE);

		Assert.assertEquals(mission, result);
	}

	@Test
	public void missionCreate_WrongImageType() {
		Mission result = missionService.missionCreate(TestObjectFactory.MissionCreate.MISSION_NAME,
				TestObjectFactory.MissionCreate.WRONG_IMAGE_TYPE,
				TestObjectFactory.MissionCreate.START_DATE,
				TestObjectFactory.MissionCreate.END_DATE);

		String exceptedResult = "400 Bad Request";

		Assert.assertEquals(exceptedResult, result);
	}

	@Test
	public void missionUpdate_Success() {
		String result = missionService.updateMission(TestObjectFactory.MissionCreate.MISSION_NAME,
				TestObjectFactory.MissionCreate.PROPER_IMAGE_TYPE,
				TestObjectFactory.MissionCreate.START_DATE,
				TestObjectFactory.MissionCreate.END_DATE);

		Mission missionResult = Mission.builder()
				.name(TestObjectFactory.MissionCreate.MISSION_NAME)
				.imageType(TestObjectFactory.MissionCreate.PROPER_IMAGE_TYPE)
				.startDate(LocalDate.parse(TestObjectFactory.MissionCreate.START_DATE))
				.endDate(LocalDate.parse(TestObjectFactory.MissionCreate.END_DATE))
				.build();

		String exceptedResult = missionResult.toString() + " updated successfully";

		Assert.assertEquals(exceptedResult, result);
	}

	@Test
	public void missionUpdate_Fail() {
		String result = missionService.updateMission(TestObjectFactory.MissionCreate.MISSION_NAME,
				TestObjectFactory.MissionCreate.PROPER_IMAGE_TYPE,
				TestObjectFactory.MissionCreate.START_DATE,
				TestObjectFactory.MissionCreate.END_DATE);

		String exceptedResult = "Mission: " +
				TestObjectFactory.MissionCreate.MISSION_NAME +
				" update failed, no such mission available";

		Assert.assertEquals(exceptedResult, result);
	}
}
