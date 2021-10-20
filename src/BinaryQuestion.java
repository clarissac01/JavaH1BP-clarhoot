
public class BinaryQuestion extends Question implements Cloneable{

	public BinaryQuestion(String question, String correctAnswer, int timelimit, String pointtype) {
		super(question, correctAnswer, timelimit, pointtype);
	}
	
	@Override
	public int checkAnswer(String userAnswer) {
		// TODO Auto-generated method stub
		if(userAnswer.equals(getCorrectAnswer())) {
			return 1;
		}
		return 0;
	}


}
