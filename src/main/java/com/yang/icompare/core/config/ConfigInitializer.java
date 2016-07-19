package com.yang.icompare.core.config;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import org.apache.commons.lang3.StringUtils;

public class ConfigInitializer {
    private  Config generateDefaultConfig() {
        try {
            return getConfigFromFile(new File(Resources.getResource("application.yaml").getFile()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
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
