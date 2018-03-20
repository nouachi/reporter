package com.illy.command;

import com.illy.Table;

/**
 * Created by nouachi on 20/03/2018.
 */
public class RemoveColumnCommand implements Command {
	private final Table table;
	private final String columnName;

	public RemoveColumnCommand(Table table, String columnName) {
		this.table = table;
		this.columnName = columnName;
	}

	@Override
	public void execute() {
		table.removeColumn(columnName);
	}
}
