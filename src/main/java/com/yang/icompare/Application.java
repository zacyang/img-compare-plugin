package com.yang.icompare;

import static com.yang.icompare.CommandLineParser.getConfiguration;

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
import org.apache.commons.cli.ParseException;

public class Application {

    public static void main(String[] args) throws IOException, ParseException {
        ConfigInitializer configInitializer = new ConfigInitializer();
        Config config = configInitializer.initConfig(getConfiguration(args));
        new Application().compare(config);
    }

    public void compare(Config config) throws IOException {
        ConfigurableComparator configurableComparator = new ConfigurableComparator(new PixelComparator(),
                new OverlapDiffGenerator(),
                new CompareCandidatesExtractor(),
                config);

        List<Result> results = configurableComparator.compare();

        new HtmlReporter(config, new ResultToJsonConverter()).generateReports(results);
    }
}
