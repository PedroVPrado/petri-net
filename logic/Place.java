package logic;

public class Place {
    
	String id;
	String name;
    int tokens;
    

    public Place(String id, String name, int tokens) {
        this.id = id;
        this.name = name;
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
