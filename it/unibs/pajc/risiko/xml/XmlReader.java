package it.unibs.pajc.risiko.xml;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;

public class XmlReader {

    // Metodo per leggere e stampare i nomi dei territori dal file XML
    public void printTerritoryNames(String filePath) {
        try {
            // 1. Carica il documento XML
            File inputFile = new File(filePath); // Percorso del file XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputFile);
            document.getDocumentElement().normalize();

            // 2. Ottieni tutti gli elementi "territory"
            NodeList nodeList = document.getElementsByTagName("territory");

            // 3. Itera su ciascun elemento "territory"
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    // 4. Estrai solo il nome del territorio
                    NodeList nameList = element.getElementsByTagName("name");
                    if (nameList.getLength() > 0) {
                        String name = nameList.item(0).getTextContent();
                        System.out.println(name);
                    }

                    // 5. Ignora i linkedTerritories puoi aggiungere qui eventuali logiche se vuoi
                    // manipolare altri dati ma non li stamperai a meno che non sia richiesto
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
