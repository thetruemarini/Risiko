package it.unibs.pajc.risiko.svg;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SVGParser {
    public static List<String> parsePaths(String filePath) throws IOException {
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);
        byte[] svgBytes = Files.readAllBytes(Paths.get(filePath));
        InputStream svgStream = new ByteArrayInputStream(svgBytes);
        Document document = factory.createDocument(null, svgStream);

        List<String> paths = new ArrayList<>();
        NodeList pathNodes = document.getElementsByTagName("path");
        for (int i = 0; i < pathNodes.getLength(); i++) {
            Element pathElement = (Element) pathNodes.item(i);
            paths.add(pathElement.getAttribute("d"));
        }
        return paths;
    }
}
