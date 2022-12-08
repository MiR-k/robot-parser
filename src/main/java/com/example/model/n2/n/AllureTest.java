package com.example.model.n2.n;

interface AllureTest extends AllureStep {

    int getTestId();

    String getDescription();

    String getTestTitle();

    String getSeverity();

    void setTestId(int id);

    void setTestTitle(String title);

    void setDescription(String description);

    void setSeverity(String severity);

}
