package com.rtn.dcgs.af.coalescence.persistence.api.impl;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rtn.dcgs.af.coalescence.persistence.api.Dao;

public class AbstractDao<T> implements Dao<T> {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractDao.class);

	@PersistenceContext(unitName = "coalescence")
	protected EntityManager entityManager;

	public Session getSession() {
		return entityManager.unwrap(org.hibernate.Session.class);
	}

	public T create(T t) {
		entityManager.persist(t);
		entityManager.flush();
		entityManager.refresh(t);

		return t;
	}

	public T update(T t) {
		entityManager.merge(t);
		return t;
	}

	public T findById(Long id, Class<T> clazz) {
		return entityManager.find(clazz, id);
	}

	public void delete(Long id, Class<T> clazz) {
		T found = entityManager.find(clazz, id);
		entityManager.remove(found);
		entityManager.flush();
	}

	public List<T> findAll(Class<T> clazz) {
		return entityManager.createQuery("FROM " + clazz.getName()).getResultList();
	}

	@Override
	public List<T> findAll(Class<T> clazz, int start, int quantity) {
		Query query = entityManager.createQuery("FROM " + clazz.getName());
		setItemsReturned(query, start, quantity);
		List<T> toReturn = query.getResultList();
		Collections.sort(toReturn, new EntityComparator());
		return toReturn;
	}

	// @Override
	// public List<T> findByName(Class<T> clazz, String namePattern) {
	// // return entityManager.createQuery("FROM " + clazz.getName() +
	// " WHERE name = '" + name + "'").getResultList();
	// return entityManager.createNamedQuery("findByName").setParameter("name",
	// namePattern).getResultList();
	// }

	@Override
	public List<T> findBy(Criteria criteria) {
		// if (LOG.isDebugEnabled()) {
		// LOG.debug("clazz: " + clazz.toString() + "; queryName: " + queryName
		// + "; itemToSearchBy: "
		// + itemToSearchBy.toString());
		// }

		// return entityManager.createNamedQuery("findBy").setParameter("field",
		// queryName)
		// .setParameter("value", "%" + itemToSearchBy + "%").getResultList();
		return criteria.list();
		// return entityManager.createQuery(
		// "FROM " + clazz.getName() + " WHERE " + propertyName + " = '" +
		// itemToSearchBy + "'").getResultList();
	}

	public class EntityComparator implements Comparator<Object> {

		@Override
		public int compare(Object arg0, Object arg1) {
			// If both classes are strings, returns the comparison of the
			// strings
			if (arg0.getClass().equals(String.class) && arg1.getClass().equals(String.class)) {
				return ((String) arg0).compareToIgnoreCase((String) arg1);
			}
			try {
				// if the classes have a method "getName" that returns a String,
				// returns comparison of the names
				Method nameGetter = arg0.getClass().getMethod("getName", (Class<?>[]) null);
				String name0 = (String) nameGetter.invoke(arg0, (Object[]) null);
				String name1 = (String) nameGetter.invoke(arg1, (Object[]) null);
				return name0.compareToIgnoreCase(name1);
			} catch (Exception e1) {
				try {
					// if the classes have a method "getType", returns the
					// comparison of the types
					Method typeGetter = arg0.getClass().getMethod("getType", (Class<?>[]) null);
					Object type0 = typeGetter.invoke(arg0, (Object[]) null);
					Object type1 = typeGetter.invoke(arg1, (Object[]) null);
					return (new EntityComparator()).compare(type0, type1);
				} catch (Exception e2) {
					// otherwise, calls toString and compares the results.
					return (arg0.toString()).compareToIgnoreCase(arg1.toString());
				}
			}
		}

	}

	/**
	 * Used internally to set the number of items to return, using
	 * Query.setFirstResult and Query.setMaxResults.
	 * 
	 * @param query
	 * @param start
	 * @param quantity
	 */
	protected void setItemsReturned(Query query, int start, int quantity) {
		query.setFirstResult(start);
		if (quantity != 0) {
			query.setMaxResults(quantity);
		}
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}