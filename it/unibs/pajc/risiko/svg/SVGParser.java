package it.unibs.pajc.risiko.svg;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class SVGParser {

    public static List<String> extractPaths(Document document) {
        List<String> paths = new ArrayList<>();
        NodeList pathNodes = document.getElementsByTagName("path");

        for (int i = 0; i < pathNodes.getLength(); i++) {
            Element pathElement = (Element) pathNodes.item(i);
            String dAttribute = pathElement.getAttribute("d");
            paths.add(dAttribute);
        }

        return paths;
    }
}