package com.parser.utilities;
import com.typesafe.config.Config;
import org.jsoup.nodes.Element;

public interface Search {

    String XpathSearch(String key, Config required_field_xpaths);
    String CustomSearch(String key, Element body);
}
