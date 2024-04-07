package Task0;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.Set;

public class Task0 {
    public static void main(String[] args) {
        try {
            File inputFile = new File("Popular_Baby_Names_NY.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            FindTagsHandler handler = new FindTagsHandler();
            parser.parse(inputFile, handler);

            Set<String> tags = handler.getTags();
            System.out.println("Tags:\n");
            for (String tag : tags) {
                System.out.println(tag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}