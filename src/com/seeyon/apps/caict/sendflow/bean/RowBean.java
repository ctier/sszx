package com.seeyon.apps.caict.sendflow.bean;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("row")
public class RowBean {
	
	@XStreamImplicit(itemFieldName="column")
	private List<ColumnBean> colList = null;

	public List<ColumnBean> getColList() {
		return colList;
	}

	public void setColList(List<ColumnBean> colList) {
		this.colList = colList;
	}
	
}