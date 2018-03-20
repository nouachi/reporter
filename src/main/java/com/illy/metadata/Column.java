package com.illy.metadata;

/**
 * Created by nouachi on 11/03/2018.
 */
public class Column {
	private String name;
	private final ColumnType type;
	private final String defaultValue;

	public Column(String name, ColumnType type, String defaultValue) {
		this.name = name;
		this.type = type;
		this.defaultValue = defaultValue;
	}

	public ColumnType type() {
		return type;
	}

	public String defaultValue() {
		return defaultValue;
	}

	public String name() {
		return name;
	}
}
