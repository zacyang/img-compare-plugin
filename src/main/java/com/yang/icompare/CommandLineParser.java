package com.yang.icompare;

import com.google.common.base.Strings;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

public class CommandLineParser {
    private static final String APPLICATION_YAML = "application.yaml";

    public static String getConfiguration(final String[] args) throws ParseException {
        Options options = new Options();
        options.addOption("f", true, "configuration yaml file");

        org.apache.commons.cli.CommandLineParser parser = new PosixParser();
        CommandLine cmd = parser.parse( options, args);

        if(cmd.hasOption("f")){
            String configuration = cmd.getOptionValue("f");
            return Strings.isNullOrEmpty(configuration)? APPLICATION_YAML :configuration;
        }
        return APPLICATION_YAML;
    }
}
