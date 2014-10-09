package com.rtn.dcgs.af.coalescence.ws.rs.missionservice.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.rtn.dcgs.af.coalescence.model.mission.Mission;
import com.rtn.dcgs.af.coalescence.ws.rs.missionservice.api.MissionService;
import com.rtn.dcgs.af.coalescence.ws.rs.missionservice.service.MissionServiceService;

public class MissionServiceImpl implements MissionService {

	@Inject
	MissionServiceService service;

	public List<Mission> getAllMissions() {
		System.out.println("\n\n\tgetting the missions!");

		List<Mission> missions = new ArrayList<>();

		return missions;
	}

	public Mission findMissionByName(String missionName) {
		System.out.println("\n\n\tgetMissionByName " + missionName);
		return null;
	}

	public void createMission(String missionName) {
		System.out.println("\n\n\tMissionServiceImpl: createMission " + missionName);

		service.createMission(missionName);
	}

	@Override
	public void createMissionByName(Mission mission) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createMission(Mission mission) {
		// TODO Auto-generated method stub

	}

}
