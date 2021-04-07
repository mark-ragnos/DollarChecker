package com.example.dollarchecker.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.Objects;

@Root(name = "Record")
public class Record {
    @Attribute
    private String Date;
    @Attribute(required = false)
    private String Id;
    @Element(required = false)
    private String Nominal;
    @Element
    private String Value;

    public String getId() {
        return Id;
    }

    public String getDate() {
        return Date;
    }

    public String getValue() {
        return Value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record record = (Record) o;
        return Objects.equals(Date, record.Date) &&
                Objects.equals(Id, record.Id) &&
                Objects.equals(Nominal, record.Nominal) &&
                Objects.equals(Value, record.Value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Date, Id, Nominal, Value);
    }
}
