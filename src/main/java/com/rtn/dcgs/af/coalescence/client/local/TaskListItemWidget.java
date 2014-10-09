package com.rtn.dcgs.af.coalescence.client.local;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.jboss.errai.databinding.client.api.DataBinder;
import org.jboss.errai.ui.client.widget.HasModel;
import org.jboss.errai.ui.shared.api.annotations.AutoBound;
import org.jboss.errai.ui.shared.api.annotations.Bound;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.github.gwtbootstrap.client.ui.Column;
import com.github.gwtbootstrap.client.ui.Label;
import com.github.gwtbootstrap.client.ui.Row;
import com.google.gwt.user.client.ui.Composite;
import com.rtn.dcgs.af.coalescence.model.task.CoalescenceTask;

@Templated("TaskListItemWidget.html#task-item")
public class TaskListItemWidget extends Composite implements HasModel<CoalescenceTask> {

	@Inject
	@AutoBound
	private DataBinder<CoalescenceTask> task;

	@Bound
	private final Label taskName = new Label();

	@Bound
	private final Label missionName = new Label();

	@Bound
	private final Label sceneName = new Label();

	@Bound
	private final Label lookNum = new Label();

	@Inject
	@DataField("task-item-row")
	Row taskItemRow;

	Column taskNameColumn = new Column(2);

	Column missionNameColumn = new Column(2);

	Column sceneNameColumn = new Column(2);

	Column lookNumColumn = new Column(2);

	@Inject
	private App app;

	@PostConstruct
	public void init() {
		setupColumns();
		setupTaskItemRow();
	}

	private void setupColumns() {
		setupTaskNameColumn();
		setupMissionNameColumn();
		setutSceneNameColumn();
		setupLookNumColumn();

	}

	private void setupTaskItemRow() {
		taskItemRow.add(taskNameColumn);
		taskItemRow.add(missionNameColumn);
		taskItemRow.add(sceneNameColumn);
		taskItemRow.add(lookNumColumn);
	}

	private void setupTaskNameColumn() {
		taskNameColumn.add(taskName);
	}

	private void setupMissionNameColumn() {
		missionNameColumn.add(missionName);
	}

	private void setutSceneNameColumn() {
		sceneNameColumn.add(sceneName);
	}

	private void setupLookNumColumn() {
		lookNumColumn.add(lookNum);
	}

	@Override
	public CoalescenceTask getModel() {
		return task.getModel();
	}

	@Override
	public void setModel(CoalescenceTask model) {
		task.setModel(model);

	}

	// this will have a task and that should be put in a panel along with the bound fields

	// each task will be a row with a class that determines the color. this way the style can be changed
	// within each row, the task variables will be bound as label so that if needed, they can have their style changed
	// based on state

	// don't need a grid, just use the gwtbootstrap layout, make a task some width and then put the labels in the grid
	// divs

}
