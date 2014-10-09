package com.rtn.dcgs.af.coalescence.ws.rs.missionservice.impl;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.runner.RunWith;

import com.rtn.dcgs.af.coalescence.model.mission.Mission;
import com.rtn.dcgs.af.coalescence.ws.rs.missionservice.api.MissionService;

//@RunWith(Arquillian.class)
public class MissionServiceTest {

	// @ArquillianResource
	// private URL deploymentUrl;

	 //@Inject
	// MissionService missionService;

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap
				.create(JavaArchive.class, "MissionServiceTest.jar")
				.addClasses(MissionService.class, MissionServiceImpl.class, Mission.class)
				.addPackages(true, "org.apache.commons.beanutils", "org.apache.commons", "org.apache.log", "org.slf4j")
				.addPackages(true, MissionService.class.getPackage(), MissionServiceImpl.class.getPackage(),
						Mission.class.getPackage()).addAsResource("META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@SuppressWarnings("unchecked")
	@Before
	public void setup() throws Exception {
		System.out.println("\n\n\tsetup");
		// utx.begin();
		// em.joinTransaction();
		//
		// // clear the table between tests (findAll can't test without this)
		// // cannot drop a table with a child table using only JPQL (i.e. DELETE)
		// List<SharedMission> results = em.createQuery("FROM SharedMission").getResultList();
		// for (SharedMission mission : results) {
		// System.out.println("\n\n\ttotal mission "+results.size());
		// System.out.println("\n\n\tremoving "+mission.getBusinessKey());
		// em.remove(mission);
		// }
		//
		// List<SharedMission> mResults = em.createQuery("FROM SharedMission").getResultList();
		// for (SharedMission mission : mResults) {
		// em.remove(mission);
		// }
	}

	//@Test
	public void testGetAllMissions(@ArquillianResteasyResource("coalescence/rs") MissionService service) {
		System.out.println("\n\n\ttestGetAllMissions");
		// given

		// when
		final List<Mission> result = service.getAllMissions();

		// then
		assertNotNull("The result list is null!!", result);
//		assertTrue("The list does not contain any missions", )

	}
	
	//@Test
	public void testCreateMission(@ArquillianResteasyResource("coalescence/rs") MissionService service){
		System.out.println("\n\n\ttestCreateMission");
		//given
		String missionName = "test_mission";
		//when
		//service.createMission(missionName);
	}
	
	//@Test
	public void testGetMissionByName(@ArquillianResteasyResource("coalescence/rs") MissionService service) {
		System.out.println("\n\n\ttestGetMissionByName");
		// given
		String missionName = "test_mission";
		// when
		final Mission mission = service.findMissionByName(missionName);

		// then
		assertNotNull("The mission is null!!", mission);

	}

}
