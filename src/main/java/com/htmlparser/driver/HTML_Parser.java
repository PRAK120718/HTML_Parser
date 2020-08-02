package com.htmlparser.driver;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigList;
import com.typesafe.config.ConfigValue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.xsoup.Xsoup;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTML_Parser {

    public static void main(String args[]) throws IOException {


        String input_path = args[0];
        String config_path = args[1];
        String output_path = args[2];

        File input = new File(input_path);
        Config conf = ConfigFactory.load(config_path);
        Document doc= Jsoup.parse(input,"UTF-8");
        Element body = doc.body();
        HashMap<String,String> json_obj = new HashMap<String, String>();


        Config required_fields = conf.getConfig("required");


        for( Map.Entry<String, ConfigValue> required_field : required_fields.entrySet()){

            Config required_field_xpaths = conf.getConfig(required_field.getKey()+".xpath");
            ConfigList required_field_keys = required_fields.getList(required_field.getKey());

            for( ConfigValue required_field_key : required_field_keys){

                String key = required_field_key.unwrapped().toString();
                json_obj.put(key,null);
                if(required_field_xpaths.hasPath(key)){

                    String required_field_xpath = required_field_xpaths.getValue(key).unwrapped().toString();
                    Elements required_field_element = Xsoup.compile(required_field_xpath).evaluate(doc).getElements();
                    String required_field_value = required_field_element.html();
//                    System.out.println(required_field_value);
                    json_obj.put(key,required_field_value);
                }
                else
                {
                    if(key.equals("address")){
//                         fetching the property address
                    Elements address = body.getElementsByAttributeValueMatching("style", "italic");
                    for (Element x : address) {
                        String pattern = x.html();
                        Pattern p = Pattern.compile("[0-9]{3,}.*");
                        Matcher matcher = p.matcher(pattern);
                        if (matcher.find()) {
                            pattern = matcher.group();
                            pattern = pattern.replace("\"", "");
                            }
                        Elements realtor = body.getElementsByAttributeValueMatching("href", "http://email.realtor.com.*");
                        for (Element y : realtor)
                        {
                        String value = y.html();
                        if (value.toLowerCase().contains(pattern.toLowerCase())) {
                            json_obj.put("address",value);
                                }
                            }
                        }

                    }
                }
            }
        }


//
        //Creating the ObjectMapper object
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json_obj);
        System.out.println(jsonString);
        FileWriter file = new FileWriter(output_path);
        file.write(jsonString);
        file.close();


    }

}
