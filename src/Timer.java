
public class Timer implements Runnable{

	public Thread t;
	private String threadName;
	public Integer timenow = 0;
	private int duration;
	private QuizPage quizp;
	   
	public Timer(QuizPage qp, int duration) {
		this.quizp = qp;
		this.duration = duration;
		start();
	}
	   
	public void start() {
		if(t == null) {
			t = new Thread(this);
			t.start();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (Integer i = 1; i <= duration; i++) {
			try {
				Thread.sleep(1000);
				quizp.setTime(i);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				break;
			}
		}
	}
	
}
