package it.unibs.pajc.risiko.svg;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class SVGParser {
    public static void parseSVG(Document document) {
        // Ottiene tutti gli elementi "path"
        NodeList pathNodes = document.getElementsByTagName("path");

        for (int i = 0; i < pathNodes.getLength(); i++) {
            Element pathElement = (Element) pathNodes.item(i);

            // Ottiene l'attributo "d" che definisce il percorso
            String pathData = pathElement.getAttribute("d");
            System.out.println("Path data: " + pathData);

            //TODO si possono calcolare i confini usando librerie di parsing (ad esempio Batik's AWTGVTBuilder)
        }
    }
}
