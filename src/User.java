
public class User implements Comparable<User>{

	private String username;
	private String role;
	private int score;
	private String password;
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public User(String username, String role, int score, String password) {
		super();
		this.username = username;
		this.role = role;
		this.score = score;
		this.password = password;
	}

	@Override
	public int compareTo(User compareUser) {
		// TODO Auto-generated method stub
		int compareQty = ((User) compareUser).getScore();
			
		return compareQty - this.score;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getUsername();
	}

}
