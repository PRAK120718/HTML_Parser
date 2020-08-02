package com.parser.utilities;

import org.apache.commons.cli.*;

public class CmdConfig {

    static private String input_path;
    static private String config_path;
    static private String output_path;

    public static String getInput_path() {
        return input_path;
    }

    public static void setInput_path(String input_path) {
        CmdConfig.input_path = input_path;
    }

    public static String getConfig_path() {
        return config_path;
    }

    public static void setConfig_path(String config_path) {
        CmdConfig.config_path = config_path;
    }

    public static String getOutput_path() {
        return output_path;
    }

    public static void setOutput_path(String output_path) {
        CmdConfig.output_path = output_path;
    }

    private static Options CmdOptions(){

        Options options = new Options();
        Option optarg1 = Option.builder("i").optionalArg(true).numberOfArgs(1).build();
        Option optarg2 = Option.builder("c").optionalArg(true).numberOfArgs(1).build();
        Option optarg3 = Option.builder("o").optionalArg(true).numberOfArgs(1).build();
        options.addOption(optarg1);
        options.addOption(optarg2);
        options.addOption(optarg3);
        return options;
    }

    public static CommandLine CmdArgConfig(String args[]) throws ParseException {

        Options options = CmdOptions();
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse( options, args);
        return cmd;
    }
    public static void CmdArgs(CommandLine cmd){

        if(cmd.hasOption("i"))
            setInput_path(cmd.getOptionValue("i"));
        else
            setInput_path("/Users/prakarsh/Desktop/HTML_Parser/src/main/resources/HTML.html");

        if(cmd.hasOption("c"))
            setConfig_path(cmd.getOptionValue("c"));
        else
            setConfig_path("/Users/prakarsh/Desktop/HTML_Parser/src/main/resources/reference.conf");

        if(cmd.hasOption("o"))
            setOutput_path(cmd.getOptionValue("o"));
        else
            setOutput_path("/Users/prakarsh/Desktop/HTML_Parser/src/main/resources/output.json");
    }

}
