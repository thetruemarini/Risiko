package provaSVG;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.batik.dom.util.DOMUtilities;
import org.w3c.dom.Document;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.JFrame;

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

    public static void main(String[] args) {
        try {
            // Carica il file SVG
            Document svgDocument = loadSVGDocument("provaSVG/oceania.svg");

            // Ora puoi utilizzare il documento SVG caricato
            System.out.println("SVG Document loaded successfully.");

            List<String> paths = SVGParser.extractPaths(svgDocument);

            // Stampa i percorsi estratti
            for (String path : paths) {
                System.out.println(path);
                System.out.println("\n");
            }
            
            // Stampa l'intero documento SVG
             printSVGDocument(svgDocument);

            JFrame frame = new JFrame("SVG Drawer");
            //SVGDrawer svgDrawer = new SVGDrawer(paths);
            //frame.add(svgDrawer);
            frame.setSize(400, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}