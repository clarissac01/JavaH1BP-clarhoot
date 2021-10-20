
public abstract class Question implements Cloneable{

	private String question;
	private String correctAnswer;
	private int timelimit;
	private String pointtype;
	
	public String getQuestion() {
		return question;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}

	public abstract int checkAnswer(String userAnswer);



	public int getTimelimit() {
		return timelimit;
	}

	public String getPointtype() {
		return pointtype;
	}

	public Question(String question, String correctAnswer, int timelimit, String pointtype) {
		super();
		this.question = question;
		this.correctAnswer = correctAnswer;
		this.timelimit = timelimit;
		this.pointtype = pointtype;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public void setTimelimit(int timelimit) {
		this.timelimit = timelimit;
	}

	public void setPointtype(String pointtype) {
		this.pointtype = pointtype;
	}

}
