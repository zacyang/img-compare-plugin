package com.yang.icompare.reporter;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.List;

import com.yang.icompare.core.Result;
import com.yang.icompare.core.comparator.PixelDiffResult;
import org.junit.Before;
import org.junit.Test;

public class ResultToJsonConverterTest {
    private ResultToJsonConverter classUnderTest;

    @Before
    public void setUp() throws Exception {
        classUnderTest = new ResultToJsonConverter();
    }

    @Test
    public void shouldGenerateExpectJson() throws Exception {
        List<Result> results = newArrayList(new PixelDiffResult(false, "/somePath/diff.png"),
                new PixelDiffResult(true, null));
        String generateJson = classUnderTest.generateJson(results);
        assertThat(generateJson, is("{\n  \"summary\" : {\n    \"hasDifference\" : true,\n    \"totalDifference\" : 1\n  },\n  \"results\" : [ {\n    \"isIdentical\" : false,\n    \"diffImgLocation\" : \"/somePath/diff.png\"\n  }, {\n    \"isIdentical\" : true,\n    \"diffImgLocation\" : null\n  } ]\n}" ));
    }
}