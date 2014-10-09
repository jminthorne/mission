package com.rtn.dcgs.af.coalescence.persistence.api;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.jboss.errai.jpa.sync.client.shared.DataSyncService;
import org.jboss.errai.jpa.sync.client.shared.JpaAttributeAccessor;
import org.jboss.errai.jpa.sync.client.shared.SyncRequestOperation;
import org.jboss.errai.jpa.sync.client.shared.SyncResponse;
import org.jboss.errai.jpa.sync.client.shared.SyncableDataSet;
import org.jboss.errai.jpa.sync.server.JavaReflectionAttributeAccessor;

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
	
	
	  /**
	   * Passes a data sync operation on the given data set to the server-side of
	   * the Errai DataSync system.
	   * <p>
	   * This method is not invoked directly by the application code; it is called
	   * via Errai RPC by Errai's ClientSyncManager.
	   *
	   * @param dataSet
	   *          The data set to synchronize.
	   * @param remoteResults
	   *          The remote results produced by ClientSyncManager, which the
	   *          server-side needs to perform to synchronize the server data with
	   *          the client data.
	   * @return A list of sync response operations that ClientSyncManager needs to
	   *         perform to synchronize the client data with the server data.
	   */
	  public <X> List<SyncResponse<X>> sync(SyncableDataSet<X> dataSet, List<SyncRequestOperation<X>> remoteResults); 
}
