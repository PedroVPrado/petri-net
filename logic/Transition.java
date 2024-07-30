package logic;

import java.util.ArrayList;
import java.util.List;

public class Transition {

	String id;
	String name;
	List<Arc> entryArcs = new ArrayList<Arc>();
    List<Arc> exitArcs = new ArrayList<Arc>();

    public Transition(String id, String name) {
        this.id = id;
        this.name = name;

		/* 
			while (!this.isTriggerable()) { }
			this.trigger()
			return true; //kill thread
		*/

    }

    public boolean isTriggerable() {
		boolean status = true;

        for (Arc arc : this.entryArcs) {
            status = status && arc.isTriggerable();
        }

        return status && this.isAlive();
    }
    
    public String trigger() {
		String log = "\n Transição " + this.id + " ativada.";
        for (Arc arc : this.entryArcs) {
            arc.trigger();
			log += "\n Removeu " + arc.getCost() + " tokens de " + arc.getPlace();
        }

		for (Arc arc : this.exitArcs) {
            arc.trigger();
			log += "\n Adicionou " + arc.getCost() + " tokens de " + arc.getPlace();
        }
		return log;
    }
    
    public void addArc(Arc arc) {
		if (arc.type.equals("pre")) {
			this.entryArcs.add(arc);
		} else {
			this.exitArcs.add(arc);
		}
	}
    
    public String getId() {
    	return this.id;
    }

    public boolean isAlive() {
        return !(this.entryArcs.isEmpty() || this.exitArcs.isEmpty());
    }
    
}
