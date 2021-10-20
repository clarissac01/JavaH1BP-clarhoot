import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class QuizPage extends JPanel implements ActionListener{

	JLabel question;
	JRadioButton option1, option2, option3, option4;
	ButtonGroup bg;
	Vector<Question> questions = new Vector<>();
	int questionnumber = 0;
	JPanel ansPanel;
	MyFont myfont = new MyFont();
	int score = 0;
	Main main;
	User user;
	Timer timer;
	JButton time;
	JTextArea qPanel;
	
	public void initMainPanel() {
		JPanel a = new JPanel(new BorderLayout());
		Integer timeinit = questions.get(questionnumber).getTimelimit();
		time = new JButton(timeinit.toString());
		time.setFont(myfont.font2.deriveFont(Font.PLAIN, 30f));
		time.setBackground(Color.red);
		time.setPreferredSize(new Dimension(75, 75));
		qPanel = new JTextArea();
		qPanel.setBackground(Color.white);
		a.setBackground(Color.white);
		qPanel.setEditable(false);
		qPanel.setLineWrap(true);
		qPanel.setPreferredSize(new Dimension(250, 300));
		qPanel.setAlignmentY(CENTER_ALIGNMENT);
		qPanel.setFont(myfont.font2.deriveFont(Font.PLAIN, 30f));
		qPanel.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
		qPanel.setAlignmentY(SwingConstants.CENTER);
		qPanel.setText(questions.get(questionnumber).getQuestion());
		a.add(time, BorderLayout.NORTH);
		a.add(qPanel, BorderLayout.CENTER);
		
		if(questions.get(questionnumber) instanceof MultipleAnsQuestion) {
			bg = new ButtonGroup();
			
			option1 = new JRadioButton(((MultipleAnsQuestion) questions.get(questionnumber)).getAnswers().get(0));
			option2 = new JRadioButton(((MultipleAnsQuestion) questions.get(questionnumber)).getAnswers().get(1));
			option3 = new JRadioButton(((MultipleAnsQuestion) questions.get(questionnumber)).getAnswers().get(2));
			option4 = new JRadioButton(((MultipleAnsQuestion) questions.get(questionnumber)).getAnswers().get(3));
			
			option1.setFont(myfont.font2.deriveFont(Font.PLAIN, 20f));
			option2.setFont(myfont.font2.deriveFont(Font.PLAIN, 20f));
			option3.setFont(myfont.font2.deriveFont(Font.PLAIN, 20f));
			option4.setFont(myfont.font2.deriveFont(Font.PLAIN, 20f));
			
			option1.setForeground(Color.white);
			option2.setForeground(Color.white);
			option3.setForeground(Color.white);
			option4.setForeground(Color.white);
			
			option1.setBackground(Color.decode("#ff3355"));
			option2.setBackground(Color.decode("#45a3e5"));
			option3.setBackground(Color.decode("#eb670f"));
			option4.setBackground(Color.decode("#66bf39"));
			
			bg.add(option4);
			bg.add(option3);
			bg.add(option2);
			bg.add(option1);
			
			ansPanel = new JPanel(new GridLayout(2,2));
			ansPanel.setPreferredSize(new Dimension(425, 300));
			ansPanel.add(option1);
			ansPanel.add(option2);
			ansPanel.add(option3);
			ansPanel.add(option4);
			
			add(a, BorderLayout.CENTER);
			add(ansPanel, BorderLayout.SOUTH);
		}else {
			bg = new ButtonGroup();
			
			option1 = new JRadioButton("TRUE");
			option1.setForeground(Color.white);
			option2 = new JRadioButton("FALSE");
			option2.setForeground(Color.white);
			
			option1.setFont(myfont.font2.deriveFont(Font.PLAIN, 35f));
			option2.setFont(myfont.font2.deriveFont(Font.PLAIN, 35f));
		
			option1.setBackground(Color.decode("#ff3355"));
			option2.setBackground(Color.decode("#66bf39"));
			
			bg.add(option2);
			bg.add(option1);
			
			ansPanel = new JPanel(new GridLayout(1,2));
			ansPanel.setPreferredSize(new Dimension(425, 150));
			ansPanel.add(option1);
			ansPanel.add(option2);
			
			add(a, BorderLayout.CENTER);
			add(ansPanel, BorderLayout.SOUTH);
		}
		
		option1.addActionListener(this);
		option2.addActionListener(this);
		option3.addActionListener(this);
		option4.addActionListener(this);
		
	}

	public QuizPage(Main main, User user, Vector<Question> questions) {
		this.main = main;
		this.user = user;
		this.questions = questions;

		setLayout(new BorderLayout());
		setBackground(Color.white);
		timer = new Timer(this, questions.get(questionnumber).getTimelimit());
		
		initMainPanel();
	}
	
	private void getAnswer(ActionEvent e) {
		if(e.getSource() == option1) {
			if(questions.get(questionnumber).getPointtype().equals("Standard"))
				score += questions.get(questionnumber).checkAnswer(option1.getText());
			else  if(questions.get(questionnumber).getPointtype().equals("Double points"))
				score += (questions.get(questionnumber).checkAnswer(option1.getText()) * 2);
		}
		else if(e.getSource() == option2) {
			if(questions.get(questionnumber).getPointtype().equals("Standard"))
				score += questions.get(questionnumber).checkAnswer(option2.getText());
			else  if(questions.get(questionnumber).getPointtype().equals("Double points"))
				score += (questions.get(questionnumber).checkAnswer(option2.getText()) * 2);
		}
		else if(e.getSource() == option3) {
			if(questions.get(questionnumber).getPointtype().equals("Standard"))
				score += questions.get(questionnumber).checkAnswer(option3.getText());
			else  if(questions.get(questionnumber).getPointtype().equals("Double points"))
				score += (questions.get(questionnumber).checkAnswer(option3.getText()) * 2);
		}else {
			if(questions.get(questionnumber).getPointtype().equals("Standard"))
				score += questions.get(questionnumber).checkAnswer(option4.getText());
			else  if(questions.get(questionnumber).getPointtype().equals("Double points"))
				score += (questions.get(questionnumber).checkAnswer(option4.getText()) * 2);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		getAnswer(e);
		nextQuestion();
	}
	
	public void setTime(Integer i) {
		Integer timeleft = questions.get(questionnumber).getTimelimit() - i;
		if(timeleft == 0) {
			nextQuestion();
		}else
			time.setText(timeleft.toString());
			
	}
	
	public void nextQuestion() {
		timer.t.interrupt();
		if(questionnumber < questions.size()-1) {
			questionnumber++;
			removeAll();
			initMainPanel();
			revalidate();
			timer = new Timer(this, questions.get(questionnumber).getTimelimit());
		}
		else {
			main.changetoGameOver(user, score);
		}
	}

}
