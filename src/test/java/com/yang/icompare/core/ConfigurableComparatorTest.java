package com.yang.icompare.core;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.yang.icompare.core.config.Config;
import com.yang.icompare.core.config.ConfigInitializer;
import com.yang.icompare.extractor.CompareCandidatesExtractor;
import com.yang.icompare.core.generator.OverlapDiffGenerator;
import com.yang.icompare.core.comparator.PixelComparator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class ConfigurableComparatorTest {

    private ConfigurableComparator classUnderTest;
    @Mock
    private OverlapDiffGenerator generator;
    @Mock
    private PixelComparator comparator;
    @Mock
    private ConfigInitializer configInitializer;
    @Mock
    private Config config;
    @Mock
    private Result result;
    @Mock
    private CompareCandidatesExtractor candidateExtractor;
    @Mock
    private File file1;
    @Mock
    private File file2;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        configInit();

        String configPath = "/configPath";

        classUnderTest = new ConfigurableComparator(comparator, generator, candidateExtractor, configInitializer, configPath);
    }

    @Test
    public void shouldGenerateResultWhenConfigCorrectAndDiffFound() throws Exception {
        when(result.isIdentical()).thenReturn(false);
        when(comparator.compare(any(File.class),any(File.class), any(Result.class) )).thenReturn(result);
        when(generator.report(any(File.class),any(File.class), anyString(), any(Result.class))).thenReturn(result);
        Map<String, List<File>> candidateMap = newHashMap();
        candidateMap.put("test1", newArrayList(file1, file2));

        when(candidateExtractor.extractorCompareCandidateFiles(any(String.class), any(String.class))).thenReturn(candidateMap);

        List<Result> results = classUnderTest.compare();

        assertThat(results, hasItem(result));
    }

    @Test
    public void shouldGenerateResultWithNoDiffReportWhenConfigCorrectAndNoDiffFound() throws Exception {
        when(result.isIdentical()).thenReturn(true);
        when(comparator.compare(any(File.class),any(File.class), any(Result.class) )).thenReturn(result);
        Map<String, List<File>> candidateMap = newHashMap();
        candidateMap.put("test1", newArrayList(file1, file2));

        when(candidateExtractor.extractorCompareCandidateFiles(any(String.class), any(String.class))).thenReturn(candidateMap);

        List<Result> results = classUnderTest.compare();

        verifyNoMoreInteractions(generator);
        assertThat(results.size(), is(1));
    }


    @Test
    public void shouldReturnEmptyResultWhenNoMatchingComparingCandidateFound() throws Exception {
        when(result.isIdentical()).thenReturn(true);
        when(comparator.compare(any(File.class),any(File.class), any(Result.class) )).thenReturn(result);
        Map<String, List<File>> candidateMap = newHashMap();

        when(candidateExtractor.extractorCompareCandidateFiles(any(String.class), any(String.class))).thenReturn(candidateMap);

        List<Result> results = classUnderTest.compare();

        verifyNoMoreInteractions(generator);
        assertThat(results.size(), is(0));
    }

    private void configInit() {
        when(configInitializer.initConfig(anyString())).thenReturn(config);
        when(config.getFirstCompareCandidateDir()).thenReturn("");
        when(config.getSecondCompareCandidateDir()).thenReturn("");
        when(config.getOutPutDir()).thenReturn("");
    }


}