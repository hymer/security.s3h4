package com.epic.core.model;

public class SortInfo {

	private String variableName;
	private int direction = 1;

	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	@Override
	public String toString() {
		return " order by " + variableName
				+ ((direction < 0) ? " desc " : " asc ");
	}

}
