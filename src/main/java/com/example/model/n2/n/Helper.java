package com.example.model.n2.n;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.util.Objects;

public class Helper {

    public static String generateFromTemplate(Object o, String templateName) {
        JtwigModel model = new JtwigModel().with("object", o);
//        model.with("printArgument", convertListToString(this.argumentsLists));
//        model.with("body", convertListToString(this.body));
//        model.with("comments", convertListToString(this.comments));
        return JtwigTemplate.classpathTemplate(templateName).render(model);
    }
}
