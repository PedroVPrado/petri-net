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
