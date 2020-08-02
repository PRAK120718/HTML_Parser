package com.parser.driver;

import org.apache.commons.cli.ParseException;
import java.io.IOException;
import java.util.HashMap;

public class Parser implements Parsable{
    private String input_path;

    public String getInput_path() {
        return input_path;
    }

    public void setInput_path(String input_path) {
        this.input_path = input_path;
    }

    public String getConfig_path() {
        return config_path;
    }

    public void setConfig_path(String config_path) {
        this.config_path = config_path;
    }

    private String config_path;


    public Parser(String input_path,String config_path) throws ParseException, IOException {

        this.input_path=input_path;
        this.config_path=config_path;

    }
    public HashMap<String,Object> parser() throws IOException, ParseException {
        System.out.println("Customization required");
        return new HashMap<String, Object>();
    }
}
