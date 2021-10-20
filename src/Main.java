import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main extends JFrame{
	
	int currentUseridx;
	String currentUsername;
	int userScore;
	Vector<User> users;
	
	public void initNewGame() {
		currentUseridx = -1;
		currentUsername = "";
		userScore = 0;
		setContentPane(new HomePage(this));
	}
	
	public Main() {
		// TODO Auto-generated constructor stub
		initNewGame();
		setSize(425, 610);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		setTitle("Clarhoot!");
		setIconImage(new ImageIcon("logo.png").getImage());
	}
	
	public void gotoSignUp() {
		getContentPane().removeAll();
		setContentPane(new SignUp(this));
		revalidate();
	}
	
	public void gotoSignIn() {
		getContentPane().removeAll();
		setContentPane(new HomePage(this));
		revalidate();
	}
	
	public void changetoGameOver(User user,int score) {
		this.userScore = score;
		user.setScore(score);
		getContentPane().removeAll();
		setContentPane(new GameOverPage(score, this, user));
		revalidate();
	}
	
	public void gotoTeacherPage(Main main, User user) {
		getContentPane().removeAll();
		setContentPane(new TeacherPage(this, user));
		revalidate();
	}

	public void gotoStudentPage(Main main, User user) {
		getContentPane().removeAll();
		setContentPane(new StudentPage(this, user));
		revalidate();
		users = FileManager.getInstance().getAllUsers();
	}
	
	public void changetoLeaderBoard(boolean saveScore, User currUser, int userscore) {
		if(saveScore == true) {
			
			try {
				File file = new File("src\\users.csv");
				
				FileWriter fw = new FileWriter(file);
				fw.write("question,limit,point,option1,option2,option3,option4,correctAnswer,questionType\n");
				for (User user : users) {
					if(user.getUsername().equals(currUser.getUsername())) {
						user.setScore(userscore);
					}
					fw.write(user.getUsername()+","+ user.getPassword() + ","+user.getScore()+ "," + user.getRole() + "\n");
				}
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		getContentPane().removeAll();
		setContentPane(new LeaderBoard(this, users, currUser));
		revalidate();
	}

	public void playAgain(User user) {
		getContentPane().removeAll();
		setContentPane(new StudentPage(this, user));
		revalidate();
	}
	
	public void backToTeacherPage(User user) {
		getContentPane().removeAll();
		setSize(425, 610);
		setLocationRelativeTo(null);
		setContentPane(new TeacherPage(this, user));
		revalidate();
	}
	
	public void updateQuestionset(User user, String filename) {
		getContentPane().removeAll();
		setSize(1000, 500);
		setLocationRelativeTo(null);
		setContentPane(new ManageQuestions(this, user, FileManager.getInstance().readQuestion(user, filename), filename));
		revalidate();
	}
	
	public void createKahoot(User user) {
		getContentPane().removeAll();
		setSize(1000, 500);
		setLocationRelativeTo(null);
		setContentPane(new ManageQuestions(this, user));
		revalidate();
		
	}
	
	public static void main(String[] args) {
		new Main();
	}

	public void doQuiz(User user, String gamepin) {
		getContentPane().removeAll();
		setContentPane(new QuizPage(this, user, FileManager.getInstance().getQuestionSet(gamepin)));
		revalidate();
	}
	
}
