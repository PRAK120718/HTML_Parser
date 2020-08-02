package com.parser.driver;
import com.parser.utilities.Search;
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

public class HTML_Parser extends Parser implements Search {


    private Config conf;
    private Document doc;
    private File file;

    public HTML_Parser(String input_path,String config_path) throws ParseException, IOException {

        super(input_path,config_path);
        this.file = new File(getInput_path());
        this.conf = ConfigFactory.load(getConfig_path());
        this.doc= Jsoup.parse(this.file,"UTF-8");
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }



    public String XpathSearch(String key,Config required_field_xpaths ){

        String required_field_xpath = required_field_xpaths.getValue(key).unwrapped().toString();
        Elements required_field_element = Xsoup.compile(required_field_xpath).evaluate(doc).getElements();
        String required_field_value = required_field_element.html();
        return required_field_value;

    }

    public String CustomSearch(String key,Element body){

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

    public HashMap<String,Object> parser() throws IOException, ParseException {


        Element body = doc.body();
        HashMap<String,Object> json_obj = new HashMap<String, Object>();
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
                    required_field_value = XpathSearch(key,required_field_xpaths);
                else
                    required_field_value = CustomSearch(key,body);

                json_obj.put(key,required_field_value);
            }
        }

        return json_obj;
    }

}
