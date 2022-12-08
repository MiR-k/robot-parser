package com.example.model.n2.n;

public interface AllureStep {

    String METAFILE = "/templates/TestMetaInformation.twig";

    String generateClassFromTemplate(String templateName);

    default String buildMetaInformation() {
        return generateClassFromTemplate(this.METAFILE);
    }

}
