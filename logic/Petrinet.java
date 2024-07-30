package logic;

import java.util.ArrayList;
import java.util.List;

public class Petrinet {

    private static final String nl = "\n";
	String filename;
	String log;
	int loops;
	List<Place> places = new ArrayList<Place>();
    List<Transition> transitions = new ArrayList<Transition>();
    List<Arc> arcs = new ArrayList<Arc>();
	Transition t1;
	Transition t2;
	Place p1;
	Place p2;
	Place p3;
    
    public Petrinet(String filename, int n) {
        this.filename = filename;
		this.loops = n;
		this.log = "";

		setUp();

    }

	public void setUp() {
		t1 = new Transition("t1");
		this.transitions.add(t1);
		p1 = new Place("p1", 2);
		this.places.add(p1);
		p2 = new Place("p2", 1);
		this.places.add(p2);
		Arc a1 = new Arc("a1", p1, t1, 1);
		this.arcs.add(a1);
		Arc a2 = new Arc("a2", t1, p2, 2);
		this.arcs.add(a2);
		
		t2 = new Transition("t2");
		this.transitions.add(t2);
		p3 = new Place("p3", 1);
		this.places.add(p3);
		Arc a3 = new Arc("a3", p2, t2, 1);
		this.arcs.add(a3);
		Arc a4 = new Arc("a4", t2, p3, 1);
		this.arcs.add(a4);

		this.run();
	}

	private void run() {
		boolean running = true;
		while (running) {
			running = false;
			for (Transition transition : this.transitions) {
				if (transition.isTriggerable()) {
					if (!running) running = true;
					this.log += transition.trigger();
				}
			}
		}

		System.out.println(this.log);
	}
    
    public List<Transition> getTransitionsAbleToFire() {
        ArrayList<Transition> list = new ArrayList<Transition>();
        for (Transition t : transitions) {
            if (t.isTriggerable()) {
                list.add(t);
            }
        }
        return list;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Petrinet ");
        sb.append(super.toString()).append(nl);
        sb.append("---Transitions---").append(nl);
        for (Transition t : transitions) {
            sb.append(t).append(nl);
        }
        sb.append("---Places---").append(nl);
        for (Place p : places) {
            sb.append(p).append(nl);
        }
        return sb.toString();
    }

    public List<Place> getPlaces() {
        return places;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public List<Arc> getArcs() {
        return arcs;
    }

}
