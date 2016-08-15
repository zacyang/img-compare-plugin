package com.yang.icompare.core.config;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import org.apache.commons.lang3.StringUtils;

public class ConfigInitializer {

    private static final String APPLICATION_YAML = "application.yaml";

    private  Config generateDefaultConfig() {
        try {
            URL resource = Resources.getResource(APPLICATION_YAML);
            return getConfigFromFile(new File(resource.getFile()));
        } catch (IOException e) {
            return new Config("/home/yyang/projects/mail-routing-ui/frontend/pre_screenshoots/screenshots/firefox", "/home/yyang/projects/mail-routing-ui/frontend/after_screenshots", "/home/yyang/projects/mail-routing-ui/frontend/result/", "/home/yyang/playground/com-aconex-yang-img-comparator/src/main/resources/data.js");
        }
    }

    public Config initConfig(String config) {
        try {
            return getConfigFromFile(new File(Resources.getResource(config).getFile()));
        } catch (IOException e) {
            return generateDefaultConfig();
        }
    }

    private Config getConfigFromFile(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Config config = mapper.readValue(file, Config.class);
        if (StringUtils.isEmpty(config.getOutPutDir())) {
            config.setOutPutDir(Files.createTempDir().getAbsolutePath());
        }
        return config;
    }
}
