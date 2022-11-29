package com.ansv.taskmanagement.dto.criteria;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;

    public SearchCriteria(String key, String operation, Object value) {
        super();
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

//    public Object getValue() {
//        return value;
//    }
//
//    public String getKey() {
//        return key;
//    }
//
//    public String getOperation() {
//        return operation;
//    }
}
