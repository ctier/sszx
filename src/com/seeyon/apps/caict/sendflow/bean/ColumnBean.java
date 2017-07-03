package com.seeyon.apps.caict.sendflow.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("column")
public class ColumnBean {

	@XStreamAlias("name")
	@XStreamAsAttribute
	private String name;

	@XStreamAlias("value")
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}