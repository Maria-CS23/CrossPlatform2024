import java.io.StringWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XMLUtils {
    public static String toXML(List<Participant> participants) throws Exception {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();

        Element rootElement = doc.createElement("RegisteredConferees");
        doc.appendChild(rootElement);

        for (Participant participant : participants) {
            Element conferee = doc.createElement("Conferee");
            rootElement.appendChild(conferee);

            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(participant.getName()));
            conferee.appendChild(name);

            Element familyName = doc.createElement("familyName");
            familyName.appendChild(doc.createTextNode(participant.getFamilyName()));
            conferee.appendChild(familyName);

            Element placeOfWork = doc.createElement("placeOfWork");
            placeOfWork.appendChild(doc.createTextNode(participant.getPlaceOfWork()));
            conferee.appendChild(placeOfWork);

            Element reportTitle = doc.createElement("reportTitle");
            reportTitle.appendChild(doc.createTextNode(participant.getReportTitle()));
            conferee.appendChild(reportTitle);

            Element email = doc.createElement("email");
            email.appendChild(doc.createTextNode(participant.getEmail()));
            conferee.appendChild(email);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "Windows-1251");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        DOMSource source = new DOMSource(doc);

        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);

        transformer.transform(source, result);

        return writer.toString();
    }

    public static List<Participant> fromXML(String xml) throws Exception {
        List<Participant> participants = new ArrayList<>();

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(new InputSource(new StringReader(xml)));

        NodeList nodeList = doc.getElementsByTagName("Conferee");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);

            String name = element.getElementsByTagName("name").item(0).getTextContent();
            String familyName = element.getElementsByTagName("familyName").item(0).getTextContent();
            String placeOfWork = element.getElementsByTagName("placeOfWork").item(0).getTextContent();
            String reportTitle = element.getElementsByTagName("reportTitle").item(0).getTextContent();
            String email = element.getElementsByTagName("email").item(0).getTextContent();

            Participant participant = new Participant(name, familyName, placeOfWork, reportTitle, email);
            participants.add(participant);
        }

        return participants;
    }
}