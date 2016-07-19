package com.yang.icompare;

import java.io.IOException;
import java.util.List;

import com.yang.icompare.core.ConfigurableComparator;
import com.yang.icompare.core.Result;
import com.yang.icompare.core.comparator.PixelComparator;
import com.yang.icompare.core.config.Config;
import com.yang.icompare.core.config.ConfigInitializer;
import com.yang.icompare.core.generator.OverlapDiffGenerator;
import com.yang.icompare.extractor.CompareCandidatesExtractor;
import com.yang.icompare.reporter.HtmlReporter;
import com.yang.icompare.reporter.ResultToJsonConverter;

public class Application {

    private static final String APPLICATION_YAML = "application.yaml";


    public static void main(String[] args) throws IOException {
        new Application().compare();
    }

    public void compare() throws  IOException{
        ConfigurableComparator configurableComparator = new ConfigurableComparator(new PixelComparator(),
                new OverlapDiffGenerator(),
                new CompareCandidatesExtractor(),
                new ConfigInitializer(),
                APPLICATION_YAML);

        List<Result> results = configurableComparator.compare();

        Config config = new ConfigInitializer().initConfig(APPLICATION_YAML);
        new HtmlReporter(config, new ResultToJsonConverter()).generateReports(results);
    }
}
