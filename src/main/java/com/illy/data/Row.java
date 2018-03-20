package com.illy.data;

import com.illy.metadata.Column;
import com.illy.metadata.Header;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by nouachi on 11/03/2018.
 */
public class Row {
	private List<ColumnValue> values;

	public Row(ColumnValue ... values) {
		this.values = new ArrayList<>(Arrays.asList(values));
	}

	public Row(List<ColumnValue> values) {
		this.values = values;
	}

	public String valueOf(String columnName) {
		Optional<ColumnValue> columnValue = values.stream().filter(cv -> cv.column().equals(columnName)).findFirst();
		if(columnValue.isPresent())
			return columnValue.get().value();
		throw new IllegalArgumentException("Column name not found");
	}

	public boolean conformTo(Header header) {
		if(values.isEmpty())
			return true;
		values.stream().forEach(value -> {
			if( !header.contain(value.column()))
				throw new IllegalStateException("Row not conform to header, column name: " + value.column() + " doesn't exist");
		});
		return true;
	}

	public void addAll(List<ColumnValue> columnValues) {
		values.addAll(columnValues);
	}

	public int size() {
		return values.size();
	}

	public boolean hasColumn(String column) {
		return values.stream()
				.filter(v -> v.column().equals(column))
				.findFirst()
				.isPresent();
	}

	public void completeWithDefaultValue(Column column) {
		add(new ColumnValue(column.name(), column.defaultValue()));
	}

	public void add(ColumnValue columnValue) {
		values.add(columnValue);
	}

	public void remove(String columnName) {
		Optional<ColumnValue> columnValue = values.stream()
				.filter(v -> v.column().equals(columnName))
				.findFirst();
		if(columnValue.isPresent())
			values.remove(columnValue.get());
	}
}
