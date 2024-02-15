package com.insurance_domain.exceptation;

public class NoRecordFoundException extends RuntimeException {

    public NoRecordFoundException(String str){
        System.out.println(str);
    }
}
