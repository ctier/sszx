package com.seeyon.apps.caict.sendflow.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("subForm")
public class SubFormBean {
	
    @XStreamAlias("values")
    private ValueBean valueBean;

    
    public ValueBean getValueBean() {
        return valueBean;
    }

    
    public void setValueBean(ValueBean valueBean) {
        this.valueBean = valueBean;
    }

}