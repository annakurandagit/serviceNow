package com.interview.annak.dao;


import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * DeltaAnalyzerResult
 *
 *
 */
public class DeltaAnalyzerResult implements AnalyzerResult<Map<String, Set<Pair<String, String>>>> {

    private final String RESULT_PRINT_PREFIX = "The changing word was: %s";
    //map lines by escaped word in line and sentence combinations when the word escaped
    Map<String, Set<Pair<String, String>>> map;

    public DeltaAnalyzerResult() {
        this.map = new HashMap<>();
    }

    public Set<Pair<String, String>> getResultByRegex(String delta) {
        return map.getOrDefault(delta, new HashSet<>());

    }

    @Override
    public Map<String, Set<Pair<String, String>>> getResult() {
        return map;
    }

    @Override
    public void setResult(Map<String, Set<Pair<String, String>>> map) {
        this.map = map;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        map.keySet().forEach(key -> {
            Set<String> delta = new HashSet<>();
            map.get(key).forEach(pair -> {
                delta.add(pair.getKey());
                sb.append(pair.getValue()).append(System.lineSeparator());
            });
            sb.append(String.format(RESULT_PRINT_PREFIX, String.join(", ", delta))).append(System.lineSeparator()).append(System.lineSeparator());
        });

        return sb.toString();

    }


}
