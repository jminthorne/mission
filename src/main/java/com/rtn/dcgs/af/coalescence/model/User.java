package com.rtn.dcgs.af.coalescence.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;



@Entity
@Table(name="C_USER")
public class User {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "C_USER_ID")
	private Long id; 

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	private Set<SiteRole> siteRoles;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the siteRoles
	 */
	public Set<SiteRole> getSiteRoles() {
		return siteRoles;
	}

	/**
	 * @param siteRoles the siteRoles to set
	 */
	public void setSiteRoles(Set<SiteRole> siteRoles) {
		this.siteRoles = siteRoles;
	}
	
	
	
	
}
