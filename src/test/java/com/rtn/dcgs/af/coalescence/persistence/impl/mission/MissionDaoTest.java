package com.rtn.dcgs.af.coalescence.persistence.impl.mission;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.rtn.dcgs.af.coalescence.model.mission.Mission;
import com.rtn.dcgs.af.coalescence.persistence.api.Dao;
import com.rtn.dcgs.af.coalescence.persistence.api.impl.AbstractDao;
import com.rtn.dcgs.af.coalescence.persistence.api.impl.MissionDao;

@RunWith(Arquillian.class)
public class MissionDaoTest {

	@PersistenceContext(unitName = "coalescence")
	EntityManager em;

	@Inject
	UserTransaction utx;

	@Inject
	MissionDao missionDao;

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class, "MissionServiceTest.jar")
				.addClasses(MissionDao.class, AbstractDao.class, Dao.class)
				.addPackages(true, "org.apache.commons.beanutils", "org.apache.commons", "org.apache.log", "org.slf4j")
				.addPackages(true, "com.rtn.dcgs.af.coalescence").addAsResource("META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@SuppressWarnings("unchecked")
	@Before
	public void setup() throws Exception {
		System.out.println("\n\n\tsetup");
		utx.begin();
		em.joinTransaction();

		// clear the table between tests (findAll can't test without this)
		// cannot drop a table with a child table using only JPQL (i.e. DELETE)
		List<Mission> results = em.createQuery("FROM Mission").getResultList();
		for (Mission mission : results) {
			System.out.println("\n\n\ttotal mission "+results.size());
			System.out.println("\n\n\tremoving "+mission.getBusinessKey());
			em.remove(mission);
		}

		List<Mission> mResults = em.createQuery("FROM Mission").getResultList();
		for (Mission mission : mResults) {
			em.remove(mission);
		}
	}

	@Test
	public void testCreateNewMission() {
		System.out.println("\n\nttestcreate");
		Mission mission = initMission();

		assertNotNull("The Mission Dao was not injected", missionDao);

		// missionDao.setEntityManager(em);
		Mission savedMission = missionDao.create(mission);

		//assertEquals("The Missions are not equal", mission, savedMission);

		assertTrue("The Mission was not persisted properly.", savedMission.getId() > 0);

		// find the Mission using JPQL
		List<Mission> results = em.createQuery("FROM Mission where id = :id").setParameter("id", savedMission.getId())
				.getResultList();

		assertEquals("Wrong number of results found.", 1, results.size());
		assertEquals("Mission was not persisted properly.", mission, results.get(0));

		commit();
	}

	@SuppressWarnings("unchecked")
	// @Test
	public void testDelete() {

		Mission mission = initMission();

		assertNotNull("The Mission Dao was not injected", missionDao);

		missionDao.setEntityManager(em);

		em.persist(mission);
		em.flush();

		assertNotNull("The Mission ID is null", mission.getId());

		// find our Mission using JPQL
		List<Mission> results = em.createQuery("FROM Mission where id = :id").setParameter("id", mission.getId())
				.getResultList();

		assertEquals("Wrong number of results found.", 1, results.size());
		assertEquals("Mission was not persisted properly.", mission, results.get(0));

		// delete the Mission
		missionDao.delete(mission.getId(), Mission.class);

		Long previousId = mission.getId();

		// (don't) find our Mission using JPQL
		results = em.createQuery("FROM Mission where id = :id").setParameter("id", previousId).getResultList();

		assertEquals("Wrong number of results found.", 0, results.size());

		commit();
	}

	@SuppressWarnings("unchecked")
	// @Test
	public void testUpdate() {
		/*
		 * TODO: test modification of the following fields: pollingSchedule sourceResource targetResource
		 * fntNotification pipeline
		 */

		Mission mission = initMission();

		assertNotNull("The Mission Dao was not injected", missionDao);

		missionDao.setEntityManager(em);

		em.persist(mission);
		em.flush();

		assertNotNull("The Mission ID is null", mission.getId());

		// find our Mission using JPQL
		List<Mission> results = em.createQuery("FROM Mission where id = :id").setParameter("id", mission.getId())
				.getResultList();

		assertEquals("Wrong number of results found.", 1, results.size());
		assertEquals("Mission was not persisted properly.", mission, results.get(0));

		// set updated values
		// TODO: test for updated ScheduleWindow
		mission.setName("2Test Mission" + System.currentTimeMillis());
		mission.setDescription("2Test Mission Description" + System.currentTimeMillis());

		// update changes using DAO
		missionDao.update(mission);

		// find our Mission using JPQL
		results = em.createQuery("FROM Mission where id = :id AND name = :name AND description = :description")
				.setParameter("id", mission.getId()).setParameter("name", mission.getName())
				.setParameter("description", mission.getDescription()).getResultList();

		assertEquals("More than one result found.", 1, results.size());
		assertEquals("Updated Mission was not persisted properly: id property incorrect.", mission.getId(), results
				.get(0).getId());
		assertEquals("Updated Mission was not persisted properly: name property incorrect.", mission.getName(), results
				.get(0).getName());
		assertEquals("Updated Mission was not persisted properly: description property incorrect.",
				mission.getDescription(), results.get(0).getDescription());

		commit();
	}

	@SuppressWarnings("unchecked")
	// @Test
	public void testFindById() {
		Mission mission = initMission();

		assertNotNull("The Mission Dao was not injected", missionDao);

		missionDao.setEntityManager(em);

		em.persist(mission);
		em.flush();

		assertNotNull("The Mission ID is null", mission.getId());

		// find our Mission using JPQL
		List<Mission> results = em.createQuery("FROM Mission where id = :id").setParameter("id", mission.getId())
				.getResultList();
		assertEquals("Wrong number of results found.", 1, results.size());
		assertEquals("The Mission was not persisted properly: entity manager did not get proper Mission.", mission,
				results.get(0));

		assertEquals("Incorrect result from findById().", results.get(0),
				missionDao.findById(mission.getId(), Mission.class));

		commit();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindAll() {
		Mission mission = initMission();

		Mission mission2 = initMission();

		Mission mission3 = initMission();

		// set up DAO
		assertNotNull("The Mission Dao was not injected", missionDao);
		missionDao.setEntityManager(em);

		// persist test jobs (without DAO)
		em.persist(mission);
		em.persist(mission2);
		em.persist(mission3);
		em.flush();

		assertNotNull("The Mission ID is null", mission.getId());
		assertNotNull("The Mission2 ID is null", mission2.getId());
		assertNotNull("The Mission3 ID is null", mission3.getId());

		// find our jobs using JPQL (sanity check; still no DAO)
		List<Mission> expectedResults = em.createQuery("FROM Mission").getResultList();
		assertEquals("Wrong number of results found.", 3, expectedResults.size());
		assertEquals("The Mission was not persisted properly: entity manager did not get proper Mission.", mission,
				expectedResults.get(0));
		assertEquals("The Mission2 was not persisted properly: entity manager did not get proper Mission.", mission2,
				expectedResults.get(1));
		assertEquals("The Mission3 was not persisted properly: entity manager did not get proper Mission.", mission3,
				expectedResults.get(2));

		// findAll using our DAO
		List<Mission> actualResults = missionDao.findAll(Mission.class);

		// check the results from our DAO
		assertEquals("Wrong number of results found.", 3, actualResults.size());
		for (int i = 0; i < expectedResults.size(); i++) {
			assertEquals("Incorrect result from findAll(): incorrect mission #" + i + 1, expectedResults.get(i),
					actualResults.get(i));
		}

		commit();
	}

	private Mission initMission() {
		Mission mission = new Mission();

		mission.setName("Mission " + UUID.randomUUID());
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
