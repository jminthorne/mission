package com.rtn.dcgs.af.coalescence.bp.api;

import java.util.List;

import org.jboss.errai.jpa.sync.client.shared.DataSyncService;
import org.jboss.errai.jpa.sync.client.shared.JpaAttributeAccessor;
import org.jboss.errai.jpa.sync.client.shared.SyncRequestOperation;
import org.jboss.errai.jpa.sync.client.shared.SyncResponse;
import org.jboss.errai.jpa.sync.client.shared.SyncableDataSet;
import org.jboss.errai.jpa.sync.server.JavaReflectionAttributeAccessor;


/**
 * This is an interface for Business Process classes, providing some common methods that can be implemented by each
 * entity's Business Process class.
 * 
 * @author Red Hat Consulting @ 2014
 * 
 * @param <T>
 */
public interface Bp<T> {

	/**
	 * Persists the entity t, adding it to the database.
	 * 
	 * @param t
	 *            - the object to be persisted
	 * @return the managed entity
	 */
	public T create(T t);

	/**
	 * Given a database key id and the entity type to return, finds the entity in the database.
	 * 
	 * @param id
	 *            - the id to search by
	 * @param type
	 *            - the class type of the entity to be returned
	 * @return the entity found with the id, or null if it doesn't exist
	 */
	public T findById(Long id, Class<T> type);

	/**
	 * Deletes the entity, determined by the id from the database.
	 * 
	 * @param id
	 *            - the id of the entity to be deleted
	 * @param type
	 *            - the class type of the entity to be deleted
	 */
	public void delete(Long id, Class<T> type);

	/**
	 * Given a modified entity that exists on the database, updates the database with the changed entity.
	 * 
	 * @param t
	 *            - the entity to be updated
	 * @return the updated, managed entity
	 */
	public T update(T t);

	/**
	 * Given a type, the starting index, and the number to return, returns an unfiltered List<T> of entities in this
	 * database table.
	 * 
	 * @param type
	 *            - the class type of the entities to be returned
	 * @param startIndex
	 *            - the position of the first item to be returned
	 * @param quantity
	 *            - the number of items to return. If this is equal to 0, returns all items.
	 * @return A List<T> containing a number of elements determined by the quantity field.
	 */
	public List<T> findAll(Class<T> type, int startIndex, int quantity);

	public List<T> findAll(Class<T> type);

	/**
	 * Returns the number of items that will be found if a search with the given entity is done.
	 * 
	 * @param search
	 *            - the entity to be searched by
	 * @return The number of items that will be returned.
	 */
	public long resultQuantity(T search);

	/**
	 * Takes in an entity, and returns an instance of that entity fully hydrated. If any changes have been made the the
	 * entity, those changes will be persisted, and eventually will be flushed to the database. If the entity passed in
	 * is managed, the passed in object will be the one hydrated and returned.
	 * 
	 * @param toLoad
	 *            - the entity to load
	 * @return The hydrated entity.
	 */
	public T eagerLoad(T toLoad);

	/**
	 * Given a property name-value pair, find a list of model objects whose property with the given name meets criteria
	 * (to be determined by the concrete BP class) based on the provided value.
	 * 
	 * @param propertyName
	 *            - the name of the property to be searched on
	 * @param value
	 *            - the value that relates (in some way) to the value to be found in search results
	 * @return 
	 * 			- the entity
	 */
	List<T> findBy(String propertyName, String value);
	
	public <X> List<SyncResponse<X>> sync(SyncableDataSet<X> dataSet, List<SyncRequestOperation<X>> remoteResults);
	
}
