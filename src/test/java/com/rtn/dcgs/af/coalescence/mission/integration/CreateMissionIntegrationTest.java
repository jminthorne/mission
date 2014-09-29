/**
 * 
 */
package com.rtn.dcgs.af.coalescence.mission.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.rtn.dcgs.af.coalescence.bp.api.Bp;
import com.rtn.dcgs.af.coalescence.bp.api.impl.AbstractBp;
import com.rtn.dcgs.af.coalescence.bp.api.impl.MissionBp;
import com.rtn.dcgs.af.coalescence.model.mission.Mission;
import com.rtn.dcgs.af.coalescence.persistence.api.Dao;
import com.rtn.dcgs.af.coalescence.persistence.api.impl.AbstractDao;
import com.rtn.dcgs.af.coalescence.persistence.api.impl.MissionDao;
import com.rtn.dcgs.af.coalescence.ws.rs.missionservice.api.MissionService;
import com.rtn.dcgs.af.coalescence.ws.rs.missionservice.impl.MissionServiceImpl;
import com.rtn.dcgs.af.coalescence.ws.rs.missionservice.service.MissionServiceService;

/**
 * @author Josh
 * 
 */

@RunWith(Arquillian.class)
public class CreateMissionIntegrationTest {

	@PersistenceContext(unitName = "coalescence")
	EntityManager em;

	@Inject
	UserTransaction utx;

	@Inject
	MissionBp bp;

	@Inject
	MissionDao missionDao;

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap
				.create(JavaArchive.class, "create-mission-integration-test.jar")
				.addClasses(MissionService.class, MissionServiceImpl.class, MissionServiceService.class, Mission.class,
						MissionBp.class, AbstractBp.class, Bp.class, MissionDao.class, AbstractDao.class, Dao.class)
				.addPackages(true, "org.apache.commons.beanutils", "org.apache.commons", "org.apache.log", "org.slf4j")
				.addPackages(true, MissionService.class.getPackage(), MissionServiceImpl.class.getPackage(),
						MissionServiceService.class.getPackage(), Mission.class.getPackage(),
						MissionDao.class.getPackage(), Dao.class.getPackage(), MissionBp.class.getPackage(),
						Bp.class.getPackage(), AbstractBp.class.getPackage()).addAsResource("META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Before
	public void setup() throws Exception {
		System.out.println("\n\n\tCreateMissionIntegrationTest: setup");
		utx.begin();
		em.joinTransaction();

		// clear the table between tests (findAll can't test without this)
		// cannot drop a table with a child table using only JPQL (i.e. DELETE)
		List<Mission> results = em.createQuery("FROM Mission").getResultList();
		for (Mission mission : results) {
			System.out.println("\n\n\ttotal mission " + results.size());
			System.out.println("\n\n\tremoving " + mission.getBusinessKey());
			em.remove(mission);
		}

		List<Mission> mResults = em.createQuery("FROM Mission").getResultList();
		for (Mission mission : mResults) {
			em.remove(mission);
		}
	}
	

	@Test
	public void integration_testCreateMission(@ArquillianResteasyResource("coalescence/rs") MissionService service) {
		System.out.println("\n\n\tCreateMissionIntegrationTest: integration_testCreateMission");
		// given
		String missionName = "test_mission";
		// when
		service.createMission(missionName);

		// find the Mission using JPQL
		@SuppressWarnings("unchecked")
		List<Mission> results = em.createQuery("FROM Mission where name = :name").setParameter("name", missionName)
				.getResultList();

		assertEquals("integration_testCreateMission: Wrong number of results found.", 1, results.size());
		assertEquals("The saved entity does not have the expected name", results.get(0).getName(), missionName);

		commit();
		
	}
	

	private Mission initMission() {
		Mission mission = new Mission();

		//mission.setName("Mission " + UUID.randomUUID());
		mission.setBusinessKey("testBussinessKey" + UUID.randomUUID());
		mission.setDescription("New Mission" + UUID.randomUUID());

		return mission;
	}

	private void commit() {
		try {
			utx.commit();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeuristicMixedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
