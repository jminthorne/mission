/**
 * 
 */
package com.rtn.dcgs.af.coalescence.bp.api.impl;

import java.util.List;
import java.util.UUID;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rtn.dcgs.af.coalescence.model.mission.Mission;
import com.rtn.dcgs.af.coalescence.persistence.api.impl.MissionDao;

/**
 * @author Red Hat Consulting @ 2014
 * 
 */

public class MissionBp extends AbstractBp<Mission, MissionDao> {

	private Logger LOG = LoggerFactory.getLogger(MissionBp.class);

	@Inject
	public void setDao(MissionDao dao) {
		this.dao = dao;
	}

	@Inject
	public void setEventBus(Event<Mission> event) {
		this.eventBus = event;
	}

	@Inject
	private Event<Mission> validationError;

	@Inject
	private Event<Mission> entitySaved;

	@Override
	public List<Mission> findBy(String propertyName, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	public Mission create(String missionName) {
		System.out.println("\n\n\tMissionBp create: " + missionName);
		Mission mission = new Mission();

		mission.setName(missionName);
		mission.setBusinessKey("testBussinessKey" + UUID.randomUUID());

		return create(mission);
	}

}
