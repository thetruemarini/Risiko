package it.unibs.pajc.risiko.svg;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Document;

import java.io.File;

public class SVGLoader {
    public static Document loadSVG(String filePath) throws Exception {
        // creo il factory per i documenti SVG
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);

        // carico il file SVG e restituisce il documento
    
        File svgFile = new File(filePath);
        return factory.createDocument(svgFile.toURI().toString());
    }
}


