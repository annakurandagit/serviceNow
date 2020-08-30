package com.interview.annak;

import com.interview.annak.analyzer.runner.AnalyzerRunner;
import com.interview.annak.analyzer.runner.InvestigatorAnalyzerRunner;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * AnnakApplication
 * Main class
 */
@SpringBootApplication
public class AnnakApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnnakApplication.class, args);
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Please enter your input and output file : [input file full path,output file full path] ");
            System.out.println("To stop the program print : [QUIT]");

            String input = scanner.nextLine();
            if (input.toUpperCase().startsWith("QUIT")) {
                System.exit(0);
            }
            if (StringUtils.isEmpty(input)) {
                System.out.println("Bad input.Please try again");
                continue;
            }
            String[] data = input.split(",");
            if (data.length != 2) {
                System.out.println("Bad input.Please try again");
            } else {
                AnalyzerRunner analyzerRunner = new InvestigatorAnalyzerRunner(data[0], data[1]);
                analyzerRunner.execute();
            }
        }
    }
}
