# Data_Parser
Data parser takes in 3 inputs
* input data file formats like HTML , XML etc
* the information one wants from the specified data file 
* the output file path to store data in user specified format like json , csv etc.

## Implementation of HTML_JSON_Data_Parser
* Input data - .html file which has to scraped
* Configuration data - the information required and the Xpaths of information required
* Output data - Output file path as .json

##Configuration file
```
buyer{
  xpath {
    name = "/html/body/table/tbody/tr[3]/td/table/tbody/tr[1]/td[1]/table/tbody/tr[8]/td[2]/font/strong"
    email = "/html/body/table/tbody/tr[3]/td/table/tbody/tr[2]/td[2]/table/tbody/tr[4]/td/font/a"
    contact = "/html/body/table/tbody/tr[3]/td/table/tbody/tr[2]/td[2]/table/tbody/tr[3]/td/font/a"
  }
}

property{
  xpath {
    beds = "/html/body/table/tbody/tr[8]/td[2]/table/tbody/tr/td/table/tbody/tr[2]/td/font[3]/strong[1]"
    baths = "/html/body/table/tbody/tr[8]/td[2]/table/tbody/tr/td/table/tbody/tr[2]/td/font[3]/strong[2]"
  }
}

required {
  buyer : [name , email , contact]
  property : [address , beds , baths]
}
```

##Xpaths and Configuration file
[XPath stands for XML Path Language. It uses a non-XML syntax to provide a flexible way of addressing (pointing to) different parts of an XML/HTML document.](https://developer.mozilla.org/en-US/docs/Web/XPath)

Xpaths are one of the quickest and efficient ways to scrap data from Web Pages.

Project uses XPath to uniquely identify the address of an element in Web Page.
* Required Object - denotes the elements you are searching for 
* For every data element you find ```XPath``` and generate data Objects
* If Xpath not found for a data element, custom search additonal code implementation to the codebase is required .

##Generally XPaths are sufficient to scrap information

####```Disclaimer```

Efficient way to implement XPath search is to go for relative paths while browsers provide absolute paths which are comparatively inefficient.

##Steps to fetch XPaths for information scraping from HTML file for generic users 
* Load the HTML page in a Web Browser(Firefox , Chrome)
* Inspect Element the HTML page
* Select the information you want to fetch using Arrow Selector 
* Right click and copy the XPath for the element

#Steps to Execute
* Parser takes in 3 arguments where ```i``` denotes input file path, ```c``` denotes configuration file path,```o``` denotes output file path


    
* Create jar file for the project or directly execute through ```IDE``` (dont forget to pass program arguments by editing configuration)
* If arguments are not passed , default argument values are taken . They are

  * for ```-i``` - [```src/main/resources/HTML.html```](https://drive.google.com/file/d/1eJUXg-fQ6oQEBTn2eVor1InRH-lS6nW-/view?usp=sharing) ( link to HTML page )
  * for ```-c``` - ```src/main/resources/reference.conf```
  * for ```-o``` - ```src/main/resources/output.json```
  
* Dummy execution code -     ``` java -jar jar-path -i input-file-path -c configuration-file-path -o output-file-path ```
* Example execution code -   ``` java -jar HTML_Parser-1.0-SNAPSHOT-jar-with-dependencies.jar -i src/main/resources/HTML.html -c src/main/resources/reference.conf -o src/main/resources/output.json ```
* executes to give output in output.json file as
```
   {
     "baths" : "2",
     "address" : "43824 W Sagebrush Trl, Maricopa, AZ 85138",
     "contact" : "111-222-3333",
     "name" : "Test Lead",
     "beds" : "3",
     "email" : "testlead@gmail.com"
   }
```




