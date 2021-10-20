import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class StudentPage extends JPanel{

	private JPanel topPanel, mainPanel;
	private JLabel title;
	private Main main;
	private User user;
	private JTextField gamepinField;
	private JButton enter;
	MyFont myfont = new MyFont();
	
	public StudentPage(Main main, User user) {
		// TODO Auto-generated constructor stub
		this.main = main;
		this.user = user;
		
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
		
		JLabel username;
		username = new JLabel(user.getUsername());
		username.setFont(myfont.font2.deriveFont(Font.BOLD, 20f));
		
	
		JButton signout = new JButton("<html><h3>Sign Out</h3></html>");
		signout.setBackground(Color.decode("#eb21b3c"));
		signout.setForeground(Color.white);
		
		signout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				main.gotoSignIn();
			}
		});
		
		topPanel.add(username, BorderLayout.WEST);
		topPanel.add(signout, BorderLayout.EAST);
		
	}
	
	private void initMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.decode("#33cccc"));
		mainPanel.setBorder(new EmptyBorder(50, 50,50,50));
		JLabel title = new JLabel();
		title = new JLabel("Clarhoot!");
		title.setBackground(Color.decode("#33cccc"));
		title.requestFocusInWindow();
		title.setForeground(Color.decode("#46178f"));
		title.setFont(myfont.font.deriveFont(Font.BOLD, 30f));

		gamepinField = new JTextField("Game PIN");
		gamepinField.setFont(gamepinField.getFont().deriveFont(Font.PLAIN, 23f));
		gamepinField.setPreferredSize(new Dimension(250, 50));
		gamepinField.setForeground(Color.gray);
		gamepinField.setHorizontalAlignment(SwingConstants.CENTER);
		
		gamepinField.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				if(gamepinField.getText().equals("") || gamepinField.getText().equals("Game PIN")) {
					gamepinField.setForeground(Color.gray);
					gamepinField.setText("Game PIN");
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if(gamepinField.getText().equals("Game PIN")) {
					gamepinField.setText("");
					gamepinField.setForeground(Color.black);
				}
			}
		});
		
		enter = new JButton("<html><h1>Enter</h1></html>");
		enter.setBackground(Color.black);
		enter.setForeground(Color.white);
		
		enter.setPreferredSize(new Dimension(250, 50));
	
		enter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(gamepinField.getText().equals("Game PIN")) {
					JOptionPane.showMessageDialog(null, "You must input Game PIN!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}else {
					if(FileManager.getInstance().getQuestionSet(gamepinField.getText()).size() == 0) {
						JOptionPane.showMessageDialog(null, "Invalid Game PIN!", "Quiz not found!", JOptionPane.ERROR_MESSAGE);
						return;
					}else {
						main.doQuiz(user, gamepinField.getText());
					}
				}
			}
		});
		
		mainPanel.add(title);
		mainPanel.add(gamepinField);
		mainPanel.add(enter);
		
	}
	
}
