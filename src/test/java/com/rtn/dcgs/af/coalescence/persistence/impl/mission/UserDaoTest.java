package com.rtn.dcgs.af.coalescence.persistence.impl.mission;

import java.util.HashSet;
import java.util.Set;

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

import com.rtn.dcgs.af.coalescence.model.Role;
import com.rtn.dcgs.af.coalescence.model.Site;
import com.rtn.dcgs.af.coalescence.model.SiteRole;
import com.rtn.dcgs.af.coalescence.model.User;
import com.rtn.dcgs.af.coalescence.persistence.api.Dao;
import com.rtn.dcgs.af.coalescence.persistence.api.impl.AbstractDao;
import com.rtn.dcgs.af.coalescence.persistence.api.impl.UserDao;

@RunWith(Arquillian.class)
public class UserDaoTest {

	@PersistenceContext(unitName = "coalescence")
	EntityManager em;

	@Inject
	UserTransaction utx;

	@Inject
	UserDao dao;

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class, "user-dao-test.jar")
				.addClasses(UserDao.class, AbstractDao.class, Dao.class)
				.addPackages(true, "org.apache.commons.beanutils", "org.apache.commons", "org.apache.log", "org.slf4j")
				.addPackages(true, "com.rtn.dcgs.af.coalescence").addAsResource("META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Before
	public void setup() throws Exception {
		System.out.println("\n\n\tsetup");
		utx.begin();
		em.joinTransaction();
	}

	@Test
	public void testCreateSiteRoleForUser() {
		System.out.println("\n\nttestcreate");

		User user = new User();

		Set<SiteRole> siteRoles = new HashSet<>();
		
		for( int i = 0; i < 4 ; i++){
			siteRoles.add(new SiteRole(new Role(), new Site()));
		}

		user.setSiteRoles(siteRoles);
		
		dao.create(user);

		commit();
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
