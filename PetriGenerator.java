import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import logic.Arc;
import logic.Place;
import logic.Transition;
 
public class PetriGenerator {
	
	String log;
	int loops;
	List<Place> places = new ArrayList<Place>();
    List<Transition> transitions = new ArrayList<Transition>();
    List<Arc> arcs = new ArrayList<Arc>();
 
    public static void main(String[] args) {
		if (args.length != 2) {
            System.out.println("Favor inserir argumentos <filename> <cost>");
            return;
        }

        String filename = args[0];
		int loops = Integer.parseInt(args[1]);
		
		System.out.println("Filename: " + filename);
        System.out.println("N: " + loops);
        
		new PetriGenerator(filename, loops);

        //new Petrinet(filename, loops);
    }
    
    public PetriGenerator(String filename, int loops) {
    	this.loops = loops;
		this.log = "";
		readPNMLFile(filename);
		run();
    }
    
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
                if (initialMarking.length() == 0) {
                	initialMarking = "1";
                }                
                System.out.println("\tPlace ID: " + placeId + ", Name: " + placeName + ", Initial Marking: " + initialMarking);
                
                Place p = new Place(placeId, placeName, Integer.parseInt(initialMarking));
                places.add(p);
            }

            // Get all transitions
            NodeList transitionList = doc.getElementsByTagName("transition");
            System.out.println("Transitions:");
            for (int i = 0; i < transitionList.getLength(); i++) {
                Element transitionElement = (Element) transitionList.item(i);
                String transitionId = transitionElement.getAttribute("id");
                String transitionName = getTextContentByTagName(transitionElement, "name");
                System.out.println("\tTransition ID: " + transitionId + ", Name: " + transitionName);
                
                Transition t = new Transition(transitionId, transitionName);
                transitions.add(t);
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
                if (inscription.length() == 0) {
                	inscription = "1";
                }
                System.out.println("\tArc ID: " + arcId + ", Source: " + source + ", Target: " + target + ", Inscription: " + inscription);
                createArc(arcId, source, target, inscription);
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
    
    private void createArc(String id, String source, String target, String inscription) {
    	Arc a;
    	boolean created = false;
    	for (Place p : places) {
    		if (created) break;
        	if (p.getId().equals(source)) {
        		for (Transition t : this.transitions) {
        			if (t.getId().equals(target)) {
        				a = new Arc(id, p, t, Integer.parseInt(inscription));
        				this.arcs.add(a);
        				created = true;
        				break;
        			}
        		}
        	}
        }
    	for (Transition t : this.transitions) {
    		if (created) break;
			if (t.getId().equals(source)) {
		    	for (Place p : places) {
		        	if (p.getId().equals(target)) {
        				a = new Arc(id, t, p, Integer.parseInt(inscription));
        				this.arcs.add(a);
        				created = true;
        				break;
        			}
        		}
        	}
        }
    }
    
    private void run() {
		boolean running = true;
		int i = 0;
		while (running && i < this.loops) {
			running = false;
			i ++;
			System.out.println("\n\nrodando");
			for (Transition transition : this.transitions) {
				if (transition.isTriggerable()) {
					if (!running) running = true;
					String triggerLog = transition.trigger();
					//System.out.println(triggerLog);
					this.log += triggerLog;
				}
			}
		}
		System.out.println(this.log);
		System.out.println("fim");
		System.err.println("Iterações feitas: " + i);
	}
    
}
