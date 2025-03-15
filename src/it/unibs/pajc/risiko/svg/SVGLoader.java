package src.it.unibs.pajc.risiko.svg;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.batik.dom.util.DOMUtilities;
import org.w3c.dom.Document;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;

public class SVGLoader {
    public static Document loadSVGDocument(String filePath) throws IOException {
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);
        byte[] svgBytes = Files.readAllBytes(Paths.get(filePath));
        ByteArrayInputStream svgInputStream = new ByteArrayInputStream(svgBytes);
        return factory.createDocument(null, svgInputStream);
    }

    public static void printSVGDocument(Document document) {
        try {
            StringWriter writer = new StringWriter();
            DOMUtilities.writeDocument(document, writer);
            System.out.println(writer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}