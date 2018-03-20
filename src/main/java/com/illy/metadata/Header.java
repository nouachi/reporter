package com.illy.metadata;

import com.illy.data.ColumnValue;
import com.illy.data.Row;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * Created by nouachi on 11/03/2018.
 */
public class Header {
	private List<Column> columns;

	public Header(List<Column> columns) {
		this.columns = columns;
	}

	public boolean contain(String columnName) {
		return columns.stream()
				.filter(c -> c.name().equals(columnName))
				.findFirst()
				.isPresent();
	}

	public List<ColumnValue> defaultValues() {
		return columns.stream().map(h -> new ColumnValue(h.name(), h.defaultValue())).collect(toList());
	}

	public void complete(Row row) {
		List<ColumnValue> defaultValues = defaultValues();
		if(row.size() == 0) {
			row.addAll(defaultValues);
		} else {
			defaultValues.stream().forEach(defaultValue -> {
				if(!row.hasColumn(defaultValue.column())){
					row.add(defaultValue);
				}
			});
		}
	}

	public void add(Column column){
		columns.add(column);
	}

	public Column get(int index) {
		return columns.get(index - 1);
	}

	public void remove(String columnName) {
		Optional<Column> column = columns.stream()
									.filter(c -> c.name().equals(columnName))
									.findFirst();
		if (column.isPresent())
			columns.remove(column.get());
	}
}
