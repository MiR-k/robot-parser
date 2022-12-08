package com.example.model.n2.n;

/**
 * Created by Dev on 23.10.2016.
 */
public class Argument {

    private Class argumentType = String.class;
    private String argumentName = null;

    public Argument() {
    }

    public Argument(Class argumentType, String argumentName) {
        this.argumentType = argumentType;
        this.argumentName = argumentName;
    }

    @Override
    public String toString() {
        if (this.argumentName == null) {
            return "";
        }
        return this.argumentType.getSimpleName() + " " + this.argumentName;
    }
}
