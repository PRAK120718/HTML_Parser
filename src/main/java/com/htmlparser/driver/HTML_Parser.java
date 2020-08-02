package com.htmlparser.driver;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.xsoup.Xsoup;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTML_Parser {

    public static void main(String args[]) throws IOException {


        JSON_Obj hp = new JSON_Obj();
        File input = new File("/Users/prakarsh/Desktop/HTML_Parser/src/main/resources/HTML.html");
        Document doc= Jsoup.parse(input,"UTF-8");
         Element table = doc.body();


         // fetching the property address

        Elements address = table.getElementsByAttributeValueMatching("style", "italic");
        for (Element x : address) {
            String pattern = x.html();
            Pattern p = Pattern.compile("[0-9]{3,}.*");
            Matcher matcher = p.matcher(pattern);
            if (matcher.find()) {
                pattern = matcher.group();
                pattern = pattern.replace("\"", "");
            }
            Elements realtor = table.getElementsByAttributeValueMatching("href", "http://email.realtor.com.*");
            for (Element y : realtor) {
                String value = y.html();
                if (value.toLowerCase().contains(pattern.toLowerCase())) {
                    hp.property_address = value;
                }
            }
        }



        //fetching buyer email,contact

        //xsoup to use XPath

        Elements contact = Xsoup.compile("/html/body/table/tbody/tr[3]/td/table/tbody/tr[2]/td[2]/table/tbody/tr[3]/td/font/a").evaluate(doc).getElements();
        hp.buyer_contact = contact.html();
        Elements email = Xsoup.compile("/html/body/table/tbody/tr[3]/td/table/tbody/tr[2]/td[2]/table/tbody/tr[4]/td/font/a").evaluate(doc).getElements();
        hp.buyer_email = email.html();
        Elements name = Xsoup.compile("/html/body/table/tbody/tr[3]/td/table/tbody/tr[1]/td[1]/table/tbody/tr[8]/td[2]/font/strong").evaluate(doc).getElements();
        hp.buyer_name = name.html();
        Elements beds = Xsoup.compile("/html/body/table/tbody/tr[8]/td[2]/table/tbody/tr/td/table/tbody/tr[2]/td/font[3]/strong[1]").evaluate(doc).getElements();
        hp.property_beds =beds.html();
        Elements baths = Xsoup.compile("/html/body/table/tbody/tr[8]/td[2]/table/tbody/tr/td/table/tbody/tr[2]/td/font[3]/strong[2]").evaluate(doc).getElements();
        hp.property_baths = baths.html();
//        Elements body_buyer=table.getElementsByAttributeValueMatching("style","border: 1px solid #ddd.*");
//        for( Element body: body_buyer) {
//
//
//            Elements buyer_contact = body.getElementsByAttributeValueMatching("href", "tel.*");
//
//            for (Element x : buyer_contact) {
//
////                    System.out.println(x.html());
//                    hp.buyer_contact=x.html();
//
//            }
//            Elements buyer_mail = body.getElementsByAttributeValueMatching("href","mailto.*");
//            for (Element x : buyer_mail) {
////                System.out.println(x.html());
//                hp.buyer_email = x.html();
//            }
//
//
//            break;
//        }
//
//        //fetching buyer name
//        Elements body_property=table.getElementsByClass("font16");
//        for( Element body: body_property) {
//
//            if(body.html().contains("Name")){
//                Elements vals = body.getElementsByTag("Strong");
//                for(Element name_val : vals)
//                {
////                    System.out.println(name_val.html());
//                    hp.buyer_name=name_val.html();
//                }
//            }
//        }
//
//        //fetching property details
//        Elements property_details = table.getElementsByClass("font12");
//        for( Element body: property_details) {
//
//
//            if(body.html().contains("Beds ")){
//
//                Elements vals = body.getElementsByTag("Strong");
//                for(Element name_val : vals)
//                {
//                    hp.property[i++]=name_val.html();
////                    System.out.println(hp.property[i-1]);
//                }
//            }
//
//        }
//        for(String x : hp.property)
//        {
//            System.out.println(x);
//        }
//        System.out.println(hp.buyer_contact);
//        System.out.println(hp.buyer_email);
//        System.out.println(hp.buyer_name);
//
        //Creating the ObjectMapper object
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(hp);
        System.out.println(jsonString);

    }

}
