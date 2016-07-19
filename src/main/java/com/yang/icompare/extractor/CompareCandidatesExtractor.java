package com.yang.icompare.extractor;

import static com.google.common.collect.Lists.newArrayList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CompareCandidatesExtractor {
    public static final String PNG = ".png";

    public Map<String, List<File>> extractorCompareCandidateFiles(String firstCompareCandidateDir, String secondCompareCandidateDir) throws IOException {
        final List<File> firstCandidateOfImgsFiles = getCandidateImgFiles(firstCompareCandidateDir);
        final List<File> secondCandidateOfImgsFiles = getCandidateImgFiles(secondCompareCandidateDir);
        final Map<String, List<File>> testCandidateMap = new HashMap<>();

        //get candidate files
        firstCandidateOfImgsFiles.forEach(file -> {
            String firstSetFileName = file.getName();
            Optional<File> matches = secondCandidateOfImgsFiles.stream().filter(file1 -> {
                String secondSetFileName = file1.getName();
                return firstSetFileName.equalsIgnoreCase(secondSetFileName);
            }).findFirst();

            if (matches.isPresent()) {
                testCandidateMap.put(firstSetFileName, newArrayList(file, matches.get()));
            }
        });
        return testCandidateMap;
    }


    private List<File> getCandidateImgFiles(final String dir) throws IOException {
        return Files.walk(new File(dir).toPath())
                .filter(path -> !path.getFileName().endsWith(PNG))
                .map(Path::toFile)
                .filter(f->!f.isDirectory())
                .collect(Collectors.toList());
    }
}
