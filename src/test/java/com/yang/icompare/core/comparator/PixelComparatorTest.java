package com.yang.icompare.core.comparator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;

import com.google.common.io.Resources;
import com.yang.icompare.core.Result;
import org.junit.Before;
import org.junit.Test;

public class PixelComparatorTest {

    private PixelComparator comparator;

    @Before
    public void setUp() throws Exception {
        comparator = new PixelComparator();
    }

    @Test
    public void shouldReturnFalseForTotallyDifferentImg() throws Exception {
        File textOne = new File(Resources.getResource("images/aconex-default-01.png").getFile());
        File textTwo = new File(Resources.getResource("images/google-default-01.png").getFile());

        Result result = comparator.compare(textOne, textTwo, null);

        assertThat(result.isIdentical(), is(false));
    }

    @Test
    public void shouldReturnTrueForSamePage() throws Exception {
        File textOne = new File(Resources.getResource("images/aconex-default-01.png").getFile());
        File textTwo = new File(Resources.getResource("images/aconex-default-01.png").getFile());

        Result result = comparator.compare(textOne, textTwo, null);

        assertThat(result.isIdentical(), is(true));
    }

    @Test
    public void shouldReturnFalseForManualChangedIconOnPage() throws Exception {
        File textOne = new File(Resources.getResource("images/google-default-01.png").getFile());
        File textTwo = new File(Resources.getResource("images/google-changed-icon-position.png").getFile());

        Result result = comparator.compare(textOne, textTwo, null);

        assertThat(result.isIdentical(), is(false));
    }

}