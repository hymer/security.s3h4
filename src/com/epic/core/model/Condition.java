package com.epic.core.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "valueType" })
public class Condition {

	public static final String LIKE_SYMBOL = "%";
	public static final String JOIN_SYMBOL = ",";

	public static final String EQ = "=";
	public static final String NOT_EQ = "!=";
	public static final String LIKE = " like ";
	public static final String BETWEEN = " between ";
	public static final String GT = ">";
	public static final String LT = "<";
	public static final String LTE = "<=";
	public static final String GTE = ">=";
	public static final String IN = " in ";
	public static final String NOT_IN = " not in ";

	private String key;
	private String operator = EQ;
	private Object value;
	private Class<?> valueType = String.class;

	public Condition() {
	}

	public Condition(String key, Object value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Class<?> getValueType() {
		return valueType;
	}

	public void setValueType(Class<?> valueType) {
		this.valueType = valueType;
	}

	@Override
	public String toString() {
		return "Condition[" + key + operator + value + "]";
	}

}
