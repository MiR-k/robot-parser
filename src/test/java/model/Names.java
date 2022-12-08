package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

/**
 * @author Anton_Tatur
 * @created 10/28/2020
 */
public class Names {

    public static void main(String[] args) {
        List<String> names = asList("");
        Map<Object, List<String>> namesMap = names.stream().collect(Collectors.groupingBy(it -> it.equals("a")));
        namesMap.get("a").size();
    }
}
