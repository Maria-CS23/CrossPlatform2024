import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class Task5 {
    public static void main(String[] args) {
        String xmlFilePath = "Popular_Names.xml";

        try {
            File inputFile = new File(xmlFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

            NodeList nodeList = doc.getElementsByTagName("name");
            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node node = nodeList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    if (element.getElementsByTagName("name").getLength() > 0 &&
                            element.getElementsByTagName("gender").getLength() > 0 &&
                            element.getElementsByTagName("count").getLength() > 0 &&
                            element.getElementsByTagName("rank").getLength() > 0) {
                        String name = element.getElementsByTagName("name").item(0).getTextContent();
                        String gender = element.getElementsByTagName("gender").item(0).getTextContent();
                        int count = Integer.parseInt(element.getElementsByTagName("count").item(0).getTextContent());
                        int rank = Integer.parseInt(element.getElementsByTagName("rank").item(0).getTextContent());

                        System.out.println("Name: " + name);
                        System.out.println("Gender: " + gender);
                        System.out.println("Count: " + count);
                        System.out.println("Rank: " + rank);
                        System.out.println("--------------------------");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}