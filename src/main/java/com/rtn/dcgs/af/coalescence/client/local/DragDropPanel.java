package com.rtn.dcgs.af.coalescence.client.local;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.jboss.errai.ui.shared.api.annotations.DataField;

import com.github.gwtbootstrap.client.ui.Accordion;
import com.github.gwtbootstrap.client.ui.AccordionGroup;
import com.github.gwtbootstrap.client.ui.CellTable;
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
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

//@Page(role = DefaultPage.class)
//@Templated("DragDropPanel.html#drag-drop-panel")
public class DragDropPanel{// extends Composite {

	@Inject
	@DataField("drag")
	Label dragMe;

	@Inject
	@DataField("drop")
	Label dropMe;

	@Inject
	@DataField("accordian")
	Accordion accordian;

	@Inject
	@DataField("accordian-row")
	Row accordianRow;

	@Inject
	@DataField("task-grid")
	CellTable<String> taskGrid = new CellTable<String>();

	static List<String> groups = Arrays.asList("Buenos Aires", "Córdoba", "La Plata");

	// static List<String> users = Arrays.asList("Han Solo", "Boba Fett", "Luke Skywalker", "Master Yoda");

	static List<String> tasks = Arrays.asList("Exploit: Endor Shield Generator - 1", "Exploit: Hoth Rebel Base - 2",
			"Exploit: Cloud City: Carbonite Facilities - 3");


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

		com.github.gwtbootstrap.client.ui.Column right = new com.github.gwtbootstrap.client.ui.Column(6);

		setupTaskGrid();

		left.add(taskGrid);

		setupGroupAccordian();

		right.add(accordian);

		accordianRow.add(left);
		accordianRow.add(right);
	}

	private void setupGroupAccordian() {
		for (String group : groups) {
			final AccordionGroup accordianGroup = new AccordionGroup();

			accordianGroup.setHeading(group);

			// for (String user : users) {
			//
			// com.github.gwtbootstrap.client.ui.Label bsl = new com.github.gwtbootstrap.client.ui.Label("bsl " + user);
			// bsl.setType(LabelType.SUCCESS);
			//
			// accordianGroup.add(bsl);
			//
			// }
			// for (String user : users) {
			// SimplePanel p = new SimplePanel();
			//
			// Label spl = new Label("spl ");
			//
			// p.add(spl);
			//
			// accordianGroup.add(p);
			// }

			// accordianGroup.add(p);

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
		final SingleSelectionModel<String> selectionModel = new SingleSelectionModel<String>();
		taskGrid.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				String selected = selectionModel.getSelectedObject().toString();
				if (selected != null) {
					Window.alert("You selected: " + selected);
				}
			}
		});

		TextColumn<String> taskNameColumn = new TextColumn<String>() {

			@Override
			public String getValue(String object) {
				return object;
			}
		};

		// TextColumn<String> taskNameColumn = new TextColumn<String>() {
		// @Override
		// public String getValue(String task) {
		// return task;
		// }
		// };

		taskGrid.addColumn(taskNameColumn, "Task");
		taskGrid.setStriped(true);
		taskGrid.setHover(true);
		taskGrid.setBordered(true);
		taskGrid.setRowData(0, tasks);

	}
}
