package com.seeyon.apps.caict.sendflow.bean;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("values")
public class ValueBean {

    @XStreamImplicit(itemFieldName = "column")
    private List<ColumnBean> colList = null;

    @XStreamImplicit(itemFieldName = "row")
    private List<RowBean> rowList = null;

    public List<RowBean> getRowList() {
        return rowList;
    }

    public void setRowList(List<RowBean> rowList) {
        this.rowList = rowList;
    }

    public List<ColumnBean> getColList() {
        return colList;
    }

    public void setColList(List<ColumnBean> colList) {
        this.colList = colList;
    }

}
