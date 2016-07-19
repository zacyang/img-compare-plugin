package com.yang.icompare.core.generator;

import java.io.File;

import com.google.common.io.Resources;
import com.yang.icompare.core.comparator.PixelDiffResult;
import org.junit.Before;
import org.junit.Test;

public class OverlapDiffGeneratorTest {

    private OverlapDiffGenerator overlapDiffGenerator;

    @Before
    public void setUp() throws Exception {
        overlapDiffGenerator = new OverlapDiffGenerator();
    }

    @Test
    public void shouldGenerateReport() throws Exception {
        File file1 = new File(Resources.getResource("images/google-default-01.png").getFile());
        File file2 = new File(Resources.getResource("images/google-changed-icon-position.png").getFile());
        String outDir = "/home/yyang/projects/babylon/tmp/";
        overlapDiffGenerator.report(file1, file2, outDir, new PixelDiffResult(false, ""));
    }

}