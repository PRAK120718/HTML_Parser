package com.htmlparser.utilities;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.cli.*;

public class HTML_Parser {


    private String input_path;
    private String config_path;
    private String output_path;
    private Config conf;
    private Document doc;
    private File file;

    public HTML_Parser(String input_path,String config_path,String output_path) throws ParseException, IOException {

        this.input_path=input_path;
        this.config_path=config_path;
        this.output_path=output_path;
        this.file = new File(input_path);
        this.conf = ConfigFactory.load(config_path);
        this.doc= Jsoup.parse(this.file,"UTF-8");
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }



    private String XpathSearch(String key,Document doc,Config required_field_xpaths ){

        String required_field_xpath = required_field_xpaths.getValue(key).unwrapped().toString();
        Elements required_field_element = Xsoup.compile(required_field_xpath).evaluate(doc).getElements();
        String required_field_value = required_field_element.html();
        return required_field_value;

    }

    private String CustomSearch(String key,Element body){

        String key_val = null;
        if(key.equals("address")){

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
                        key_val=value;
                    }
                }
            }

        }
        return key_val;
    }

    public HashMap<String,String> Parser() throws IOException, ParseException {


        Element body = doc.body();
        HashMap<String,String> json_obj = new HashMap<String, String>();
        Config required_fields = conf.getConfig("required");

        for( Map.Entry<String, ConfigValue> required_field : required_fields.entrySet())
        {

            Config required_field_xpaths = conf.getConfig(required_field.getKey()+".xpath");
            ConfigList required_field_keys = required_fields.getList(required_field.getKey());

            for(ConfigValue required_field_key : required_field_keys)
            {

                String key = required_field_key.unwrapped().toString();
                String required_field_value = null;

                json_obj.put(key,required_field_value);

                if(required_field_xpaths.hasPath(key))
                    required_field_value = XpathSearch(key,doc,required_field_xpaths);
                else
                    required_field_value = CustomSearch(key,body);

                json_obj.put(key,required_field_value);
            }
        }

        return json_obj;
    }

}
