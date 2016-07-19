package com.yang.icompare.core;

import static com.google.common.collect.Lists.newArrayList;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.yang.icompare.core.config.Config;
import com.yang.icompare.core.config.ConfigInitializer;
import com.yang.icompare.extractor.CompareCandidatesExtractor;
import com.yang.icompare.core.generator.OverlapDiffGenerator;
import com.yang.icompare.core.comparator.PixelComparator;

public class ConfigurableComparator {
    final private Config config;
    final private PixelComparator pixelComparator;
    final private OverlapDiffGenerator overlapDiffGenerator;
    final private CompareCandidatesExtractor compareCandidatesExtractor;

    public ConfigurableComparator(PixelComparator pixelComparator,
                                  OverlapDiffGenerator overlapDiffGenerator,
                                  CompareCandidatesExtractor compareCandidatesExtractor,
                                  ConfigInitializer initializer,
                                  String configurationPath) {
        this.pixelComparator = pixelComparator;
        this.overlapDiffGenerator = overlapDiffGenerator;
        this.compareCandidatesExtractor = compareCandidatesExtractor;
        this.config = initializer.initConfig(configurationPath);
    }


    public List<Result> compare() throws IOException {
        //config
        final Map<String, List<File>> testCandidateMap =
                compareCandidatesExtractor.extractorCompareCandidateFiles(
                        config.getFirstCompareCandidateDir(),
                        config.getSecondCompareCandidateDir());

        //compare

        final List<Result> results = newArrayList();

        new File(config.getOutPutDir()).mkdir();
        testCandidateMap.forEach((s, files) -> results.add(generateComparingReport(files.get(0), files.get(1), config.getOutPutDir())));

        return results;
    }

    private Result generateComparingReport(File first, File second, String outPutDir) {
        Result result = this.pixelComparator.compare(first, second, null);
        if (!result.isIdentical()) {
            result = overlapDiffGenerator.report(first, second, outPutDir, result);
        }
        return result;
    }

}
