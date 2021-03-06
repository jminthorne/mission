package com.rtn.dcgs.af.coalescence.model.mission;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.Constraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;

/**
*
*/
// jpa
@Entity
@Table(name = "MISSION")
@NamedQueries({ @NamedQuery(name = "allMission", query = "SELECT m FROM Mission m ORDER BY m.id") })
// errai
@Portable
@Bindable
public class Mission implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MISSION_ID")
	private Long id;

	@NotNull
	@Column(name = "MISSION_KEY", unique = true)
	private String businessKey;

	@NotNull(message = "SharedMission Name required.")
	@Size(min = 1, max = 500, message = "SharedMission Name is required and must be between 1 and 500 characters.")
	@Column(name = "MISSION_NAME", unique = true)
	private String name;

	@Size(min = 0, max = 500, message = "SharedMission Description is required and must be between 1 and 500 characters.")
	@Column(name = "MISSION_DSCR")
	private String description = "";

	@Column
	private String foo;

	@Column
	private boolean completed;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the businessKey
	 */
	public String getBusinessKey() {
		return businessKey;
	}

	/**
	 * @param businessKey
	 *            the businessKey to set
	 */
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the foo
	 */
	public String getFoo() {
		return foo;
	}

	/**
	 * @param foo
	 *            the foo to set
	 */
	public void setFoo(String foo) {
		this.foo = foo;
	}

	/**
	 * @return the completed
	 */
	public boolean isCompleted() {
		return completed;
	}

	/**
	 * @param completed
	 *            the completed to set
	 */
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

}
