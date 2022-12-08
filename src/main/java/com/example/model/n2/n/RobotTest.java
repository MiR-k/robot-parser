package com.example.model.n2.n;

public class RobotTest extends Method implements AllureTest {

    private int testId;
    private String testTitle = null;
    private String description = null;
    private String severity = "NORMAL";

    public RobotTest() {
    }

    public RobotTest(RobotTest test) {
        super(test);
        testId = test.getTestId();
        testTitle = test.getTestTitle();
        description = test.getDescription();
        severity = test.getSeverity();
    }

    @Override
    public int getTestId() {
        return testId;
    }

    @Override
    public void setTestId(int testId) {
        this.testId = testId;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String getTestTitle() {
        return this.testTitle;
    }

    @Override
    public String getSeverity() {
        return severity;
    }

    @Override
    public void setTestTitle(String title) {
        this.testTitle = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String toString() {
        return buildMetaInformation() + super.toString();
    }

}
