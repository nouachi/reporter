package com.illy;

import com.illy.command.AddColumnCommand;
import com.illy.command.Command;
import com.illy.command.RemoveColumnCommand;
import com.illy.data.ColumnValue;
import com.illy.data.Row;
import com.illy.metadata.Column;
import com.illy.request.Request;
import org.junit.Before;
import org.junit.Test;

import static com.illy.metadata.ColumnType.string;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Created by nouachi on 11/03/2018.
 */
public class TableTest {

	private Column stringColumn;
	private Table table;

	@Before
	public void setUp() throws Exception {
		stringColumn = new Column("name", string, "toto");
		table = new Table(new Column("name1", string, "default1"),
							new Column("name2", string, "default2"));
	}

	@Test
	public void columnShouldHaveANameATypeAndDefaultValue() throws Exception {
		assertThat(stringColumn.name(), is("name"));
		assertThat(stringColumn.type(), is(string));
		assertThat(stringColumn.defaultValue(), is("toto"));
	}

	@Test(expected = IllegalStateException.class)
	public void ifColumnNameDoesntExistThrowException() throws Exception {
		table.insert(new Row(new ColumnValue("errorColumnName", "givenName")));
	}

	@Test
	public void insertWithNoRawInsertDefaultValueInTheTable() throws Exception {
		table.insert();
		Row row = table.getOne(new Request("name1", "==", "default1"));
		assertNotNull(row);
		assertThat(row.valueOf("name1"), is("default1"));
		assertThat(row.valueOf("name2"), is("default2"));
	}

	@Test
	public void canInsertData() throws Exception {
		table.insert(new Row(new ColumnValue("name1", "givenName")));
		table.insert(new Row(new ColumnValue("name2", "givenName2")));

		Row row = table.getOne(new Request("name1", "==", "givenName"));
		assertNotNull(row);
		assertThat(row.valueOf("name1"), is("givenName"));
		assertThat(row.valueOf("name2"), is("default2"));

		row = table.getOne(new Request("name2", "==", "givenName2"));
		assertNotNull(row);
		assertThat(row.valueOf("name1"), is("default1"));
		assertThat(row.valueOf("name2"), is("givenName2"));

		row = table.getOne(new Request("name2", "==", "noneExistingValue"));
		assertNull(row);
	}

	@Test(expected = IllegalArgumentException.class)
	public void ifRequestOnNoneExistingColumnNameThrowException() throws Exception {
		table.insert(new Row(new ColumnValue("name1", "givenName")));
		table.insert(new Row(new ColumnValue("name2", "givenName2")));
		table.getOne(new Request("noneExistingColumnName", "==", "givenName"));
	}

	@Test
	public void canAddColumnToTable() throws Exception {
		Column column = new Column("name3", string, "default3");
		Command addColumnCommand = new AddColumnCommand(table, column);
		addColumnCommand.execute();
		assertThat(table.getColumn(3), is(column));
	}

	@Test
	public void canRemoveColumnFromTable() throws Exception {
		Command removeColumnCommand = new RemoveColumnCommand(table, "name1");
		removeColumnCommand.execute();
		assertThat(table.getColumn(1).name(), is("name2"));
	}
}
