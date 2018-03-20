package com.illy;

import com.illy.data.ColumnValue;
import com.illy.data.Row;
import com.illy.metadata.Column;
import com.illy.metadata.ColumnType;
import com.illy.metadata.Header;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by nouachi on 11/03/2018.
 */
public class RowTest {

	private ColumnValue columnValue;
	private ColumnValue columnValue2;
	private Header header;

	@Before
	public void setUp() throws Exception {
		setUpColumns();
		setUpHeader();
	}

	private void setUpColumns() {
		columnValue = new ColumnValue("ColumnName", "Value");
		columnValue2 = new ColumnValue("ColumnName2", "Value2");
	}

	private void setUpHeader() {
		List<Column> columns = new ArrayList<>();
		columns.add(new Column("column1", ColumnType.string, "default1"));
		columns.add(new Column("column2", ColumnType.string, "default2"));
		columns.add(new Column("column3", ColumnType.string, "default3"));
		header = new Header(columns);
	}

	@Test
	public void aRawIsACoupleOfColumnNameValue() throws Exception {
		Row row = new Row(new ColumnValue("ColumnName", "Value"));
		assertThat(row.valueOf("ColumnName"), is("Value"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowErrorIfColumnNameNotFound() throws Exception {
		Row row = new Row(columnValue);
		assertThat(row.valueOf("errorName"), is("Value"));
	}

	@Test
	public void aRawCanBeASetOfColumnNameAndValues() throws Exception {
		Row row = new Row(columnValue, columnValue2);
		assertThat(row.valueOf("ColumnName"), is("Value"));
		assertThat(row.valueOf("ColumnName2"), is("Value2"));
	}

	@Test
	public void rowRespectHeaderIfAllColumnNameExistInHeader() throws Exception {
		Row row = new Row(new ColumnValue("column1", "value1"),
							new ColumnValue("column2", "value2"),
							new ColumnValue("column3", "value3"));
		Assert.assertTrue(row.conformTo(header));
	}

	@Test(expected = IllegalStateException.class)
	public void expectExceptionIfRowContainNoneExinstingColumnName() throws Exception {
		Row row = new Row(new ColumnValue("column1", "value1"),
				new ColumnValue("column2", "value2"),
				new ColumnValue("noneExistingColumn", "value3"));
		Assert.assertTrue(row.conformTo(header));
	}

	@Test
	public void canCompleteARowWithDefaultValue() throws Exception {
		Row row = new Row(new ColumnValue("column1", "value1"),
				new ColumnValue("column2", "value2"));

		header.complete(row);
		assertThat(row.valueOf("column1"), is("value1"));
		assertThat(row.valueOf("column2"), is("value2"));
		assertThat(row.valueOf("column3"), is("default3"));
	}
}
