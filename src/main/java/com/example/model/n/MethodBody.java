package com.example.model.n;

import java.util.ArrayList;
import java.util.List;

public class MethodBody {

    private List<StringInMethod> stringsList = new ArrayList<>();

    MethodBody() {}

    public MethodBody(ArrayList arrayList) {
        this.stringsList = arrayList;
    }

    public List<StringInMethod> getStringsList() {
        return this.stringsList;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("    {");
        for (StringInMethod singleString : this.stringsList) {
            builder.append("    " + singleString.toString() + '\n');
        }
        builder.append("    }");
        return builder.toString();
    }
}
