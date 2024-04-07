package Task0;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashSet;
import java.util.Set;

public class FindTagsHandler extends DefaultHandler {
    private Set<String> tags = new HashSet<>();

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        tags.add(qName);
    }

    public Set<String> getTags() {
        return tags;
    }
}