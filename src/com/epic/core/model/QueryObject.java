package com.epic.core.model;

import java.util.List;


public class QueryObject {
	private List<Condition> conditions;
	private PageInfo pageinfo;
	private SortInfo sortinfo;

	public List<Condition> getConditions() {
		return conditions;
	}

	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}

	public PageInfo getPageinfo() {
		return pageinfo;
	}

	public void setPageinfo(PageInfo pageinfo) {
		this.pageinfo = pageinfo;
	}

	public SortInfo getSortinfo() {
		return sortinfo;
	}

	public void setSortinfo(SortInfo sortinfo) {
		this.sortinfo = sortinfo;
	}

}
