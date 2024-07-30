package logic;

public class Place {
    
	String id;
    int tokens;
    

    public Place(String id, int tokens) {
        this.id = id;
		this.tokens = tokens;
    }

	public String getId() {
		return id;
	}
    
    public int getTokens() {
        return tokens;
    }

    public void addTokens(int tokens) {
        this.tokens += tokens;
    }

    public void removeTokens(int tokens) {
        this.tokens -= tokens;
    }

}
