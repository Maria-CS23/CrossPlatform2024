import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Task1 extends DefaultHandler {
    private SAXParser parser;
    private FileInputStream stream;
    private int indent = 0;
    private int numElements;
    private int limit;

    public Task1(int limit) {
        this.limit = limit;
    }

    public Task1() {
        this(-1);
    }

    private void printIndent(int indentSize) {
        for (int s = 0; s < indentSize; s++) {
            System.out.print(" ");
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (this.limit > 0) {
            this.numElements += 1;
        }

        indent = indent + 4;
        printIndent(indent);
        System.out.println("<" + qName + ">");

        if (attributes.getLength() > 0) {
            printIndent(indent + 4);
            System.out.println("Attributes: ");
            for (int i = 0; i < attributes.getLength(); i++) {
                printIndent(indent + 8);
                System.out.println(attributes.getQName(i).trim() + " = " + attributes.getValue(i).trim());
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        printIndent(indent);
        System.out.println("<" + qName + ">");
        indent = indent - 4;

        if (this.limit > 0 && this.numElements >= this.limit) {
            throw new SAXException();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String str = new String(ch, start, length).trim();
        if (str.length() > 0) {
            printIndent(indent + 4);
            System.out.println(str);
        }
    }

    public void process(String fileName) throws ParserConfigurationException, SAXException, FileNotFoundException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        this.parser = factory.newSAXParser();
        this.stream = new FileInputStream(fileName);

        try {
            this.parser.parse(this.stream, this);
        } catch (SAXException e) {
            if (e.getMessage() != null && !e.getMessage().equals("Reached the limit of " + this.limit + " elements")) {
                throw e;
            }
        } finally {
            if (this.stream != null) {
                this.stream.close();
            }
        }
    }

    public static void main(String[] args) {
        Task1 handler = new Task1(16);
        try {
            handler.process("Popular_Baby_Names_NY.xml");
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}