package com.illy.request;

/**
 * Created by nouachi on 11/03/2018.
 */
public class Request {
	private final String columnName;
	private final String operator;
	private final String value;

	public Request(String columnName, String operator, String value) {

		this.columnName = columnName;
		this.operator = operator;
		this.value = value;
	}

	public String operator(){
		return operator;
	}

	public String columnName(){
		return columnName;
	}

	public String value(){
		return value;
	}
}
