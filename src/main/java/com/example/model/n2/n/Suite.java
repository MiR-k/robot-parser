package com.example.model.n2.n;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static freemarker.template.utility.StringUtil.capitalize;

public class Suite {

    public String author = "Ivan.Ivanov";
    public String features = "\"UFM\"";
    public String baseclass = "BaseTest";
    public String source = "/templates/18_1_2_ps.export_product_offerings_result.robot";

    private static String stringPackage = "com.peterservice.ufm.test";
    private List<Variable> suiteVariablesList = new ArrayList<>();
    private List<Step> stepsList = new ArrayList<>();
    private List<RobotTest> testsList = new ArrayList<>();

    public Suite() {
    }

    public Suite(List<Variable> suiteVariablesList, List<Step> steps, List<RobotTest> tests) {
        this.suiteVariablesList = suiteVariablesList;
        this.stepsList = steps;
        this.testsList = tests;
    }

    public List<Step> getStepList() {
        return stepsList;
    }

    public List<RobotTest> getTestList() {
        return testsList;
    }

    public List<Variable> getSuiteVariableList() {
        return suiteVariablesList;
    }

    public boolean addStep(Step step) {
        return stepsList.add(step);
    }

    public boolean addTest(RobotTest test) {
        return testsList.add(test);
    }

    public boolean addSuiteVariable(Variable localVariable) {
        return suiteVariablesList.add(localVariable);
    }

    public void setDescriptionForSuite(String author, String features, String baseClass) {
        this.author = author;
        this.features = features;
        this.baseclass = baseClass;
    }

    public String getSource() {
        return this.source;
    }

    protected String formatSource(String fullSource) {
        String fileName = new File(fullSource).getName().replaceAll("\\.\\w+$", "").replaceAll("\\d+", "");
        fileName = capitalize(fileName.replaceAll("[\\._]", " ")).replaceAll("\\s+", "");
        return fileName.replaceAll("[\\(\\)\\-']", "");
    }

    @Override
    public String toString() {
        String METHODMETAFILE = "/templates/SuiteTemplate.twig";
        return generateFromTemplate(METHODMETAFILE);
    }

    String generateFromTemplate(String templateName) {
        JtwigModel model = new JtwigModel().with("suite", this);
        return JtwigTemplate.classpathTemplate(templateName).render(model);
    }

}
