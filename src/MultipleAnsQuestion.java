import java.util.Vector;

public class MultipleAnsQuestion extends Question implements Cloneable{

	private Vector<String> answers;

	public MultipleAnsQuestion(String question, String correctAnswer, int timelimit, String pointtype,
			Vector<String> answers) {
		super(question, correctAnswer, timelimit, pointtype);
		this.answers = answers;
	}

	public Vector<String> getAnswers() {
		return answers;
	}


	public void setAnswers(Vector<String> answers) {
		this.answers = answers;
	}


	@Override
	public int checkAnswer(String userAnswer) {
		// TODO Auto-generated method stub
		if(getCorrectAnswer().equals(userAnswer)) {
			return 1;
		}
		return 0;
	}

}
