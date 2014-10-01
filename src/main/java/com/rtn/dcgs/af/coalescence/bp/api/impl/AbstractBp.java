package com.rtn.dcgs.af.coalescence.bp.api.impl;

import java.util.List;

import javax.enterprise.event.Event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rtn.dcgs.af.coalescence.bp.api.Bp;
import com.rtn.dcgs.af.coalescence.persistence.api.Dao;

/**
 * @author Red Hat Consulting @ 2014
 * 
 */
public abstract class AbstractBp<ENTITYTYPE, DAOTYPE extends Dao<ENTITYTYPE>> implements Bp<ENTITYTYPE> {

	protected static Logger LOG = LoggerFactory.getLogger(AbstractBp.class);

	/**
	 * Initializes the AbstractBp superclass. Takes the concrete Dao instance that should be used.
	 * 
	 */
	protected DAOTYPE dao;
	protected Event<ENTITYTYPE> eventBus;

	/**
	 * Persists the entity t, adding it to the database.
	 * 
	 * @param t
	 *            - the object to be persisted
	 * @return the managed entity
	 */
	@Override
	public ENTITYTYPE create(ENTITYTYPE t) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("\n\n\tcreate() called with " + t);
		}
		ENTITYTYPE type = this.dao.create(t);
		//fireSaveEvent(type);
		return type;
	}

	/**
	 * Given a database key id, finds the entity in the database.
	 * 
	 * @param id
	 *            - the id to search by
	 * @return the entity found with the id, or null if it doesn't exist
	 */
	@Override
	public ENTITYTYPE findById(Long id, Class<ENTITYTYPE> type) {
		return this.dao.findById(id, type);
	}

	/**
	 * Given a modified entity that exists on the database, updates the database with the changed entity.
	 * 
	 * @param t
	 *            - the entity to be updated
	 * @return the updated, managed entity
	 */
	@Override
	public ENTITYTYPE update(ENTITYTYPE toUpdate) {
		ENTITYTYPE type = this.dao.update(toUpdate);
		fireSaveEvent(type);
		return type;
	}

	/**
	 * Deletes the entity, determined by the id from the database.
	 * 
	 * @param id
	 *            - the id of the entity to be deleted
	 */
	@Override
	public void delete(Long id, Class<ENTITYTYPE> type) {
		this.dao.delete(id, type);
	}

	/**
	 * Returns the number of items that will be found if a search with the given entity is done.
	 * 
	 * @param search
	 *            - the entity to be searched by
	 * @return The number of items that will be returned.
	 */
	public long resultQuantity(ENTITYTYPE search) {
		return 1;
		// return this.dao.resultQuantity(search);
	}

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
	@Override
	public List<ENTITYTYPE> findAll(Class<ENTITYTYPE> type, int startIndex, int quantity) {
		return this.dao.findAll(type, startIndex, quantity);
	}

	@Override
	public List<ENTITYTYPE> findAll(Class<ENTITYTYPE> type) {
		return this.dao.findAll(type);
	}

	@Override
	public ENTITYTYPE eagerLoad(ENTITYTYPE toLoad) {
		return null;
		// return this.dao.eagerLoad(toLoad);
	}

	protected void fireSaveEvent(ENTITYTYPE type) {
		//this.eventBus.select(new AnnotationLiteral<EntitySavedEvent>() {}).fire(type);
	}
}
