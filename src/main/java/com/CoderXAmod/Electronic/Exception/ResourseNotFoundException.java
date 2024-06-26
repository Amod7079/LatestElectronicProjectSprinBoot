package com.CoderXAmod.Electronic.Exception;

import lombok.Builder;

@Builder
public class ResourseNotFoundException  extends  RuntimeException{
    public ResourseNotFoundException() {
        super("Resourse Not Found !!");
    }

    public ResourseNotFoundException(String message) {
        super(message);
    }
}
