package com.example.dollarchecker;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "ValCurs")
public class ValCurs {
    @Attribute
    private String ID;
    @Attribute
    private String DateRange1;
    @Attribute
    private String DateRange2;
    @Attribute
    private String name;

    @ElementList(name = "Record", inline =  true, required = false)
    private List<Record> valueList;

    public List<Record> getValueList() {
        return valueList;
    }

    public void setValueList(List<Record> valueList) {
        this.valueList = valueList;
    }
}

