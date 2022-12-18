package com.in28minutes.rest.webservices.restfulwebservices.filtering;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
//@JsonIgnoreProperties("f3")
public class SomeBean {
    private final String f1;
    @JsonIgnore //prefer
    private final String f2;
    private final String f3;

    public SomeBean(String value1, String value2, String value3) {
        this.f1 = value1;
        this.f2 = value2;
        this.f3 = value3;
    }
}
