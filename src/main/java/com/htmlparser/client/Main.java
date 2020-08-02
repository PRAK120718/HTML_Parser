package com.htmlparser.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.htmlparser.utilities.HTML_Parser;
import org.apache.commons.cli.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Main {

    static String input_path;
    static String config_path;
    static String output_path;

    public static void JsonFileDump(HashMap<String,String> json_obj,String output_path) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json_obj);
        System.out.println(jsonString);
        FileWriter file = new FileWriter(output_path);
        file.write(jsonString);
        file.close();

    }
    public static Options CmdArgConfig(){

        Options Options = new Options();
        Option optarg1 = Option.builder("i").optionalArg(true).numberOfArgs(1).build();
        Option optarg2 = Option.builder("c").optionalArg(true).numberOfArgs(1).build();
        Option optarg3 = Option.builder("o").optionalArg(true).numberOfArgs(1).build();
        Options.addOption(optarg1);
        Options.addOption(optarg2);
        Options.addOption(optarg3);
        return Options;
    }
    public static void CmdArgs(CommandLine cmd){

        if(cmd.hasOption("i"))
            input_path = cmd.getOptionValue("i");
        else
            input_path = "/Users/prakarsh/Desktop/HTML_Parser/src/main/resources/HTML.html";

        if(cmd.hasOption("c"))
            config_path = cmd.getOptionValue("c");
        else
            config_path = "/Users/prakarsh/Desktop/HTML_Parser/src/main/resources/reference.conf";

        if(cmd.hasOption("o"))
            output_path = cmd.getOptionValue("o");
        else
            output_path = "/Users/prakarsh/Desktop/HTML_Parser/src/main/resources/output.json";
    }

    public static void main(String args[]) throws ParseException, IOException {

        Options Options = CmdArgConfig();
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse( Options, args);
        CmdArgs(cmd);
        HTML_Parser html_parser = new HTML_Parser(input_path,config_path,output_path);
        HashMap<String,String> json_obj = html_parser.Parser();
        JsonFileDump(json_obj,output_path);

    }
}
