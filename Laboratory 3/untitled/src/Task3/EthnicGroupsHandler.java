package Task3;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.LinkedList;
import java.util.List;

public class EthnicGroupsHandler extends DefaultHandler {
    private final List<String> ethnicList;
    private boolean isInEthnicGroup;

    public EthnicGroupsHandler() {
        this.ethnicList = new LinkedList<>();
    }

    public List<String> getEthnicList() {
        this.ethnicList.sort(String::compareToIgnoreCase);
        return this.ethnicList;
    }

    @Override
    public void startDocument() throws SAXException {
        this.isInEthnicGroup = false;
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("Number of ethnic groups: " + this.ethnicList.size());
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("ethcty")) {
            this.isInEthnicGroup = true;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (this.isInEthnicGroup) {
            String ethnicGroup = new String(ch, start, length).trim();
            if (!this.ethnicList.contains(ethnicGroup)) {
                this.ethnicList.add(ethnicGroup);
            }
            this.isInEthnicGroup = false;
        }
    }
}