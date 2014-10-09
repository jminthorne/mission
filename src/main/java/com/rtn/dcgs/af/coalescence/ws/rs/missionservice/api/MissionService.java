package com.rtn.dcgs.af.coalescence.ws.rs.missionservice.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.rtn.dcgs.af.coalescence.model.mission.Mission;

@Path(MissionService.PATH)
public interface MissionService {

	public static final String PATH = "/mission/";

	@GET
	@Path("/getAllMissions")
	@Produces({ "application/json" })
	List<Mission> getAllMissions();

	@GET
	@Path("/getAllMissions/{missionName}")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	Mission findMissionByName(@PathParam("missionName") String missionName);

	@PUT
	@PathParam("/createMission/{missionName}")
	@Consumes({ "application/json" })
	void createMissionByName(@PathParam("missionName") Mission mission);

	@PUT
	@PathParam("/createMission/{mission}")
	@Consumes({ "application/json" })
	void createMission(@PathParam("mission") Mission mission);
	

}
