package logic;

public class Arc {

	String id;
    Place place;
    Transition transition;
	int tokenCost;
    String type;
    
    public Arc(String id, Place place, Transition trans, int cost) {
        this.id = id;
        this.type = "pre";
        this.place = place;
        this.transition = trans;
		this.tokenCost = cost;
		trans.addArc(this);
    }

	public Arc(String id, Transition trans, Place place, int cost) {
        this.id = id;
        this.type = "pos";
        this.place = place;
        this.transition = trans;
		this.tokenCost = cost;
		trans.addArc(this);
    }

	public String getPlace() {
		return place.getId();
	}

	public int getCost() {
		return this.tokenCost;
	}

    public boolean isTriggerable() {
        return this.place.getTokens() >= this.tokenCost;
    }
    
    public void trigger() {
		if (type == "pre") {
			this.place.removeTokens(this.tokenCost);
		} else {
        	this.place.addTokens(this.tokenCost);
		}
    }
}
