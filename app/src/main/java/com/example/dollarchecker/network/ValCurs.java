package com.example.dollarchecker.network;

import com.example.dollarchecker.network.Record;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "ValCurs")
public class ValCurs {
    @Attribute(required = false)
    private String ID;
    @Attribute(required = false)
    private String DateRange1;
    @Attribute(required = false)
    private String DateRange2;
    @Attribute(required = false)
    private String name;
    @ElementList(name = "Record", inline =  true)
    private List<Record> valueList;


    public List<Record> getValueList() {
        return valueList;
    }

    public void setValueList(List<Record> valueList) {
        this.valueList = valueList;
    }
}

