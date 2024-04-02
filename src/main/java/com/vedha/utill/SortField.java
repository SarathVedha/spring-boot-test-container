package com.vedha.utill;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortField {

    ID("id"),

    NAME("name"),

    AGE("age"),

    EMAIL("email");

    private final String filedValue;
}
