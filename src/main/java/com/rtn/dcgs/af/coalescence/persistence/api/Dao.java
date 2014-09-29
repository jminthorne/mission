package com.rtn.dcgs.af.coalescence.persistence.api;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

public interface Dao<T> {
	public Session getSession();

	public T create(T t);

	public T findById(Long id, Class<T> clazz);

	public void delete(Long id, Class<T> clazz);

	public T update(T t);

	public List<T> findAll(Class<T> clazz);

	public List<T> findAll(Class<T> clazz, int start, int end);

	// List<T> findByName(Class<T> clazz, String name);

	<S> List<T> findBy(Criteria criteria);
}
