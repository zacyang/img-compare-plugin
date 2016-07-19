package com.yang.icompare.reporter;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.List;

import com.yang.icompare.core.Result;
import com.yang.icompare.core.comparator.PixelDiffResult;
import com.yang.icompare.core.config.Config;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class HtmlReporterTest {
    private HtmlReporter classUnderTest;
    @Mock
    private ResultToJsonConverter converter;
    @Mock
    private Config config;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        classUnderTest = new HtmlReporter(config, converter);
    }

    @Test
    public void generateReports() throws Exception {
        List<Result> results = newArrayList(new PixelDiffResult(false, "/somePath/diff.png"),
                new PixelDiffResult(true, null));
        when(converter.generateJson(anyList())).thenReturn("{\n  \"summary\" : {\n    \"hasDifference\" : true,\n    \"totalDifference\" : 1\n  },\n  \"results\" : [ {\n    \"isIdentical\" : false,\n    \"diffImgLocation\" : \"/somePath/diff.png\"\n  }, {\n    \"isIdentical\" : true,\n    \"diffImgLocation\" : null\n  } ]\n}" );
        String result = classUnderTest.toJavaScriptObject(results);

        String expectedJSobject = "results={\n" +
                "  \"summary\" : {\n" +
                "    \"hasDifference\" : true,\n" +
                "    \"totalDifference\" : 1\n" +
                "  },\n" +
                "  \"results\" : [ {\n" +
                "    \"isIdentical\" : false,\n" +
                "    \"diffImgLocation\" : \"/somePath/diff.png\"\n" +
                "  }, {\n" +
                "    \"isIdentical\" : true,\n" +
                "    \"diffImgLocation\" : null\n" +
                "  } ]\n" +
                "}";


        assertThat(result, is(expectedJSobject));
    }

}