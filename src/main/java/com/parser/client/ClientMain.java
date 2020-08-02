package com.parser.client;

import com.parser.driver.Parser;
import com.parser.utilities.CmdConfig;
import com.parser.driver.HTML_Parser;
import com.parser.utilities.JSONBuilder;
import org.apache.commons.cli.*;
import java.io.IOException;
import java.util.HashMap;

public class ClientMain {


    public static void main(String args[]) throws ParseException, IOException {

        CommandLine cmd = CmdConfig.CmdArgConfig(args);
        CmdConfig.CmdArgs(cmd);
        Parser html_parser = new HTML_Parser(CmdConfig.getInput_path(),CmdConfig.getConfig_path());
        HashMap<String,Object> json_obj = html_parser.parser();
        new JSONBuilder(CmdConfig.getOutput_path()).JsonFileDump(json_obj);


    }
}
