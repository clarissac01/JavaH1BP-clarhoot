import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ManageQuestions extends JPanel{

	Main main;
	User user;
	MyFont myfont = new MyFont();
	Vector<Question> questions = new Vector<>();
	
	private JPanel topPanel, mainPanel, leftPanel, rightPanel;
	private JLabel title, questiontype, timelimit, points, trueLbl, falseLbl;
	private JButton exit, done, duplicate, addquestion, delete;
	private JTextField titleField, questionField, ans1Field, ans2Field, ans3Field, ans4Field;
	private JComboBox<String> qtypescb, timecb, pointcb;
	private Vector<String> types, times, pointv, qdesc;
	private JList<String> qlists;
	private JScrollPane spane;
	private Integer qidx = 0;
	private ButtonGroup bg;
	private JCheckBox ach, bch, cch, dch, tch, fch;
	private String qTitle;
	
	public ManageQuestions(Main main, User user) {
		// TODO Auto-generated constructor stub
		this.main = main;
		this.user = user;
		setLayout(new BorderLayout());
		
		questions.add(new MultipleAnsQuestion("Start typing your question","",0,"",null));
		
		initTopPanel();
		add(topPanel, BorderLayout.NORTH);
		initMainPanel();
		add(mainPanel, BorderLayout.CENTER);
	}
	
	public ManageQuestions(Main main, User user, Vector<Question> q, String quizTitle) {
		this.main = main;
		this.user = user;
		this.questions = q;
		this.qTitle = quizTitle;
		setLayout(new BorderLayout());
		
		initTopPanel();
		add(topPanel, BorderLayout.NORTH);
		initMainPanel();
		add(mainPanel, BorderLayout.CENTER);
	}
	
	private void initTopPanel() {
		topPanel = new JPanel(new BorderLayout());
		topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		topPanel.setBackground(Color.white);
		
		title = new JLabel("Clarhoot!   ");
		title.setForeground(Color.decode("#46178f"));
		title.setFont(myfont.font.deriveFont(Font.PLAIN, 22f));
		titleField = new JTextField("Enter clarhoot title...");
		titleField.setFont(myfont.font2.deriveFont(Font.PLAIN, 20f));
		
		titleField.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				if(titleField.getText().equals("") || titleField.getText().equals("Enter clarhoot title...")) {
					titleField.setForeground(Color.gray);
					titleField.setText("Enter clarhoot title...");
				}
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				if(titleField.getText().equals("Enter clarhoot title...")) {
					titleField.setText("");
					titleField.setForeground(Color.black);
				}
			}
		});
		
		JPanel tpnl = new JPanel();
		tpnl.setBackground(Color.white);
		titleField.setForeground(Color.gray);
		titleField.setBackground(Color.white);
		titleField.setPreferredSize(new Dimension(300, 40));
		titleField.setHorizontalAlignment(JTextField.LEFT);

		if(qTitle != null) {
			titleField.setText(qTitle.substring(qTitle.indexOf("-")+1, qTitle.indexOf(".")));
			titleField.setForeground(Color.black);
		}
		
		tpnl.add(title);
		tpnl.add(titleField);
		
		topPanel.add(tpnl, BorderLayout.WEST);
		
		JPanel pnl = new JPanel(new GridLayout(1, 2, 10, 10));
		pnl.setBackground(Color.white);
		exit = new JButton("<html><h3>Exit</h3></html>");
		exit.setBackground(Color.white);
		exit.setPreferredSize(new Dimension(100, 40));
		exit.setForeground(Color.black);
		
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int ans = JOptionPane.showConfirmDialog(null, "Your quiz might be deleted", "Are you sure?", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				
				if(ans == 0) {
					main.backToTeacherPage(user);
				}
			}
		});
		
		done = new JButton("<html><h3>Done</h3></html>");
		done.setPreferredSize(new Dimension(100, 40));
		done.setBackground(Color.decode("#26890c"));
		done.setForeground(Color.white);
		
		done.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(titleField.getText().equals("") || titleField.getText().equals("Enter clarhoot title...")) {
					JOptionPane.showMessageDialog(null, "Title need to be filled before you can start playing.", "This clarhoot can't be played", JOptionPane.ERROR_MESSAGE);
					return;
				}else if(questions.size() < 5) {
					JOptionPane.showMessageDialog(null, "Questions must be minimal 5.", "Your question is not enough", JOptionPane.ERROR_MESSAGE);
					return;
				}else {
//					if(questions.get(qidx).getQuestion().equals("") || questions.get(qidx).getQuestion().equals("Start typing your question")) {
						if(questions.get(qidx) instanceof MultipleAnsQuestion) {
							questions.get(qidx).setQuestion(questionField.getText());
							Vector<String> ans = new Vector<>();
							ans.add(ans1Field.getText());
							ans.add(ans2Field.getText());
							ans.add(ans3Field.getText());
							ans.add(ans4Field.getText());
							((MultipleAnsQuestion) questions.get(qidx)).setAnswers(ans);
							questions.get(qidx).setPointtype(pointcb.getSelectedItem().toString());
							questions.get(qidx).setTimelimit(Integer.parseInt(timecb.getSelectedItem().toString().substring(0, timecb.getSelectedItem().toString().indexOf(" "))));
							
							if(ach.isSelected()) {
								questions.get(qidx).setCorrectAnswer(ans1Field.getText());
							}
							if(bch.isSelected()) {
								questions.get(qidx).setCorrectAnswer(ans2Field.getText());
							}
							if(cch.isSelected()) {
								questions.get(qidx).setCorrectAnswer(ans3Field.getText());
							}
							if(dch.isSelected()) {
								questions.get(qidx).setCorrectAnswer(ans4Field.getText());
							}
						}else {
							if(tch.isSelected()) {
								questions.get(qidx).setCorrectAnswer("TRUE");
							}
							else {
								questions.get(qidx).setCorrectAnswer("FALSE");
							}
							questions.get(qidx).setQuestion(questionField.getText());
							questions.get(qidx).setPointtype(pointcb.getSelectedItem().toString());
							questions.get(qidx).setTimelimit(Integer.parseInt(timecb.getSelectedItem().toString().substring(0, timecb.getSelectedItem().toString().indexOf(" "))));
						}
//					}
					
					if(qTitle == null) {
						JOptionPane.showMessageDialog(null, "Success creating new clarhoot!", "Success", JOptionPane.INFORMATION_MESSAGE);
						FileManager.getInstance().createFile(user, titleField.getText(), questions);
						main.backToTeacherPage(user);
					}else {
						JOptionPane.showMessageDialog(null, "Success update clarhoot!", "Success", JOptionPane.INFORMATION_MESSAGE);
						FileManager.getInstance().updateFile(user, titleField.getText(), questions, qTitle);
						main.backToTeacherPage(user);
					}
				}
			}
		});
		
		pnl.add(exit);
		pnl.add(done);
		
		topPanel.add(pnl, BorderLayout.EAST);
	}
	
	private void initLeftPanel() {
		leftPanel = new JPanel(new BorderLayout());
		
		JPanel qpnl = new JPanel();
		qdesc = new Vector<>();
		
		for (int i = 0; i < questions.size(); i++) {
			if(questions.get(i) instanceof MultipleAnsQuestion)
				qdesc.add((i+1) + " Quiz");
			else
				qdesc.add((i+1) + " True or false");
		}
		
		qlists = new JList<>(qdesc);
		qlists.setFont(qlists.getFont().deriveFont(15f));
		spane = new JScrollPane(qlists);
		
		addquestion = new JButton("<html><h3>Add question</h3></html>");
		addquestion.setBackground(Color.decode("#0542b9"));
		addquestion.setForeground(Color.white);
		
		addquestion.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(questions.get(qidx) instanceof MultipleAnsQuestion) {
					if(ans1Field.getText().equals("Add answer 1") || ans2Field.getText().equals("Add answer 2") || ans3Field.getText().equals("Add answer 3") || ans4Field.getText().equals("Add answer 4") ) {
						JOptionPane.showMessageDialog(null, "Question isn't complete!", "Can't add new question!", JOptionPane.ERROR_MESSAGE);
						return;
					}else {
						Vector<String> ans = new Vector<>();
						ans.add(ans1Field.getText());
						ans.add(ans2Field.getText());
						ans.add(ans3Field.getText());
						ans.add(ans4Field.getText());
						((MultipleAnsQuestion) questions.get(qidx)).setAnswers(ans);
						questions.get(qidx).setPointtype(pointcb.getSelectedItem().toString());
						questions.get(qidx).setTimelimit(Integer.parseInt(timecb.getSelectedItem().toString().substring(0, timecb.getSelectedItem().toString().indexOf(" "))));
					}
					if(!ach.isSelected() && !bch.isSelected() &&!cch.isSelected() &&!dch.isSelected() ) {
						JOptionPane.showMessageDialog(null, "Question isn't complete!", "Can't add new question!", JOptionPane.ERROR_MESSAGE);
						return;
					}else {
						if(ach.isSelected()) {
							questions.get(qidx).setCorrectAnswer(ans1Field.getText());
						}
						if(bch.isSelected()) {
							questions.get(qidx).setCorrectAnswer(ans2Field.getText());
						}
						if(cch.isSelected()) {
							questions.get(qidx).setCorrectAnswer(ans3Field.getText());
						}
						if(dch.isSelected()) {
							questions.get(qidx).setCorrectAnswer(ans4Field.getText());
						}
					}
					if(!questionField.getText().equals("Start typing your question") && (questions.get(qidx).getCorrectAnswer()!=null) && (questions.get(qidx).getPointtype()!=null) && (questions.get(qidx).getTimelimit()!=0) && 
							(((MultipleAnsQuestion) questions.get(qidx)).getAnswers()!= null)) {
						questions.get(qidx).setQuestion(questionField.getText());
						questions.add(new MultipleAnsQuestion("Start typing your question","",0,"",null));
						qidx++;
						removeAll();
						add(topPanel, BorderLayout.NORTH);
						initMainPanel();
						add(mainPanel, BorderLayout.CENTER);
						revalidate();
					}else {
						JOptionPane.showMessageDialog(null, "Question isn't complete!", "Can't add new question!", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
				}else {
					if(!tch.isSelected() && !fch.isSelected() && (questionField.getText().equals("") || questionField.getText().equals("Start typing your question"))) {
						JOptionPane.showMessageDialog(null, "Question isn't complete!", "Can't add new question!", JOptionPane.ERROR_MESSAGE);
						return;
					}else {
						if(tch.isSelected()) {
							questions.get(qidx).setCorrectAnswer("TRUE");
						}
						else {
							questions.get(qidx).setCorrectAnswer("FALSE");
						}
						questions.get(qidx).setQuestion(questionField.getText());
						questions.get(qidx).setPointtype(pointcb.getSelectedItem().toString());
						questions.get(qidx).setTimelimit(Integer.parseInt(timecb.getSelectedItem().toString().substring(0, timecb.getSelectedItem().toString().indexOf(" "))));
					}
					if(!questionField.getText().equals("Start typing your question") && (questions.get(qidx).getCorrectAnswer()!=null) && (questions.get(qidx).getPointtype()!=null) && (questions.get(qidx).getTimelimit()!=0)) {
						questions.add(new BinaryQuestion("Start typing your question", "", 0, ""));
						qidx++;
						removeAll();
						add(topPanel, BorderLayout.NORTH);
						initMainPanel();
						add(mainPanel, BorderLayout.CENTER);
						revalidate();
					}else {
						JOptionPane.showMessageDialog(null, "Question isn't complete!", "Can't add new question!", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
				}
			}
		});
		
		leftPanel.add(spane, BorderLayout.CENTER);
		
		qpnl.add(addquestion);
		qpnl.setBackground(Color.white);
		qpnl.setBorder(new EmptyBorder(20, 20, 20, 20));
		
		qlists.setSelectedIndex(qidx);
		
		qlists.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if(questions.get(qidx) instanceof MultipleAnsQuestion) {
					if(ans1Field.getText().equals("Add answer 1") || ans2Field.getText().equals("Add answer 2") || ans3Field.getText().equals("Add answer 3") || ans4Field.getText().equals("Add answer 4") ) {
						JOptionPane.showMessageDialog(null, "You must complete this question first!", "Can't move to other question!", JOptionPane.ERROR_MESSAGE);
						qlists.setSelectedIndex(qidx);
						return;
					}else {
						Vector<String> ans = new Vector<>();
						ans.add(ans1Field.getText());
						ans.add(ans2Field.getText());
						ans.add(ans3Field.getText());
						ans.add(ans4Field.getText());
						((MultipleAnsQuestion) questions.get(qidx)).setAnswers(ans);
						questions.get(qidx).setPointtype(pointcb.getSelectedItem().toString());
						questions.get(qidx).setTimelimit(Integer.parseInt(timecb.getSelectedItem().toString().substring(0, timecb.getSelectedItem().toString().indexOf(" "))));
					}
					if(!ach.isSelected() && !bch.isSelected() &&!cch.isSelected() &&!dch.isSelected() ) {
						JOptionPane.showMessageDialog(null, "You must complete this question first!", "Can't move to other question!", JOptionPane.ERROR_MESSAGE);
						qlists.setSelectedIndex(qidx);
						return;
					}else {
						if(ach.isSelected()) {
							questions.get(qidx).setCorrectAnswer(ans1Field.getText());
						}
						if(bch.isSelected()) {
							questions.get(qidx).setCorrectAnswer(ans2Field.getText());
						}
						if(cch.isSelected()) {
							questions.get(qidx).setCorrectAnswer(ans3Field.getText());
						}
						if(dch.isSelected()) {
							questions.get(qidx).setCorrectAnswer(ans4Field.getText());
						}
					}
					if(!questionField.getText().equals("Start typing your question") && (questions.get(qidx).getCorrectAnswer()!=null) && (questions.get(qidx).getPointtype()!=null) && (questions.get(qidx).getTimelimit()!=0) && 
							(((MultipleAnsQuestion) questions.get(qidx)).getAnswers()!= null)) {
						questions.get(qidx).setQuestion(questionField.getText());
					}else {
						JOptionPane.showMessageDialog(null, "You must complete this question first!", "Can't move to other question!", JOptionPane.ERROR_MESSAGE);
						qlists.setSelectedIndex(qidx);
						return;
					}
					
				}else {
					if(!tch.isSelected() && !fch.isSelected() && (questionField.getText().equals("") || questionField.getText().equals("Start typing your question"))) {
						JOptionPane.showMessageDialog(null, "You must complete this question first!", "Can't move to other question!", JOptionPane.ERROR_MESSAGE);
						qlists.setSelectedIndex(qidx);
						return;
					}else {
						if(tch.isSelected()) {
							questions.get(qidx).setCorrectAnswer("TRUE");
						}
						else {
							questions.get(qidx).setCorrectAnswer("FALSE");
						}
						questions.get(qidx).setPointtype(pointcb.getSelectedItem().toString());
						questions.get(qidx).setTimelimit(Integer.parseInt(timecb.getSelectedItem().toString().substring(0, timecb.getSelectedItem().toString().indexOf(" "))));
					}
					if(!questionField.getText().equals("Start typing your question") && (questions.get(qidx).getCorrectAnswer()!=null) && (questions.get(qidx).getPointtype()!=null) && (questions.get(qidx).getTimelimit()!=0)) {
						questions.get(qidx).setQuestion(questionField.getText());
					}else {
						JOptionPane.showMessageDialog(null, "You must complete this question first!", "Can't move to other question!", JOptionPane.ERROR_MESSAGE);
						qlists.setSelectedIndex(qidx);
						return;
					}

					
				}
				
				qidx = qlists.getSelectedIndex();
				removeAll();
				add(topPanel, BorderLayout.NORTH);
				initMainPanel();
				add(mainPanel, BorderLayout.CENTER);
				revalidate();
			}
		});

		leftPanel.add(qpnl, BorderLayout.SOUTH);
	}
	
	private void initRightPanel() {
		rightPanel = new JPanel(new GridLayout(8, 1, 10, 10));
		rightPanel.setBackground(Color.white);
		rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JPanel qtPnl = new JPanel();
		qtPnl.setBackground(Color.white);
		qtPnl.setPreferredSize(new Dimension(200, 50));
		ImageIcon aII = new ImageIcon("src//type.png");
		Image aIcon = aII.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		JLabel aLabel = new JLabel(new ImageIcon(aIcon));
		qtPnl.add(aLabel);
		
		questiontype = new JLabel("<html><h2>Question Type</h2></html>");
		qtPnl.add(questiontype);
		
		JPanel tsPnl = new JPanel();
		tsPnl.setBackground(Color.white);
		types = new Vector<>();
		types.add("Quiz");
		types.add("True or False");
		qtypescb = new JComboBox<>(types);
		qtypescb.setFont(qtypescb.getFont().deriveFont(18f));
		qtypescb.setBackground(Color.white);
		qtypescb.setForeground(Color.black);
		qtypescb.setPreferredSize(new Dimension(150, 30));
		tsPnl.add(qtypescb);
		tsPnl.setPreferredSize(new Dimension(200, 50));
		
		qtypescb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(questions.get(qidx) instanceof MultipleAnsQuestion) {
					if(qtypescb.getSelectedIndex() == 1) {
						Question q = new BinaryQuestion(questionField.getText(), "", Integer.parseInt(timecb.getSelectedItem().toString().substring(0, timecb.getSelectedItem().toString().indexOf(" "))), pointcb.getSelectedItem().toString());
						questions.remove(questions.get(qidx));
						questions.add(qidx, q);
						removeAll();
						add(topPanel, BorderLayout.NORTH);
						initMainPanel();
						add(mainPanel, BorderLayout.CENTER);
						revalidate();
					}
				}else {
					if(qtypescb.getSelectedIndex()==0) {
						Question q = new MultipleAnsQuestion(questionField.getText(), "", Integer.parseInt(timecb.getSelectedItem().toString().substring(0, timecb.getSelectedItem().toString().indexOf(" "))), pointcb.getSelectedItem().toString(), null);
						questions.remove(questions.get(qidx));
						questions.add(qidx, q);
						
						removeAll();
						add(topPanel, BorderLayout.NORTH);
						initMainPanel();
						add(mainPanel, BorderLayout.CENTER);
						revalidate();
					}
				}
			}
		});
		
		JPanel tlPnl = new JPanel();
		tlPnl.setBackground(Color.white);
		tlPnl.setPreferredSize(new Dimension(200, 50));
		
		ImageIcon bII = new ImageIcon("src//time.png");
		Image bIcon = bII.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		JLabel bLabel = new JLabel(new ImageIcon(bIcon));
		tlPnl.add(bLabel);
		timelimit = new JLabel("<html><h2>Time limit</h2></html>");

		tlPnl.add(timelimit);
		
		times = new Vector<>();
		times.add("5 seconds");
		times.add("10 seconds");
		times.add("20 seconds");
		times.add("30 seconds");

		JPanel tcPnl = new JPanel();
		tcPnl.setBackground(Color.white);
		timecb = new JComboBox<>(times);
		timecb.setFont(timecb.getFont().deriveFont(18f));
		timecb.setBackground(Color.white);
		timecb.setForeground(Color.black);
		timecb.setPreferredSize(new Dimension(150, 30));
		tcPnl.add(timecb);
		tcPnl.setPreferredSize(new Dimension(200, 50));
		
		JPanel pPnl = new JPanel();
		pPnl.setBackground(Color.white);
		pPnl.setPreferredSize(new Dimension(200, 50));
		ImageIcon cII = new ImageIcon("src//badge.png");
		Image cIcon = cII.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		JLabel cLabel = new JLabel(new ImageIcon(cIcon));
		
		pPnl.add(cLabel);
		
		points = new JLabel("<html><h2>Points</h2></html>");
		pPnl.add(points);
		
		pointv = new Vector<>();
		pointv.add("Standard");
		pointv.add("Double points");
		pointv.add("No points");
		
		JPanel pcPnl = new JPanel();
		pcPnl.setBackground(Color.white);
		pointcb = new JComboBox<>(pointv);
		pointcb.setFont(pointcb.getFont().deriveFont(18f));
		pointcb.setBackground(Color.white);
		pointcb.setForeground(Color.black);
		pointcb.setPreferredSize(new Dimension(150, 30));
		pcPnl.add(pointcb);
		
		JPanel buttonPnl = new JPanel(new GridLayout(1, 2, 20, 20));
		buttonPnl.setPreferredSize(new Dimension(200, 50));
		buttonPnl.setBackground(Color.white);
		
		delete = new JButton("<html><h4>Delete</h4></html>");
		delete.setBackground(Color.white);
		delete.setForeground(Color.black);
		
		delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(questions.size() == 1 && qidx == 0) {
					JOptionPane.showMessageDialog(null, "To make a game engaging, we recommend adding at least one question.", "Can't delete the only question in your clarhoot", JOptionPane.ERROR_MESSAGE);
					return;
				}else {
					questions.remove(questions.get(qidx));
					qidx--;
					removeAll();
					initTopPanel();
					add(topPanel, BorderLayout.NORTH);
					initMainPanel();
					add(mainPanel, BorderLayout.CENTER);
					revalidate();
				}
			}
		});
		
		duplicate = new JButton("<html><h4>Duplicate</h4></html>");
		duplicate.setBackground(Color.white);
		duplicate.setForeground(Color.black);
		
		duplicate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(questions.get(qidx) instanceof MultipleAnsQuestion) {
					if(ans1Field.getText().equals("Add answer 1") || ans2Field.getText().equals("Add answer 2") || ans3Field.getText().equals("Add answer 3") || ans4Field.getText().equals("Add answer 4") ) {
						JOptionPane.showMessageDialog(null, "Question isn't complete!", "Can't duplicate this question!", JOptionPane.ERROR_MESSAGE);
						return;
					}else {
						Vector<String> ans = new Vector<>();
						ans.add(ans1Field.getText());
						ans.add(ans2Field.getText());
						ans.add(ans3Field.getText());
						ans.add(ans4Field.getText());
						((MultipleAnsQuestion) questions.get(qidx)).setAnswers(ans);
						questions.get(qidx).setPointtype(pointcb.getSelectedItem().toString());
						questions.get(qidx).setTimelimit(Integer.parseInt(timecb.getSelectedItem().toString().substring(0, timecb.getSelectedItem().toString().indexOf(" "))));
					}
					if(!ach.isSelected() && !bch.isSelected() &&!cch.isSelected() &&!dch.isSelected() ) {
						JOptionPane.showMessageDialog(null, "Question isn't complete!", "Can't duplicate this question!", JOptionPane.ERROR_MESSAGE);
						return;
					}else {
						if(ach.isSelected()) {
							questions.get(qidx).setCorrectAnswer(ans1Field.getText());
						}
						if(bch.isSelected()) {
							questions.get(qidx).setCorrectAnswer(ans2Field.getText());
						}
						if(cch.isSelected()) {
							questions.get(qidx).setCorrectAnswer(ans3Field.getText());
						}
						if(dch.isSelected()) {
							questions.get(qidx).setCorrectAnswer(ans4Field.getText());
						}
					}
					if(!questionField.getText().equals("Start typing your question") && (questions.get(qidx).getCorrectAnswer()!=null) && (questions.get(qidx).getPointtype()!=null) && (questions.get(qidx).getTimelimit()!=0) && 
							(((MultipleAnsQuestion) questions.get(qidx)).getAnswers()!= null)) {
						questions.get(qidx).setQuestion(questionField.getText());
						try {
							questions.add((Question) questions.get(qidx).clone());
						} catch (CloneNotSupportedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						qidx++;
						removeAll();
						add(topPanel, BorderLayout.NORTH);
						initMainPanel();
						add(mainPanel, BorderLayout.CENTER);
						revalidate();
					}else {
						JOptionPane.showMessageDialog(null, "Question isn't complete!", "Can't duplicate this question!", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
				}else {
					if(!tch.isSelected() && !fch.isSelected()) {
						JOptionPane.showMessageDialog(null, "Question isn't complete!", "Can't duplicate this question!", JOptionPane.ERROR_MESSAGE);
						return;
					}else {
						if(tch.isSelected()) {
							questions.get(qidx).setCorrectAnswer("TRUE");
						}
						else {
							questions.get(qidx).setCorrectAnswer("FALSE");
						}
						questions.get(qidx).setPointtype(pointcb.getSelectedItem().toString());
						questions.get(qidx).setTimelimit(Integer.parseInt(timecb.getSelectedItem().toString().substring(0, timecb.getSelectedItem().toString().indexOf(" "))));
					}
					if(!questionField.getText().equals("Start typing your question") && (questions.get(qidx).getCorrectAnswer()!=null) && (questions.get(qidx).getPointtype()!=null) && (questions.get(qidx).getTimelimit()!=0)) {
						questions.get(qidx).setQuestion(questionField.getText());
						try {
							questions.add((Question) questions.get(qidx).clone());
						} catch (CloneNotSupportedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						qidx++;
						removeAll();
						add(topPanel, BorderLayout.NORTH);
						initMainPanel();
						add(mainPanel, BorderLayout.CENTER);
						revalidate();
					}else {
						JOptionPane.showMessageDialog(null, "Question isn't complete!", "Can't duplicate this question!", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
				}
			}
		});
		
		if(!questions.get(qidx).getPointtype().equals("")) {
			pointcb.setSelectedItem(questions.get(qidx).getPointtype());
		}
		if(questions.get(qidx).getTimelimit() != 0) {
			timecb.setSelectedItem(questions.get(qidx).getTimelimit() + " seconds");
		}
		if(questions.get(qidx) instanceof MultipleAnsQuestion) {
			qtypescb.setSelectedIndex(0);
		}else {
			qtypescb.setSelectedIndex(1);
		}
		
		buttonPnl.add(delete);
		buttonPnl.add(duplicate);
		
		rightPanel.add(qtPnl);
		rightPanel.add(tsPnl);
		rightPanel.add(tlPnl);
		rightPanel.add(tcPnl);
		rightPanel.add(pPnl);
		rightPanel.add(pcPnl);
				
		JPanel pnl = new JPanel();
		pnl.setBackground(Color.white);
		
		rightPanel.add(pnl);
		rightPanel.add(buttonPnl);
	}
	
	private void initMainPanel() {
		mainPanel = new JPanel(new BorderLayout());
		
		questionField = new JTextField("Start typing your question");
		questionField.setFont(questionField.getFont().deriveFont(23f));
		questionField.setHorizontalAlignment(SwingConstants.CENTER);
		questionField.setPreferredSize(new Dimension(500, 100));
		questionField.setForeground(Color.gray);
		if(!questions.get(qidx).getQuestion().equals("Start typing your question")) {
			questionField.setText(questions.get(qidx).getQuestion());
			questionField.setForeground(Color.black);
		}else if(questions.get(qidx).getQuestion().equals("")) {
			questionField = new JTextField("Start typing your question");
			questionField.setForeground(Color.gray);
		}
		
		questionField.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				if(questionField.getText().equals("Start typing your question") || questionField.getText().equals("")) {
					questionField.setForeground(Color.gray);
					questionField.setText("Start typing your question");
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if(questionField.getText().equals("Start typing your question")) {
					questionField.setText("");
					questionField.setForeground(Color.black);
				}
			}
		});
		
		JPanel mainP = new JPanel();
		mainP.setBorder(new EmptyBorder(30, 300, 30, 300));
		
		if(questions.get(qidx) instanceof MultipleAnsQuestion) {
			
			bg = new ButtonGroup();
			ach = new JCheckBox();
			bch = new JCheckBox();
			cch = new JCheckBox();
			dch = new JCheckBox();
			bg.add(ach);
			bg.add(bch);
			bg.add(cch);
			bg.add(dch);
			
			JPanel pnl = new JPanel(new GridLayout(2, 2, 10, 10));
			pnl.setPreferredSize(new Dimension(500, 200));
			
			JPanel a = new JPanel();
			a.setBackground(Color.white);
			JPanel b = new JPanel();
			b.setBackground(Color.white);
			JPanel c = new JPanel();
			c.setBackground(Color.white);
			JPanel d = new JPanel();
			d.setBackground(Color.white);
			
			JPanel aimgpnl = new JPanel();
			ImageIcon aII = new ImageIcon("src//triangle.png");
			Image aIcon = aII.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			JLabel aLabel = new JLabel(new ImageIcon(aIcon));
			aimgpnl.setBackground(Color.decode("#eb21b3c"));
			ach.setBackground(Color.decode("#eb21b3c"));
			aimgpnl.add(aLabel);
			aimgpnl.setBorder(new EmptyBorder(12, 0, 0, 0));
			aimgpnl.setPreferredSize(new Dimension(50, 80));
			a.add(aimgpnl);
			
			ans1Field = new JTextField("Add answer 1");
			ans1Field.setPreferredSize(new Dimension(130, 80));
			ans1Field.setFont(ans1Field.getFont().deriveFont(20f));
			ans1Field.setForeground(Color.gray);
			ach.setVisible(false);
			if(((MultipleAnsQuestion)questions.get(qidx)).getAnswers() != null) {
				ans1Field.setText(((MultipleAnsQuestion)questions.get(qidx)).getAnswers().get(0));
				ans1Field.setForeground(Color.white);
				ans1Field.setBackground(Color.decode("#eb21b3c"));
				a.setBackground(Color.decode("#eb21b3c"));
				ach.setVisible(true);
			}
			a.add(ans1Field);
			a.add(ach);
			
			ans1Field.addFocusListener(new FocusListener() {
				
				@Override
				public void focusLost(FocusEvent e) {
					// TODO Auto-generated method stub
					if(ans1Field.getText().equals("Add answer 1") || ans1Field.getText().equals("")) {
						ans1Field.setForeground(Color.gray);
						ans1Field.setText("Add answer 1");
					}
				}
				
				@Override
				public void focusGained(FocusEvent e) {
					// TODO Auto-generated method stub
					if(ans1Field.getText().equals("Add answer 1")) {
						ans1Field.setText("");
						ans1Field.setForeground(Color.white);
					}
				}
			});

			ans1Field.getDocument().addDocumentListener(new DocumentListener() {
				
				@Override
				public void removeUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					change();
				}
				
				@Override
				public void insertUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					change();
				}
				
				@Override
				public void changedUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					change();
				}
				
				public void change() {
					if(!ans1Field.getText().equals("") && !ans1Field.getText().equals("Add answer 1")) {
						ans1Field.setBackground(Color.decode("#eb21b3c"));
						a.setBackground(Color.decode("#eb21b3c"));
						ach.setVisible(true);
					}else {
						ans1Field.setBackground(Color.white);
						a.setBackground(Color.white);
						ach.setVisible(false);
					}
				}
			});
			
			JPanel bimgpnl = new JPanel();
			ImageIcon bII = new ImageIcon("src//diamond.png");
			Image bIcon = bII.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			JLabel bLabel = new JLabel(new ImageIcon(bIcon));
			bimgpnl.setBackground(Color.decode("#1368ce"));
			bch.setBackground(Color.decode("#1368ce"));
			bimgpnl.setBorder(new EmptyBorder(12, 0, 0, 0));
			bimgpnl.add(bLabel);
			bimgpnl.setPreferredSize(new Dimension(50, 80));
			b.add(bimgpnl);
			bch.setVisible(false);
			
			ans2Field = new JTextField("Add answer 2");
			ans2Field.setFont(ans2Field.getFont().deriveFont(20f));
			ans2Field.setPreferredSize(new Dimension(130, 80));
			ans2Field.setForeground(Color.gray);
			if(((MultipleAnsQuestion)questions.get(qidx)).getAnswers() != null) {
				ans2Field.setText(((MultipleAnsQuestion)questions.get(qidx)).getAnswers().get(1));
				ans2Field.setBackground(Color.decode("#1368ce"));
				ans2Field.setForeground(Color.white);
				b.setBackground(Color.decode("#1368ce"));
				bch.setVisible(true);
			}
			b.add(ans2Field);
			b.add(bch);
			
			ans2Field.addFocusListener(new FocusListener() {
				
				@Override
				public void focusLost(FocusEvent e) {
					// TODO Auto-generated method stub
					if(ans2Field.getText().equals("Add answer 2") || ans2Field.getText().equals("")) {
						ans2Field.setForeground(Color.gray);
						ans2Field.setText("Add answer 2");
					}
				}
				
				@Override
				public void focusGained(FocusEvent e) {
					// TODO Auto-generated method stub
					if(ans2Field.getText().equals("Add answer 2")) {
						ans2Field.setText("");
						ans2Field.setForeground(Color.white);
					}
				}
			});
			
			ans2Field.getDocument().addDocumentListener(new DocumentListener() {
				
				@Override
				public void removeUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					change();
				}
				
				@Override
				public void insertUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					change();
				}
				
				@Override
				public void changedUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					change();
				}
				
				public void change() {
					if(!ans2Field.getText().equals("") && !ans2Field.getText().equals("Add answer 2")) {
						ans2Field.setBackground(Color.decode("#1368ce"));
						b.setBackground(Color.decode("#1368ce"));
						bch.setVisible(true);
					}else {
						ans2Field.setBackground(Color.white);
						b.setBackground(Color.white);
						bch.setVisible(false);
					}
				}
			});
			
			JPanel cimgpnl = new JPanel();
			ImageIcon cII = new ImageIcon("src//circle.png");
			Image cIcon = cII.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			JLabel cLabel = new JLabel(new ImageIcon(cIcon));
			cimgpnl.setBorder(new EmptyBorder(12, 0, 0, 0));
			cimgpnl.setBackground(Color.decode("#ffa602"));
			cch.setBackground(Color.decode("#ffa602"));
			cimgpnl.add(cLabel);
			cimgpnl.setPreferredSize(new Dimension(50, 80));
			c.add(cimgpnl);
			cch.setVisible(false);
			
			ans3Field = new JTextField("Add answer 3");
			ans3Field.setFont(ans3Field.getFont().deriveFont(20f));
			ans3Field.setPreferredSize(new Dimension(130, 80));
			ans3Field.setForeground(Color.gray);
			if(((MultipleAnsQuestion)questions.get(qidx)).getAnswers() != null) {
				ans3Field.setText(((MultipleAnsQuestion)questions.get(qidx)).getAnswers().get(2));
				ans3Field.setForeground(Color.white);
				ans3Field.setBackground(Color.decode("#ffa602"));
				c.setBackground(Color.decode("#ffa602"));
				cch.setVisible(true);
			}
			c.add(ans3Field);
			c.add(cch);
			
			ans3Field.addFocusListener(new FocusListener() {
				
				@Override
				public void focusLost(FocusEvent e) {
					// TODO Auto-generated method stub
					if(ans3Field.getText().equals("Add answer 3") || ans3Field.getText().equals("")) {
						ans3Field.setForeground(Color.gray);
						ans3Field.setText("Add answer 3");
					}
				}
				
				@Override
				public void focusGained(FocusEvent e) {
					// TODO Auto-generated method stub
					if(ans3Field.getText().equals("Add answer 3")) {
						ans3Field.setText("");
						ans3Field.setForeground(Color.white);
					}
				}
			});
			
			ans3Field.getDocument().addDocumentListener(new DocumentListener() {
				
				@Override
				public void removeUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					change();
				}
				
				@Override
				public void insertUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					change();
				}
				
				@Override
				public void changedUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					change();
				}
				
				public void change() {
					if(!ans3Field.getText().equals("") && !ans3Field.getText().equals("Add answer 3")) {
						ans3Field.setBackground(Color.decode("#ffa602"));
						c.setBackground(Color.decode("#ffa602"));
						cch.setVisible(true);
					}else {
						ans3Field.setBackground(Color.white);
						c.setBackground(Color.white);
						cch.setVisible(false);
					}
				}
			});
			
			JPanel dimgpnl = new JPanel();
			dimgpnl.setBorder(new EmptyBorder(12, 0, 0, 0));
			ImageIcon dII = new ImageIcon("src//rectangle.png");
			Image dIcon = dII.getImage().getScaledInstance(35, 40, Image.SCALE_SMOOTH);
			JLabel dLabel = new JLabel(new ImageIcon(dIcon));
			dimgpnl.add(dLabel);
			dimgpnl.setBackground(Color.decode("#26890c"));
			dch.setBackground(Color.decode("#26890c"));
			dimgpnl.setPreferredSize(new Dimension(50, 80));
			d.add(dimgpnl);
			
			ans4Field = new JTextField("Add answer 4");
			ans4Field.setFont(ans4Field.getFont().deriveFont(20f));
			ans4Field.setPreferredSize(new Dimension(130, 80));
			ans4Field.setForeground(Color.gray);
			dch.setVisible(false);
			if(((MultipleAnsQuestion)questions.get(qidx)).getAnswers() != null) {
				ans4Field.setText(((MultipleAnsQuestion)questions.get(qidx)).getAnswers().get(3));
				ans4Field.setForeground(Color.white);
				ans4Field.setBackground(Color.decode("#26890c"));
				d.setBackground(Color.decode("#26890c"));
				dch.setVisible(true);
			}
			d.add(ans4Field);
			d.add(dch);
			
			ans4Field.addFocusListener(new FocusListener() {
				
				@Override
				public void focusLost(FocusEvent e) {
					// TODO Auto-generated method stub
					if(ans4Field.getText().equals("Add answer 4") || ans4Field.getText().equals("")) {
						ans4Field.setForeground(Color.gray);
						ans4Field.setText("Add answer 4");
					}
				}
				
				@Override
				public void focusGained(FocusEvent e) {
					// TODO Auto-generated method stub
					if(ans4Field.getText().equals("Add answer 4")) {
						ans4Field.setText("");
						ans4Field.setForeground(Color.white);
					}
				}
			});
			
			ans4Field.getDocument().addDocumentListener(new DocumentListener() {
				
				@Override
				public void removeUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					change();
				}
				
				@Override
				public void insertUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					change();
				}
				
				@Override
				public void changedUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					change();
				}
				
				public void change() {
					if(!ans4Field.getText().equals("") && !ans4Field.getText().equals("Add answer 4")) {
						ans4Field.setBackground(Color.decode("#26890c"));
						d.setBackground(Color.decode("#26890c"));
						dch.setVisible(true);
					}else {
						ans4Field.setBackground(Color.white);
						d.setBackground(Color.white);
						dch.setVisible(false);
					}
				}
			});
			
			if(questions.get(qidx).getCorrectAnswer().equals(ans1Field.getText())) {
				ach.setSelected(true);
			}
			else if(questions.get(qidx).getCorrectAnswer().equals(ans2Field.getText())) {
				bch.setSelected(true);
			}
			if(questions.get(qidx).getCorrectAnswer().equals(ans3Field.getText())) {
				cch.setSelected(true);
			}
			if(questions.get(qidx).getCorrectAnswer().equals(ans4Field.getText())) {
				dch.setSelected(true);
			}
			
			a.setBorder(new EmptyBorder(2, 0, 0, 0));
			b.setBorder(new EmptyBorder(2, 0, 0, 0));
			c.setBorder(new EmptyBorder(2, 0, 0, 0));
			d.setBorder(new EmptyBorder(2, 0, 0, 0));
			
			pnl.add(a);
			pnl.add(b);
			pnl.add(c);
			pnl.add(d);
			mainP.add(questionField);
			mainP.add(pnl);
		}else {
			JPanel pnl = new JPanel(new GridLayout(1, 2, 10, 10));
			pnl.setPreferredSize(new Dimension(500, 100));
			
			bg = new ButtonGroup();
			tch = new JCheckBox();
			fch = new JCheckBox();
			bg.add(tch);
			bg.add(fch);
			
			JPanel a = new JPanel();
			a.setBackground(Color.white);
			JPanel b = new JPanel();
			b.setBackground(Color.white);
			
			JPanel aimgpnl = new JPanel();
			ImageIcon aII = new ImageIcon("src//diamond.png");
			Image aIcon = aII.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			JLabel aLabel = new JLabel(new ImageIcon(aIcon));
			aimgpnl.add(aLabel);
			aimgpnl.setBorder(new EmptyBorder(12, 0, 0, 0));
			aimgpnl.setPreferredSize(new Dimension(50, 80));
			aimgpnl.setBackground(Color.decode("#1368ce"));
			tch.setBackground(Color.decode("#1368ce"));
			a.setBackground(Color.decode("#1368ce"));
			aLabel.setBackground(Color.decode("#1368ce"));
			a.add(aimgpnl);
			
			trueLbl = new JLabel("<html><h1><b>TRUE</b></h1></html>");
			trueLbl.setForeground(Color.white);
			trueLbl.setBackground(Color.decode("#1368ce"));
			a.add(trueLbl);
			a.add(tch);
			
			JPanel bimgpnl = new JPanel();
			ImageIcon bII = new ImageIcon("src//triangle.png");
			Image bIcon = bII.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			JLabel bLabel = new JLabel(new ImageIcon(bIcon));
			bimgpnl.setBackground(Color.decode("#eb21b3c"));
			bLabel.setBackground(Color.decode("#eb21b3c"));
			fch.setBackground(Color.decode("#eb21b3c"));
			b.setBackground(Color.decode("#eb21b3c"));
			bimgpnl.setBorder(new EmptyBorder(12, 0, 0, 0));
			bimgpnl.add(bLabel);
			bimgpnl.setPreferredSize(new Dimension(50, 80));
			b.add(bimgpnl);
			
			falseLbl = new JLabel("<html><h1><b>FALSE</b></h1></html>");
			falseLbl.setForeground(Color.white);
			falseLbl.setBackground(Color.decode("#eb21b3c"));
			b.add(falseLbl);
			b.add(fch);
			
			if(questions.get(qidx).getCorrectAnswer().equals("TRUE")) {
				tch.setSelected(true);
			}else if(questions.get(qidx).getCorrectAnswer().equals("FALSE")) {
				fch.setSelected(true);
			}
			
			pnl.add(a);
			pnl.add(b);
			
			mainP.add(questionField);
			mainP.add(pnl);
		}
		
		
		mainPanel.add(mainP, BorderLayout.CENTER);
		
		initRightPanel();
		mainPanel.add(rightPanel, BorderLayout.EAST);
		
		initLeftPanel();
		mainPanel.add(leftPanel, BorderLayout.WEST);
		
	}
	
}
