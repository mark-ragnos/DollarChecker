package com.example.dollarchecker.network;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Record")
public class Record{
    @Attribute
    private  String Date;
    @Attribute(required = false)
    private  String Id;
    @Element(required = false)
    private String Nominal;
    @Element
    private String Value;

    public String getDate() {
        return Date;
    }

    public String getValue() {
        return Value;
    }
}
