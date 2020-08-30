package com.interview.annak;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.annak.analyzer.Analyzer;
import com.interview.annak.analyzer.DeltaAnalyzer;
import com.interview.annak.analyzer.runner.AnalyzerRunner;
import com.interview.annak.analyzer.runner.InvestigatorAnalyzerRunner;
import com.interview.annak.dao.AnalyzerResult;
import com.interview.annak.handlers.FileReaderHandler;
import com.interview.annak.handlers.FileWriterHandler;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

@SpringBootTest
class AnnakApplicationTests {

	@Test
	void contextLoads() {
	}
	@Test
	public void fileReaderNullOnFakeFile(){
		FileReaderHandler fileReaderHandler = new FileReaderHandler("bla.txt");
		fileReaderHandler.open();
		BufferedReader handler = fileReaderHandler.getHandler();
		Assert.isNull(handler,"No file reader handler on fake file");
	}
	@Test
	public void fileReaderReadFile(){
		FileReaderHandler fileReaderHandler = new FileReaderHandler("src/test/resources/in.txt");
		fileReaderHandler.open();
		BufferedReader handler = fileReaderHandler.getHandler();
		Assert.isTrue(handler.lines()!=null,"File reader handle file");
		fileReaderHandler.close();
	}
	@Test
	public void fileWriterWriteFile() throws IOException {
		File f = new File("src/test/resources/tmp.txt");
		if (f.exists()) {
			 f.delete();
		}
		FileWriterHandler fileWriterHandler = new FileWriterHandler("src/test/resources/tmp.txt");
		fileWriterHandler.open();
		OutputStream stream = fileWriterHandler.getHandler();
		String s = "Hello world";
		stream.write(s.getBytes());
		fileWriterHandler.close();
		FileReaderHandler fileReaderHandler = new FileReaderHandler("src/test/resources/tmp.txt");
		fileReaderHandler.open();
		BufferedReader handler = fileReaderHandler.getHandler();
		Assert.isTrue(handler.lines().findFirst().get().equals(s), "File reader handle file");
		fileReaderHandler.close();
	}
	@Test
	public void checkAnalyzerResultCorrect() throws JsonProcessingException {
		FileReaderHandler fileReaderHandler = new FileReaderHandler("src/test/resources/in.txt");
		Analyzer analyzer = new DeltaAnalyzer(fileReaderHandler);
		AnalyzerResult analyzerResult = analyzer.analyzeData();
		Map<String, Set<Pair<String, String>>> result =(Map<String, Set<Pair<String, String>>>) analyzerResult.getResult();
		String expectedResult="{\"result\":{\"Naomiiseatingata\":[{\"restaurant\":\"01-01-2012 20:12:39 Naomi is eating at a restaurant\"},{\"diner\":\"03-01-2012 10:14:00 Naomi is eating at a diner\"}],\"isgettingintothecar\":[{\"George\":\"02-01-2012 09:13:15 George is getting into the car\"},{\"Naomi\":\"01-01-2012 19:45:00 Naomi is getting into the car\"}],\"iseatingatadiner\":[{\"Naomi\":\"03-01-2012 10:14:00 Naomi is eating at a diner\"},{\"George\":\"02-01-2012 10:14:00 George is eating at a diner\"}]}}";
		ObjectMapper objectMapper = new ObjectMapper();
		String actualResult = objectMapper.writeValueAsString(analyzerResult);
		Assert.isTrue(actualResult.equals(expectedResult),"Correct result");
	}
	@Test
	public void checkAnalyzerRunnerCorrect() throws IOException {
		AnalyzerRunner analyzerRunner = new InvestigatorAnalyzerRunner("src/test/resources/in.txt","src/test/resources/tmp.txt");
		analyzerRunner.execute();
		Path tmp = Path.of("src/test/resources/tmp.txt");
		Path out = Path.of("src/test/resources/out.txt");
		String expectedResult = Files.readString(out);
		String actualResult = Files.readString(tmp);
		Assert.isTrue(actualResult.equals(expectedResult),"Correct result");
	}
}
