package com.parser.driver;

import com.parser.utilities.Parser;
import com.parser.utilities.Search;
import com.typesafe.config.Config;
import org.apache.commons.cli.ParseException;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class XML_Parser extends Parser implements Search {

    public XML_Parser(String input_path, String config_path) throws ParseException, IOException {
        super(input_path, config_path);
    }

    public String XpathSearch(String key, Config required_field_xpaths) {
        return null;
    }

    public String CustomSearch(String key, Element body) {
        return null;
    }
}
