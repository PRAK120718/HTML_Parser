package com.parser.driver;

import org.apache.commons.cli.ParseException;

import java.io.IOException;
import java.util.HashMap;

public interface Parsable {
    public HashMap<String,Object> parser() throws IOException, ParseException;
}
