package com.example.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Created by Dev on 23.10.2016.
 */
@NoArgsConstructor
@AllArgsConstructor
public class Argument {

    private final Class argumentType = String.class;
    private final String argumentName = null;

    @Override
    public String toString() {
        if (this.argumentName == null) {
            return "";
        }
        return String.format("%s %s", this.argumentType.getSimpleName(), this.argumentName);
    }

}
