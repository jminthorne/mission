package com.rtn.dcgs.af.coalescence.model.task;

import java.io.Serializable;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;

@Bindable
@Portable
public class CoalescenceTask implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String taskName;
	private String missionName;
	private String sceneName;
	private Integer lookNum;

	public CoalescenceTask() {
	}

	public CoalescenceTask(String name) {
		setTaskName(name);
	}

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
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * @param taskName
	 *            the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
	 * @return the missionName
	 */
	public String getMissionName() {
		return missionName;
	}

	/**
	 * @param missionName
	 *            the missionName to set
	 */
	public void setMissionName(String missionName) {
		this.missionName = missionName;
	}

	/**
	 * @return the sceneName
	 */
	public String getSceneName() {
		return sceneName;
	}

	/**
	 * @param sceneName
	 *            the sceneName to set
	 */
	public void setSceneName(String sceneName) {
		this.sceneName = sceneName;
	}

	/**
	 * @return the lookNum
	 */
	public Integer getLookNum() {
		return lookNum;
	}

	/**
	 * @param lookNum
	 *            the lookNum to set
	 */
	public void setLookNum(Integer lookNum) {
		this.lookNum = lookNum;
	}

}