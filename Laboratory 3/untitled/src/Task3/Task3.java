package Task3;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class Task3 {
    public static void main(String[] args) {
        EthnicGroupsHandler handler = new EthnicGroupsHandler();

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            FileInputStream stream = new FileInputStream("Popular_Baby_Names_NY.xml");
            parser.parse(stream, handler);
            printEthnicGroups(handler.getEthnicList());
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void printEthnicGroups(List<String> ethnicList) {
        for (String ethnicGroup : ethnicList) {
            System.out.println(ethnicGroup);
        }
    }
}