package com.yang.icompare.core.config;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class ConfigInitializerTest {
    private ConfigInitializer classUnderTest;
    @Before
    public void setUp() throws Exception {
        classUnderTest = new ConfigInitializer();
    }

    @Test
    public void shouldInitConfigurationWithGivenYamlFile() throws Exception {
        Config config = classUnderTest.initConfig("application-test.yaml");

        assertThat(config.getFirstCompareCandidateDir(), is("firstCompareCandidateDir"));
        assertThat(config.getSecondCompareCandidateDir(), is("secondCompareCandidateDir"));
        assertThat(config.getOutPutDir(), is("outPutDir"));
        assertThat(config.getPathOfDataJs(), is("pathOfDataJs"));
    }

    @Test
    public void shouldInitConfigurationWithDefaultConfigWhenGivenConfigFileNotFound() throws Exception {
        Config config = classUnderTest.initConfig("none-exists.file");

        assertThat(config.getFirstCompareCandidateDir(), is("/home/yyang/projects/mail-routing-ui/frontend/pre_screenshoots/screenshots/firefox"));
        assertThat(config.getSecondCompareCandidateDir(), is("/home/yyang/projects/mail-routing-ui/frontend/after_screenshots"));
        assertThat(config.getOutPutDir(), is("/home/yyang/projects/mail-routing-ui/frontend/result/"));
        assertThat(config.getPathOfDataJs(), is("/home/yyang/playground/com-aconex-yang-img-comparator/src/main/resources/data.js"));
    }

}
