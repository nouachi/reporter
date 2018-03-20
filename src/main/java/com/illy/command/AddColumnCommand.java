package com.illy.command;

import com.illy.Table;
import com.illy.metadata.Column;

/**
 * Created by nouachi on 20/03/2018.
 */
public class AddColumnCommand implements Command {

	private final Table table;
	private final Column column;

	public AddColumnCommand(Table table, Column column) {
		this.table = table;
		this.column = column;
	}

	@Override
	public void execute() {
		table.addColumn(column);
	}
}
