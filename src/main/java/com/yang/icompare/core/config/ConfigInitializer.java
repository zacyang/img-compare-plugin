package com.yang.icompare.core.config;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import org.apache.commons.lang3.StringUtils;

public class ConfigInitializer {

    private static final String APPLICATION_YAML = "application.yaml";
    private static final Config CONFIG = new Config("base", "after", "result", "data.js");
    private static final File DEFAULT_CONFIG_FILE = new File(Resources.getResource(APPLICATION_YAML).getFile());

    public Config initConfig(String filePath) {
        try {
            return getConfigFromFile(getConfigurationFile(filePath));
        } catch (IOException e) {
            return CONFIG;
        }
    }

    private File getConfigurationFile(String config) {
        try {
            File file = new File(Resources.getResource(config).getFile());
            return file;
        } catch (IllegalArgumentException e) {
            return DEFAULT_CONFIG_FILE;
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
