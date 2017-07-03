package com.seeyon.apps.caict.sendflow.bean;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("formExport")
public class FormExportBean {

    @XStreamAlias("version")
    @XStreamAsAttribute
    private String version = "2.0";

    @XStreamAlias("summary")
    private SummaryBean summaryBean;

    @XStreamAlias("values")
    private ValueBean valueBean;

    @XStreamAlias("subForms")
    private List<SubFormBean> subFormList;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public SummaryBean getSummaryBean() {
        return summaryBean;
    }

    public void setSummaryBean(SummaryBean summaryBean) {
        this.summaryBean = summaryBean;
    }

    public ValueBean getValueBean() {
        return valueBean;
    }

    public void setValueBean(ValueBean valueBean) {
        this.valueBean = valueBean;
    }

    public List<SubFormBean> getSubFormList() {
        return subFormList;
    }

    public void setSubFormList(List<SubFormBean> subFormList) {
        this.subFormList = subFormList;
    }
}