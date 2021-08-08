package DB;

public class Users {

	private String id;
	private String password;
	private int playcount = 0;
	private int humanwin = 0;
	private int molewin = 0;
	private float rate = 0;
	private int scores = 1000;

	public Users(String id, String password, int playcount, int humanwin, int molewin, float rate, int scores) {
		this.id = id;
		this.password = password;
		this.playcount = playcount;
		this.humanwin = humanwin;
		this.molewin = molewin;
		this.rate = rate;
		this.scores = scores;
	}
	
	public Users() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPlaycount() {
		return playcount;
	}

	public void setPlaycount(int playcount) {
		this.playcount = playcount;
	}

	public int getHumanwin() {
		return humanwin;
	}

	public void setHumanwin(int humanwin) {
		this.humanwin = humanwin;
	}

	public int getMolewin() {
		return molewin;
	}

	public void setMolewin(int molewin) {
		this.molewin = molewin;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public int getScores() {
		return scores;
	}

	public void setScores(int scores) {
		this.scores = scores;
	};

}
