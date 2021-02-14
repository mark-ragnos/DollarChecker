package com.example.dollarchecker;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Record")
public class Record{
    @Attribute
    private  String Date;
    @Attribute
    private  String Id;
    @Element
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
