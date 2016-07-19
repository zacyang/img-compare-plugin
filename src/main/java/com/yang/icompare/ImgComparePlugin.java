package com.yang.icompare;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class ImgComparePlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.getExtensions().create("ImgComparePlugin", ImgComparePluginExtension.class);
    }
}
