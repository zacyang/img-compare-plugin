package com.yang.icompare.core;

import static com.google.common.collect.Lists.newArrayList;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.yang.icompare.core.comparator.PixelComparator;
import com.yang.icompare.core.config.Config;
import com.yang.icompare.core.generator.OverlapDiffGenerator;
import com.yang.icompare.extractor.CompareCandidatesExtractor;

public class ConfigurableComparator {
    final private PixelComparator comparator;
    final private OverlapDiffGenerator generator;
    final private CompareCandidatesExtractor extractor;
    final private Config config;

    public ConfigurableComparator(final PixelComparator comparator,
                                  final OverlapDiffGenerator generator,
                                  final CompareCandidatesExtractor extractor,
                                  final Config config) {
        this.comparator = comparator;
        this.generator = generator;
        this.extractor = extractor;
        this.config = config;
    }


    public List<Result> compare() throws IOException {
        final Map<String, List<File>> testCandidateMap =
                extractor.extractorCompareCandidateFiles(
                        config.getFirstCompareCandidateDir(),
                        config.getSecondCompareCandidateDir());

        final List<Result> results = newArrayList();

        new File(config.getOutPutDir()).mkdir();
        testCandidateMap.forEach((s, files) -> results.add(generateComparingReport(files.get(0), files.get(1), config.getOutPutDir())));

        return results;
    }

    private Result generateComparingReport(File first, File second, String outPutDir) {
        Result result = this.comparator.compare(first, second, null);
        if (!result.isIdentical()) {
            result = generator.report(first, second, outPutDir, result);
        }
        return result;
    }
}
