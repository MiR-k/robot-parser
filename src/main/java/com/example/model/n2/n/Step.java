package com.example.model.n2.n;

public class Step extends Method implements AllureStep {

    public Step() {
    }

    public Step(Step step) {
        super(step);
    }

    public String toString() {
        return buildMetaInformation() + super.toString();
    }

}
