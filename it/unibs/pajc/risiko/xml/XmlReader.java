package it.unibs.pajc.risiko.xml;

import org.w3c.dom.*;

import it.unibs.pajc.risiko.Territory;

import javax.xml.parsers.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

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
    public ArrayList<String> getTerritoryNamesByContinent(String continentName) { //TODO da fare il by name
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

    public HashMap<String, HashMap<String, ArrayList<String>>> getData(){
        HashMap<String, HashMap<String, ArrayList<String>>> data = new HashMap<>();
        HashMap<String, ArrayList<String>> territoriesByContinent = new HashMap<>();
        ArrayList<String> linkedTerritories = new ArrayList<>();
        try {
            //TODO immagino che le ultime due nodeList saranno da spostare nei for
            NodeList continentList = document.getElementsByTagName("continent"); //ottieni elementi continent
            NodeList territoriesList = document.getElementsByTagName("territory"); //ottirni elementi terrytory
            NodeList linkedTerritoriesList = document.getElementsByTagName("//TODO"); //ottirni elementi linked territory

            for(int i =0; i<continentList.getLength(); i++){
                Node node = continentList.item(i);

                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;

                    //estraggo il nome del continente 
                    NodeList continentNameList = element.getElementsByTagName("name");

                    if (continentList.getLength() > 0) {
                        String name = continentNameList.item(i).getTextContent();
                        
                        for(int j=0; j<territoriesList.getLength();j++){
                            Node node2 = territoriesList.item(i);

                            if(node2.getNodeType() == Node.ELEMENT_NODE){
                                Element element2 = (Element) node2;

                                //estraggo i nomi dei territori
                                NodeList territoriesNameList = element2.getElementsByTagName("name");

                                if(territoriesNameList.getLength() > 0){
                                    String name2 = territoriesNameList.item(j).getTextContent();

                                    for(int k=0;k<linkedTerritoriesList.getLength();k++){
                                        Node node3 = linkedTerritoriesList.item(k);

                                        if(node3.getNodeType() == Node.ELEMENT_NODE){
                                            Element element3 = (Element) node3;

                                            //estraggo i nome dei linked territori
                                            NodeList linkedeTerritoriesNaemList = element3.getElementsByTagName("territory");

                                            if(linkedeTerritoriesNaemList.getLength()>0){
                                                String name3 = linkedeTerritoriesNaemList.item(k).getTextContent();
                                                linkedTerritories.add(name3);
                                            }
                                        }
                                    }
                                    territoriesByContinent.put(name2, linkedTerritories);

                                }

                            }
                        }
                        data.put(name, territoriesByContinent);
                    }
                }
            }

            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;

    }
}
