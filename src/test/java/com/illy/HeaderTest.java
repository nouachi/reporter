package com.illy;

import com.illy.data.ColumnValue;
import com.illy.data.Row;
import com.illy.metadata.Column;
import com.illy.metadata.ColumnType;
import com.illy.metadata.Header;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by nouachi on 11/03/2018.
 */
public class HeaderTest {

	private Header header;

	@Before
	public void setUp() throws Exception {
		ArrayList<Column> columns = new ArrayList<>();
		columns.add(new Column("name1", ColumnType.string, "default1"));
		columns.add(new Column("name2", ColumnType.string, "default2"));
		header = new Header(columns);
	}

	@Test
	public void headerIsAListOfColumn() throws Exception {
		assertTrue(header.contain("name1"));
		assertTrue(header.contain("name2"));
		assertFalse(header.contain("name3"));
	}

	@Test
	public void canCompeteEmptyRowWithDefaultValue() throws Exception {
		Row row = new Row();
		header.complete(row);
		assertThat(row.valueOf("name1"), is("default1"));
		assertThat(row.valueOf("name2"), is("default2"));
	}

	@Test
	public void canCompeteRowWithDefaultValue() throws Exception {
		Row row = new Row(new ColumnValue("name1", "givenName"));
		header.complete(row);
		assertThat(row.valueOf("name1"), is("givenName"));
		assertThat(row.valueOf("name2"), is("default2"));
		assertThat(row.size(), is(2));
	}
}
