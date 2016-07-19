package com.yang.icompare.reporter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.yang.icompare.core.Result;
import com.yang.icompare.core.config.Config;

public class HtmlReporter implements Report {
    private final String pathOfDataJs;
    private final ResultToJsonConverter converter;

    public HtmlReporter(Config config, final ResultToJsonConverter converter) {
        this.converter = converter;
        this.pathOfDataJs = config.getPathOfDataJs();
    }

    public void generateReports(final List<Result> result) {

        injectToTargetReport(toJavaScriptObject(result), pathOfDataJs);
    }

    public String toJavaScriptObject(final List<Result> result) {
        String json = converter.generateJson(result);
        return "results=" + json;
    }

    private void injectToTargetReport(String json, String pathOfReportingFile) {
        Path file = Paths.get(pathOfReportingFile);
        try {
            Files.write(file, json.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
