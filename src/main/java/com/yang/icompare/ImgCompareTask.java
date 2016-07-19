package com.yang.icompare;

import java.io.IOException;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.TaskExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImgCompareTask extends DefaultTask {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @TaskAction
    public void compareImages() throws TaskExecutionException {
        log.info("start comparing...");

        ImgComparePluginExtension extension = getProject().getExtensions().findByType(ImgComparePluginExtension.class);

        Application compartor = new Application();
        try {
            compartor.compare();
        } catch (IOException e) {
            throw new TaskExecutionException(this, e);
        }
    }

}
