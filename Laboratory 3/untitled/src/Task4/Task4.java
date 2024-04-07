package Task4;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Task4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the ethnic group: ");
        String ethnicGroup = scanner.nextLine();
        System.out.print("Enter number of names: ");
        int namesCount = scanner.nextInt();
        scanner.nextLine();

        String xmlFilePath = "Popular_Baby_Names_NY.xml";

        try {
            List<NameInfo> nameInfoList = parseXML(xmlFilePath, ethnicGroup);
            Collections.sort(nameInfoList, Comparator.comparingInt(info -> info.rank));

            List<NameInfo> topNames = nameInfoList.subList(0, Math.min(namesCount, nameInfoList.size()));

            for (NameInfo nameInfo : topNames) {
                System.out.println(nameInfo);
            }

            createNewXML(topNames, "Popular_Names.xml");

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private static List<NameInfo> parseXML(String filePath, String ethnicGroup)
            throws ParserConfigurationException, SAXException, IOException {
        List<NameInfo> nameInfoList = new ArrayList<>();
        Set<String> processedNames = new HashSet<>();

        DefaultHandler handler = new DefaultHandler() {
            String currentElement;
            String currentName;
            String currentGender;
            String currentEthnicGroup;
            int currentCount;
            int currentRating;

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                currentElement = qName;
            }

            @Override
            public void characters(char[] ch, int start, int length) throws SAXException {
                if (currentElement.equals("ethcty")) {
                    currentEthnicGroup = new String(ch, start, length).trim();
                } else if (currentElement.equals("nm")) {
                    currentName = new String(ch, start, length).trim();
                } else if (currentElement.equals("gndr")) {
                    currentGender = new String(ch, start, length).trim();
                } else if (currentElement.equals("cnt")) {
                    currentCount = Integer.parseInt(new String(ch, start, length).trim());
                } else if (currentElement.equals("rnk")) {
                    currentRating = Integer.parseInt(new String(ch, start, length).trim());
                }
            }

            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {
                if (qName.equals("row") && currentName != null && currentGender != null && currentEthnicGroup != null) {
                    if (currentEthnicGroup.equalsIgnoreCase(ethnicGroup) && !processedNames.contains(currentName)) {
                        nameInfoList.add(new NameInfo(currentName, currentGender, currentCount, currentRating));
                        processedNames.add(currentName);
                    }
                    currentName = null;
                    currentGender = null;
                    currentEthnicGroup = null;
                    currentCount = 0;
                    currentRating = 0;
                }
            }
        };

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(new File(filePath), handler);

        return nameInfoList;
    }

    private static void createNewXML(List<NameInfo> nameInfoList, String outputPath) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("names");
            doc.appendChild(rootElement);

            for (NameInfo nameInfo : nameInfoList) {
                Element nameElement = doc.createElement("name");
                rootElement.appendChild(nameElement);

                Element name = doc.createElement("name");
                name.appendChild(doc.createTextNode(nameInfo.name));
                nameElement.appendChild(name);

                Element gender = doc.createElement("gender");
                gender.appendChild(doc.createTextNode(nameInfo.gender));
                nameElement.appendChild(gender);

                Element count = doc.createElement("count");
                count.appendChild(doc.createTextNode(String.valueOf(nameInfo.count)));
                nameElement.appendChild(count);

                Element rating = doc.createElement("rank");
                rating.appendChild(doc.createTextNode(String.valueOf(nameInfo.rank)));
                nameElement.appendChild(rating);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(outputPath));
            transformer.transform(source, result);

            System.out.println("\nNew XML file created: " + outputPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}