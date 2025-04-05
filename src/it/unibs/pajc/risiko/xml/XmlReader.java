package src.it.unibs.pajc.risiko.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList; // Import specifico
import java.util.HashMap; // Import specifico
// Non più necessari: import java.util.List;
// Non più necessari: import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlReader {

    // Costanti per i nomi dei tag XML
    private static final String TAG_CONTINENT = "continent";
    private static final String TAG_TERRITORY = "territory";
    private static final String TAG_NAME = "name";
    private static final String TAG_LINKED_TERRITORIES = "linkedTerritories";
    private static final String TAG_ID = "id";

    private Document document;
    private boolean isValid = false;

    public XmlReader(String filePath) throws IOException, ParserConfigurationException, SAXException {
        try {
            File inputFile = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(inputFile);
            document.getDocumentElement().normalize();
            isValid = true;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.err.println("Errore durante il parsing del file XML: " + filePath);
            e.printStackTrace();
            isValid = false;
            throw e;
        } catch (IllegalArgumentException e) {
            System.err.println("Errore: Percorso file non valido o nullo: " + filePath);
            e.printStackTrace();
            isValid = false;
            throw e;
        }
    }

    public boolean isDocumentValid() {
        return isValid;
    }

    // Tipo restituito e variabile interna usano HashMap e ArrayList
    public HashMap<String, HashMap<String, ArrayList<String>>> getData() {
        if (!isValid) {
            System.err.println("Tentativo di leggere i dati da un documento XML non valido.");
            return new HashMap<>();
            // Oppure: throw new IllegalStateException("Documento XML non caricato
            // correttamente.");
        }

        HashMap<String, HashMap<String, ArrayList<String>>> data = new HashMap<>();
        NodeList continentList = document.getElementsByTagName(TAG_CONTINENT);

        for (int i = 0; i < continentList.getLength(); i++) {
            Node continentNode = continentList.item(i);
            if (continentNode.getNodeType() == Node.ELEMENT_NODE) {
                Element continentElement = (Element) continentNode;
                String continentName = getFirstElementTextByTagName(continentElement, TAG_NAME);

                if (continentName != null && !continentName.isEmpty()) {
                    // La variabile locale e il tipo restituito dal metodo helper usano
                    // HashMap/ArrayList
                    HashMap<String, ArrayList<String>> territories = getTerritoriesByContinent(continentElement);
                    data.put(continentName, territories);
                }
            }
        }
        return data;
    }

    // Tipo restituito e variabile interna usano HashMap e ArrayList
    public HashMap<String, ArrayList<Integer>> getTerritoryShapeIds() {
        if (!isValid) {
            System.err.println("Tentativo di leggere gli ID shape da un documento XML non valido.");
            return new HashMap<>();
            // Oppure: throw new IllegalStateException("Documento XML non caricato
            // correttamente.");
        }

        HashMap<String, ArrayList<Integer>> shapeIds = new HashMap<>();
        NodeList territoryList = document.getElementsByTagName(TAG_TERRITORY);

        for (int i = 0; i < territoryList.getLength(); i++) {
            Node territoryNode = territoryList.item(i);
            if (territoryNode.getNodeType() == Node.ELEMENT_NODE) {
                Element territoryElement = (Element) territoryNode;
                String territoryName = getFirstElementTextByTagName(territoryElement, TAG_NAME);
                if (territoryName != null && !territoryName.isEmpty()) {
                    // La variabile locale e il tipo restituito dal metodo helper usano ArrayList
                    ArrayList<Integer> ids = getShapeIds(territoryElement);
                    shapeIds.put(territoryName, ids);
                }
            }
        }
        return shapeIds;
    }

    // --- Metodi Helper ---

    // Tipo restituito e variabile interna usano HashMap e ArrayList
    private HashMap<String, ArrayList<String>> getTerritoriesByContinent(Element continentElement) {
        HashMap<String, ArrayList<String>> territories = new HashMap<>();
        NodeList territoriesList = continentElement.getElementsByTagName(TAG_TERRITORY);

        for (int i = 0; i < territoriesList.getLength(); i++) {
            Node territoryNode = territoriesList.item(i);
            if (territoryNode.getNodeType() == Node.ELEMENT_NODE) {
                Element territoryElement = (Element) territoryNode;
                String territoryName = getFirstElementTextByTagName(territoryElement, TAG_NAME);

                if (territoryName != null && !territoryName.isEmpty()) {
                    // La variabile locale e il tipo restituito dal metodo helper usano ArrayList
                    ArrayList<String> linkedTerritories = getLinkedTerritoriesNames(territoryElement);
                    territories.put(territoryName, linkedTerritories);
                }
            }
        }
        return territories;
    }

    // Tipo restituito e variabile interna usano ArrayList
    private ArrayList<String> getLinkedTerritoriesNames(Element territoryElement) {
        ArrayList<String> linkedTerritoriesNames = new ArrayList<>();
        NodeList linkedGroupList = territoryElement.getElementsByTagName(TAG_LINKED_TERRITORIES);

        if (linkedGroupList.getLength() > 0 && linkedGroupList.item(0).getNodeType() == Node.ELEMENT_NODE) {
            Element linkedGroupElement = (Element) linkedGroupList.item(0);
            NodeList linkedTerritoriesList = linkedGroupElement.getElementsByTagName(TAG_TERRITORY);

            for (int i = 0; i < linkedTerritoriesList.getLength(); i++) {
                Node linkedTerritoryNode = linkedTerritoriesList.item(i);
                if (linkedTerritoryNode.getNodeType() == Node.ELEMENT_NODE) {
                    String name = linkedTerritoryNode.getTextContent().trim();
                    if (!name.isEmpty()) {
                        linkedTerritoriesNames.add(name);
                    }
                } else if (linkedTerritoryNode.getNodeType() == Node.TEXT_NODE
                        && !linkedTerritoryNode.getTextContent().trim().isEmpty()) {
                    String name = linkedTerritoryNode.getTextContent().trim();
                    linkedTerritoriesNames.add(name);
                }
            }
        }
        return linkedTerritoriesNames;
    }

    // Tipo restituito e variabile interna usano ArrayList
    private ArrayList<Integer> getShapeIds(Element territoryElement) {
        ArrayList<Integer> shapeIds = new ArrayList<>();
        NodeList shapesList = territoryElement.getElementsByTagName(TAG_ID);

        for (int i = 0; i < shapesList.getLength(); i++) {
            Node idNode = shapesList.item(i);
            if (idNode.getNodeType() == Node.ELEMENT_NODE
                    || (idNode.getNodeType() == Node.TEXT_NODE && !idNode.getTextContent().trim().isEmpty())) {
                String shapeText = idNode.getTextContent().trim();
                try {
                    if (!shapeText.isEmpty()) {
                        shapeIds.add(Integer.parseInt(shapeText));
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Attenzione: Impossibile parsare l'ID shape '" + shapeText
                            + "' per il territorio " + getFirstElementTextByTagName(territoryElement, TAG_NAME));
                }
            }
        }
        return shapeIds;
    }

    // Helper per ottenere il testo del primo sotto-elemento con un dato tag name
    private String getFirstElementTextByTagName(Element parentElement, String tagName) {
        NodeList nodeList = parentElement.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            Node firstNode = nodeList.item(0);
            return firstNode.getTextContent().trim();
        }
        return null;
    }
}