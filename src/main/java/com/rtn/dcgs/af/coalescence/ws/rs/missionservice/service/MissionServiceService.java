package com.rtn.dcgs.af.coalescence.ws.rs.missionservice.service;

import javax.inject.Inject;

import com.rtn.dcgs.af.coalescence.bp.api.impl.MissionBp;
import com.rtn.dcgs.af.coalescence.model.mission.Mission;

public class MissionServiceService {

	@Inject
	MissionBp bp;

	public void createMission(String missionName) {
		System.out.println("\n\n\tMissionServiceService: createMission " + missionName);

		bp.create(missionName);

	}
	
	public Mission findMissionByName(String missionName){
		System.out.println("\n\n\tMissionServiceService: findMissionByName "+ missionName);
		
		return bp.findBy("name", missionName).get(0);
	}

}
