package it.unibs.pajc.risiko.svg;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.bridge.*;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class SVGLoader {
    public static Document loadSVGDocument(String filePath) throws IOException {
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);
        String svgContent = new String(Files.readAllBytes(Paths.get(filePath)));
        try (InputStream svgStream = new ByteArrayInputStream(svgContent.getBytes(StandardCharsets.UTF_8))) {
            return factory.createDocument(null, svgStream);
        }
    }

    public static List<String> extractPaths(Document document) {
        List<String> paths = new ArrayList<>();
        NodeList pathNodes = document.getElementsByTagName("path");
        for (int i = 0; i < pathNodes.getLength(); i++) {
            Element pathElement = (Element) pathNodes.item(i);
            paths.add(pathElement.getAttribute("d"));
        }
        return paths;
    }
}


