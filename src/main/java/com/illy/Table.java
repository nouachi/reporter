package com.illy;

import com.illy.data.Row;
import com.illy.metadata.Column;
import com.illy.metadata.Header;
import com.illy.request.Request;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by nouachi on 11/03/2018.
 */
public class Table {

	private final Header header;
	private final List<Row> rows;

	public Table(Column... columns) {
		header = new Header(new ArrayList<>(asList(columns)));
		rows = new ArrayList<>();
	}

	public void insert() {
		rows.add(new Row(header.defaultValues()));
	}

	public void insert(Row row) {
		if(row.conformTo(header)){
			header.complete(row);
			rows.add(row);
		}
	}

	public Row getOne(Request request) {
		if(request.operator().equals("==")){
			return rows.stream()
					.filter(r -> r.valueOf(request.columnName()).equals(request.value()))
					.findFirst()
					.orElse(null);
		}
		return null;
	}

	public void addColumn(Column column) {
		header.add(column);
		rows.stream().forEach(r -> r.completeWithDefaultValue(column));
	}

	public Column getColumn(int index) {
		return header.get(index);
	}

	public void removeColumn(String columnName) {
		header.remove(columnName);
		rows.stream().forEach(r -> r.remove(columnName));
	}
}
