package com.interswitchgroup.pinonmobile.models;

public class GenericResponse extends Throwable{

    public String code;
    public String message;

    public GenericResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
