package com.illy.data;

/**
 * Created by nouachi on 11/03/2018.
 */
public class ColumnValue {
	private final String column;
	private final String value;

	public ColumnValue(String column, String value) {
		this.column = column;
		this.value = value;
	}

	public String column() {
		return column;
	}

	public String value() {
		return value;
	}
}
