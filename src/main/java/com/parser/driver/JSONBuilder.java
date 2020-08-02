package com.parser.driver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parser.utilities.Builder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class JSONBuilder extends Builder {

    public JSONBuilder(String output_path){
        super(output_path);
    }
    public void JsonFileDump(HashMap<String,Object> json_obj) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json_obj);
        System.out.println(jsonString);
        FileWriter file = new FileWriter(getOutput_path());
        file.write(jsonString);
        file.close();

    }
}
