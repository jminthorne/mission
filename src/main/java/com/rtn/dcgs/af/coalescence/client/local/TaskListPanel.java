package com.rtn.dcgs.af.coalescence.client.local;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.jboss.errai.ui.nav.client.local.DefaultPage;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.github.gwtbootstrap.client.ui.Accordion;
import com.github.gwtbootstrap.client.ui.AccordionGroup;
import com.github.gwtbootstrap.client.ui.Row;
import com.github.gwtbootstrap.client.ui.event.ShowEvent;
import com.github.gwtbootstrap.client.ui.event.ShowHandler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.rtn.dcgs.af.coalescence.model.task.CoalescenceTask;

@Page(role = DefaultPage.class)
@Templated("TaskListPanel.html#task-list-panel")
public class TaskListPanel extends Composite {

	@Inject
	@DataField("drag")
	Label dragMe;

	@Inject
	@DataField("drop")
	Label dropMe;

	@Inject
	@DataField("accordian-row")
	Row accordianRow;

	@Inject
	@DataField("accordian")
	Accordion accordian;

	// @Inject
	// @DataField("task-grid")
	// CellTable<String> taskGrid;
	// CellTable<CoalescenceTask> taskGrid;

//	@Inject
//	@DataField("task-grid")
//	CellList<TaskListItemWidget> taskGrid;

	@Inject
	@DataField("panel")
	SimplePanel panel;

	static List<String> groups = Arrays.asList("Buenos Aires", "Córdoba", "La Plata");

	// private static final List<String> tasks = Arrays.asList("Exploit: Endor Shield Generator - 1",
	// "Exploit: Hoth Rebel Base - 2", "Exploit: Cloud City: Carbonite Facilities - 3");
	//
	private List<CoalescenceTask> tasks = new ArrayList<CoalescenceTask>();

	@PostConstruct
	public void init() {
		setupDrag();
		setupDrop();
		setupRowLayout();

	}

	/*
	 * TODO: setup drag and drop handler for two way changes. setting data on the drop actually changes the value that
	 * is seen. build list of groups build list of tasks (update group name on task [done in background] create group
	 * window - ability to add tasks at the same time?
	 */

	private void setupDrag() {
		System.out.println("\n\tdrag");
		dragMe.setText("drag me");
		dragMe.getElement().setDraggable(Element.DRAGGABLE_TRUE);
		dragMe.addDragStartHandler(new DragStartHandler() {

			@Override
			public void onDragStart(DragStartEvent event) {
				event.setData("text", "Dragging");
				event.getDataTransfer().setDragImage(dragMe.getElement(), 10, 10);
			}
		});
	}

	private void setupDrop() {
		System.out.println("\n\tdrop");
		dropMe.setText("drop me");
		dropMe.addDragOverHandler(new DragOverHandler() {

			@Override
			public void onDragOver(DragOverEvent event) {
				dropMe.getElement().getStyle().setBackgroundColor("#ffa");
			}
		});

		dropMe.addDropHandler(new DropHandler() {

			@Override
			public void onDrop(DropEvent event) {
				event.preventDefault();

				String data = event.getData("text");
				dropMe.setText(data);
			}
		});
	}

	private void setupRowLayout() {
		com.github.gwtbootstrap.client.ui.Column left = new com.github.gwtbootstrap.client.ui.Column(6);

		//setupTaskGrid();
		setupWidgetTable();

//		left.add(taskGrid);

		com.github.gwtbootstrap.client.ui.Column middle = new com.github.gwtbootstrap.client.ui.Column(6);

		setupPanel();

		middle.add(panel);

		com.github.gwtbootstrap.client.ui.Column right = new com.github.gwtbootstrap.client.ui.Column(6);

		setupGroupAccordian();

		right.add(accordian);

		accordianRow.add(left);
		accordianRow.add(middle);
		accordianRow.add(right);
	}

	private void setupGroupAccordian() {
		for (String group : groups) {
			final AccordionGroup accordianGroup = new AccordionGroup();

			accordianGroup.setHeading(group);

			accordianGroup.addShowHandler(new ShowHandler() {

				@Override
				public void onShow(ShowEvent showEvent) {

				}
			});
			accordian.add(accordianGroup);
		}

	}

	private void setupTaskGrid() {

		// Add a selection model to handle user selection.
		final SingleSelectionModel<CoalescenceTask> selectionModel = new SingleSelectionModel<CoalescenceTask>();
		// taskGrid.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				String selected = selectionModel.getSelectedObject().getTaskName();
				if (selected != null) {
					Window.alert("You selected: " + selected);
				}
			}
		});

		TextColumn<CoalescenceTask> taskNameColumn = new TextColumn<CoalescenceTask>() {

			@Override
			public String getValue(CoalescenceTask task) {
				return task.getTaskName();
			}
		};

		TextColumn<CoalescenceTask> missionNameColumn = new TextColumn<CoalescenceTask>() {

			@Override
			public String getValue(CoalescenceTask task) {
				return task.getMissionName();
			}
		};

		TextColumn<CoalescenceTask> sceneNameColumn = new TextColumn<CoalescenceTask>() {

			@Override
			public String getValue(CoalescenceTask task) {
				return task.getSceneName();
			}
		};

		TextColumn<CoalescenceTask> lookNumColumn = new TextColumn<CoalescenceTask>() {

			@Override
			public String getValue(CoalescenceTask task) {
				return task.getLookNum().toString();
			}
		};

		createTasks();

		// taskGrid.addColumn(taskNameColumn, "Task");
		// taskGrid.addColumn(missionNameColumn);
		// taskGrid.addColumn(sceneNameColumn);
		// taskGrid.addColumn(lookNumColumn);
		// taskGrid.setStriped(true);
		// taskGrid.setHover(true);
		// taskGrid.setRowData(0, tasks);

	}

	private void setupPanel() {
		// com.github.gwtbootstrap.client.ui.Label panelLabelLabel = new com.github.gwtbootstrap.client.ui.Label(
		// "vertical");
		//
		// panelLabelLabel.setType(LabelType.WARNING);
		//
		// com.github.gwtbootstrap.client.ui.Label scrollLabel = new com.github.gwtbootstrap.client.ui.Label("scroll");
		//
		// scrollLabel.setType(LabelType.SUCCESS);

		// ScrollPanel scroll = new ScrollPanel();

		// scroll.add(scrollLabel);

		VerticalPanel vpanel = new VerticalPanel();

		// vpanel.add(panelLabelLabel);
		// vpanel.add(scroll);

		// CellTable<com.github.gwtbootstrap.client.ui.Label> grid = new
		// CellTable<com.github.gwtbootstrap.client.ui.Label>();

		// Add a selection model to handle user selection.
		// final SingleSelectionModel<com.github.gwtbootstrap.client.ui.Label> selectionModel = new
		// SingleSelectionModel<com.github.gwtbootstrap.client.ui.Label>();
		// grid.setSelectionModel(selectionModel);
		// selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
		// public void onSelectionChange(SelectionChangeEvent event) {
		// String selected = selectionModel.getSelectedObject().toString();
		// if (selected != null) {
		// Window.alert("You selected: " + selected);
		// }
		// }
		// });

		// TextColumn<com.github.gwtbootstrap.client.ui.Label> taskLabel = new
		// TextColumn<com.github.gwtbootstrap.client.ui.Label>() {
		//
		// @Override
		// public String getValue(com.github.gwtbootstrap.client.ui.Label label) {
		// return label.getText();
		// }
		// };

		// List<com.github.gwtbootstrap.client.ui.Label> taskList = new
		// ArrayList<com.github.gwtbootstrap.client.ui.Label>();
		// for(String task : tasks){
		// com.github.gwtbootstrap.client.ui.Label label = new com.github.gwtbootstrap.client.ui.Label(task);
		// taskList.add(label);
		// }

		// grid.addColumn(taskLabel, "Task");
		// grid.setStriped(true);
		// grid.setHover(true);
		// grid.setBordered(true);
		// grid.setRowData(0, taskList);

		// vpanel.add(grid);

		panel.add(vpanel);

	}

	private void createTasks() {
		CoalescenceTask endorTask = new CoalescenceTask();

		endorTask.setTaskName("Exploit: Endor - Shield Generator Base - 1");
		endorTask.setMissionName("Endor");
		endorTask.setSceneName("Shield Generator Base");
		endorTask.setLookNum(1);

		System.out.println("\n\n\tadded endor");
		tasks.add(endorTask);

		CoalescenceTask hothTask = new CoalescenceTask();

		hothTask.setTaskName("Exploit: Hoth - Rebel Base - 2");
		hothTask.setMissionName("Hoth");
		hothTask.setSceneName("Rebel Base");
		hothTask.setLookNum(2);

		System.out.println("\n\n\tadded hoth");
		tasks.add(hothTask);

		CoalescenceTask cloudCityTask = new CoalescenceTask();

		cloudCityTask.setTaskName("Exploit: Cloud City - Lando Operations - 5");
		cloudCityTask.setMissionName("Bespin");
		cloudCityTask.setSceneName("Lando Operations");
		cloudCityTask.setLookNum(5);

		System.out.println("\n\n\tadded cloud city");
		tasks.add(cloudCityTask);

	}

	private List<TaskListItemWidget> createWidgetItems() {

		CoalescenceTask endorTask = new CoalescenceTask();

		endorTask.setTaskName("Exploit: Endor - Shield Generator Base - 1");
		endorTask.setMissionName("Endor");
		endorTask.setSceneName("Shield Generator Base");
		endorTask.setLookNum(1);

		TaskListItemWidget item = new TaskListItemWidget();

		item.setModel(endorTask);

		List<TaskListItemWidget> items = new ArrayList<TaskListItemWidget>();
		items.add(item);
		return items;

	}

	private void setupWidgetTable() {

		// Add a selection model to handle user selection.
//		final SingleSelectionModel<TaskListItemWidget> selectionModel = new SingleSelectionModel<TaskListItemWidget>();
//		taskGrid.setSelectionModel(selectionModel);
//		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
//			public void onSelectionChange(SelectionChangeEvent event) {
//				String selected = selectionModel.getSelectedObject().getModel().getTaskName();
//				if (selected != null) {
//					Window.alert("You selected: " + selected);
//				}
//			}
//		});
//
//
//		taskGrid = new CellList<TaskListItemWidget>(new CoalescenceTaskCell());
//
//		taskGrid.setRowData(createWidgetItems());
//		

	}

}