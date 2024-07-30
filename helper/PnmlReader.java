package helper;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;

public class PnmlReader {

	public void readPNMLFile(String filename) {
        try {
            File file = new File(filename);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            // Get the name of the Petri net
            NodeList netList = doc.getElementsByTagName("net");
            if (netList.getLength() > 0) {
                Element netElement = (Element) netList.item(0);
                String netName = netElement.getAttribute("id");
                System.out.println("Petri Net Name: " + netName);
            }

            // Get all places
            NodeList placeList = doc.getElementsByTagName("place");
            System.out.println("Places:");
            for (int i = 0; i < placeList.getLength(); i++) {
                Element placeElement = (Element) placeList.item(i);
                String placeId = placeElement.getAttribute("id");
                String placeName = getTextContentByTagName(placeElement, "name");
                String initialMarking = getTextContentByTagName(placeElement, "initialMarking");
                System.out.println("\tPlace ID: " + placeId + ", Name: " + placeName + ", Initial Marking: " + initialMarking);
            }

            // Get all transitions
            NodeList transitionList = doc.getElementsByTagName("transition");
            System.out.println("Transitions:");
            for (int i = 0; i < transitionList.getLength(); i++) {
                Element transitionElement = (Element) transitionList.item(i);
                String transitionId = transitionElement.getAttribute("id");
                String transitionName = getTextContentByTagName(transitionElement, "name");
                System.out.println("\tTransition ID: " + transitionId + ", Name: " + transitionName);
            }

            // Get all arcs
            NodeList arcList = doc.getElementsByTagName("arc");
            System.out.println("Arcs:");
            for (int i = 0; i < arcList.getLength(); i++) {
                Element arcElement = (Element) arcList.item(i);
                String arcId = arcElement.getAttribute("id");
                String source = arcElement.getAttribute("source");
                String target = arcElement.getAttribute("target");
                String inscription = getTextContentByTagName(arcElement, "inscription");
                System.out.println("\tArc ID: " + arcId + ", Source: " + source + ", Target: " + target + ", Inscription: " + inscription);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getTextContentByTagName(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            return node.getTextContent().trim();
        }
        return "";
    }
    
}