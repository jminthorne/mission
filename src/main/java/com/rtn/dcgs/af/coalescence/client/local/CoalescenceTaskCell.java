package com.rtn.dcgs.af.coalescence.client.local;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class CoalescenceTaskCell extends AbstractCell<TaskListItemWidget> {

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, TaskListItemWidget value, SafeHtmlBuilder sb) {
		if (value == null) {
			return;
		}
		sb.appendHtmlConstant(value.getElement().getInnerHTML());
	}

}
