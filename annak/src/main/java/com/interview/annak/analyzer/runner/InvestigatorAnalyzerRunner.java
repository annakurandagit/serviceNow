package com.interview.annak.analyzer.runner;

import com.interview.annak.analyzer.Analyzer;
import com.interview.annak.analyzer.DeltaAnalyzer;
import com.interview.annak.dao.AnalyzerResult;
import com.interview.annak.handlers.FileReaderHandler;
import com.interview.annak.handlers.FileWriterHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * InvestigatorAnalyzerRunner
 * Read input file and analyze with delta analyzer
 * Write analyzer result to output file
 */
public class InvestigatorAnalyzerRunner implements AnalyzerRunner {
    private static final Logger logger = LoggerFactory.getLogger(InvestigatorAnalyzerRunner.class);
    private final Analyzer analyzer;
    private final FileWriterHandler fileWriterHandler;

    /**
     * InvestigatorAnalyzerRunner
     * @param in - input file name
     * @param out - output file name
     */
    public InvestigatorAnalyzerRunner(String in, String out) {
        logger.info(String.format("Analyzer .Input file %s.output file %s", in, out));
        this.fileWriterHandler = new FileWriterHandler(out);
        FileReaderHandler fileReaderHandler = new FileReaderHandler(in);
        this.analyzer = new DeltaAnalyzer(fileReaderHandler);
    }

    /**
     * Execute analyzer flow
     */
    @Override
    public void execute() {

        try {
            AnalyzerResult analyzerResult = analyzer.analyzeData();
            fileWriterHandler.open();
            fileWriterHandler.getHandler().write(analyzerResult.toString().getBytes());
            fileWriterHandler.close();

        } catch (IOException e) {
            logger.error("Failed write result to file " + e);
        }

    }
}
