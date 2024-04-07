import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;

public class Task2 {
    public static void main(String[] args) {
        String xmlFilePath = "Popular_Baby_Names_NY.xml";
        String xsdFilePath = "Schema.xsd";

        if (validate(xmlFilePath, xsdFilePath)) {
            System.out.println("Document is valid");
        } else {
            System.out.println("Document is not valid");
        }
    }

    public static boolean validate(String xmlFilePath, String xsdFilePath){
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdFilePath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlFilePath)));
            return true;
        } catch (IOException | SAXException e) {
            e.printStackTrace();
            return false;
        }
    }
}