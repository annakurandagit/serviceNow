package com.interview.annak.analyzer;

import com.interview.annak.dao.DeltaAnalyzerResult;
import com.interview.annak.handlers.FileReaderHandler;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * DeltaAnalyzer
 * Read line by line buffer and group sentences by sentences where only a single word has
 * changed
 */
public class DeltaAnalyzer implements Analyzer {
    private static final Logger logger = LoggerFactory.getLogger(DeltaAnalyzer.class);
    public static final int index = 2;

    FileReaderHandler fileReader;
    DeltaAnalyzerResult analyzerResult;

    /**
     * DeltaAnalyzer
     * @param fileReader - input file handler
     */
    public DeltaAnalyzer(FileReaderHandler fileReader) {
        this.fileReader = fileReader;
        analyzerResult = new DeltaAnalyzerResult();
    }

    /**
     * DeltaAnalyzerResult
     * @return DeltaAnalyzerResult records grouped by delta
     */
    @Override
    public DeltaAnalyzerResult analyzeData() {
        try {
            fileReader.open();
            if (fileReader.getHandler() != null)
                fileReader.getHandler().lines().forEach(this::analyzeLine);
        } catch (Exception e) {
            logger.error("Failed analyze data", e);
        } finally {
            fileReader.close();
        }
        try {
            Map<String, Set<Pair<String, String>>> result = analyzerResult.getResult();
            //Remove entries that match only one row
            result = result.entrySet().stream().filter(entry -> entry.getValue().size() > 1)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            analyzerResult.setResult(result);
        } catch (Exception e) {
            logger.error("Analyzer failed " + e);
        }

        return analyzerResult;
    }

    private void analyzeLine(String line) {
        //get array of words
        String[] words = line.split("\\s+");
        for (int i = index; i < words.length; i++) {
            String regex = getRegex(i, words);
            analyzerResult.getResult().computeIfAbsent(regex, k -> new HashSet<>());
            analyzerResult.getResultByRegex(regex).add(new MutablePair<>(words[i], line) {
            });
        }

    }

    //create match pattern for group indication
    //pattern is line without spaces and word at index = escape index
    private String getRegex(int escapeIndex, String[] words) {
        StringBuilder sb = new StringBuilder();
        for (int i = index; i < words.length; i++) {
            if (i == escapeIndex) continue;
            sb.append(words[i]);
        }
        return sb.toString();
    }
}
