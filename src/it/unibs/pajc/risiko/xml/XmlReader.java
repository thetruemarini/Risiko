package src.it.unibs.pajc.risiko.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

//Bro io ho fatto sta cosa, ci ho messo un bel po,sono un bot pero secondo me funziona ed è funzionale mi chiamo marini sono gay fes mi piace
//il pisello
//TODO l'unica cosa che si potrebbe fare è impostare i parametri delle ultime due funzioni a String e non nodo se serve (solo se serve)
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

    // Funzione che ritorna un hashmap contenente i continenti in un hashmap con i
    // relativi territori(con i relativi territori confinanti)
    public HashMap<String, HashMap<String, ArrayList<String>>> getData() {
        HashMap<String, HashMap<String, ArrayList<String>>> data = new HashMap<>();
        HashMap<String, ArrayList<String>> territories;

        try {
            // TODO immagino che le ultime due nodeList saranno da spostare nei for
            NodeList continentList = document.getElementsByTagName("continent"); // ottieni elementi continent

            for (int i = 0; i < continentList.getLength(); i++) {
                Node node = continentList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    // estraggo il nome del continente
                    NodeList continentNameList = element.getElementsByTagName("name");

                    if (continentNameList.getLength() > 0) {
                        String name = continentNameList.item(0).getTextContent();
                        territories = getTerritoriesByContinent(node);
                        data.put(name, territories);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;

    }

    // Funzione che ritorna la lista dei territori confinanti dato un nodo
    // territorio
    private ArrayList<String> getLinkedTerritoryesNames(Node territory) {
        ArrayList<String> linkedTerritoriesNames = new ArrayList<>();
        if (territory.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) territory;
            NodeList linkedTerritoriesList = element.getElementsByTagName("linkedTerritories"); // ottirni elementi
                                                                                                // linked territory
            // perche per ogni territorio c'e solo 1 linked territories
            Node node = linkedTerritoriesList.item(0);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element2 = (Element) node;

                NodeList linkedeTerritoriesNameList = element2.getElementsByTagName("territory");

                if (linkedeTerritoriesNameList.getLength() > 0) {
                    for (int i = 0; i < linkedeTerritoriesNameList.getLength(); i++) {
                        String name = linkedeTerritoriesNameList.item(i).getTextContent();
                        // System.out.println(name);
                        linkedTerritoriesNames.add(name);
                    }
                    // System.out.println("\n------------------------\n");
                }
            }
        }
        // System.out.println("fine funzione\n------------------------\n");
        return linkedTerritoriesNames;
    }

    // Funzione che ritorna l'hashmap dei territori per ogni continente(con i
    // relativi territori confinanti)
    private HashMap<String, ArrayList<String>> getTerritoriesByContinent(Node continent) {
        HashMap<String, ArrayList<String>> territories = new HashMap<>();
        if (continent.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) continent;
            NodeList territoriesList = element.getElementsByTagName("territory"); // ottirni elementi territory del
                                                                                  // continent

            for (int i = 0; i < territoriesList.getLength(); i++) {
                Node node = territoriesList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element2 = (Element) node;

                    // estraggo i nomi dei territori
                    NodeList territoriesNameList = element2.getElementsByTagName("name");

                    if (territoriesNameList.getLength() > 0) {
                        String name = territoriesNameList.item(0).getTextContent();
                        ArrayList<String> linkedTerritories = getLinkedTerritoryesNames(node);
                        territories.put(name, linkedTerritories);
                    }
                }
            }
        }

        return territories;
    }

    public HashMap<String, ArrayList<Integer>> getTerritoryShapeIds() {
        HashMap<String, ArrayList<Integer>> shapeIds = new HashMap<>();

        try {
            NodeList continentList = document.getElementsByTagName("continent");

            for (int i = 0; i < continentList.getLength(); i++) {
                Node continentNode = continentList.item(i);
                shapeIds.putAll(getShapesByContinent(continentNode));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return shapeIds;
    }

    private HashMap<String, ArrayList<Integer>> getShapesByContinent(Node continent) {
        HashMap<String, ArrayList<Integer>> shapesByTerritory = new HashMap<>();

        if (continent.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) continent;
            NodeList territoriesList = element.getElementsByTagName("territory"); // Ottieni i territori

            for (int i = 0; i < territoriesList.getLength(); i++) {
                Node node = territoriesList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element territoryElement = (Element) node;

                    // Estraggo il nome del territorio
                    NodeList nameList = territoryElement.getElementsByTagName("name");

                    if (nameList.getLength() > 0) {
                        String name = nameList.item(0).getTextContent().trim();

                        // Estraggo gli ID delle shape
                        ArrayList<Integer> shapeIds = getShapeIds(territoryElement);
                        shapesByTerritory.put(name, shapeIds);
                    }
                }
            }
        }
        return shapesByTerritory;
    }

    private ArrayList<Integer> getShapeIds(Element territoryElement) {
        ArrayList<Integer> shapeIds = new ArrayList<>();
        NodeList shapesList = territoryElement.getElementsByTagName("id");

        for (int i = 0; i < shapesList.getLength(); i++) {
            String shapeText = shapesList.item(i).getTextContent().trim();
            shapeIds.add(Integer.parseInt(shapeText));
        }
        return shapeIds;
    }

}
