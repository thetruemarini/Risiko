package it.unibs.pajc.risiko.xml;

import org.w3c.dom.*;

import it.unibs.pajc.risiko.Territory;

import javax.xml.parsers.*;
import java.io.File;
import java.util.ArrayList;

public class XmlReader {
    File inputFile;
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder;
    Document document;

    // costruttore di apertura del file
    public XmlReader(String filePath) {
        try {
            inputFile = new File(filePath); // Percorso del file XML
            builder = factory.newDocumentBuilder();
            document = builder.parse(inputFile);
            document.getDocumentElement().normalize();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList<String> getContinentNames() {
        ArrayList<String> continentsNames = new ArrayList<>();
        try {          
            NodeList continentsList = document.getElementsByTagName("continent");
       
            for (int i = 0; i < continentsList.getLength(); i++) {
                Node node = continentsList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;                 
                    NodeList nameList = element.getElementsByTagName("name");

                    if (nameList.getLength() > 0) {
                        String name = nameList.item(0).getTextContent();
                        continentsNames.add(name);
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
                return continentsNames;
    }


    // Metodo per leggere e stampare i nomi dei territori dal file XML
    public ArrayList<String> getTerritoryNamesByContinent() { //TODO da fare il by name
        ArrayList<String> territoriesNames = new ArrayList<>();
        try {

            // 2. Ottieni tutti gli elementi "territory"
            NodeList territoriesList = document.getElementsByTagName("territory");

            // 3. Itera su ciascun elemento "territory"
            for (int i = 0; i < territoriesList.getLength(); i++) {
                Node node = territoriesList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    // 4. Estrai solo il nome del territorio
                    NodeList nameList = element.getElementsByTagName("name");

                    if (nameList.getLength() > 0) {
                        String name = nameList.item(0).getTextContent();
                        territoriesNames.add(name);
                        // System.out.println(name);
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
                return territoriesNames;
    }
}
