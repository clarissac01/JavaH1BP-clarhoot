import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class HomePage extends JPanel{

	JPanel mainPanel, topPanel;
	JLabel title, signIn;
	JButton start, signUp;
	Main main;
	MyFont myfont = new MyFont();
	JTextField usernameField;
	JPasswordField passwordField;
	

	
	public void initTopPanel() {
		topPanel = new JPanel(new BorderLayout(10, 10));
		topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		topPanel.setBackground(Color.black);
		title = new JLabel("Clarhoot!");
		title.requestFocusInWindow();
		title.setForeground(Color.white);
		title.setFont(myfont.font.deriveFont(Font.BOLD, 20f));
		
		signUp = new JButton("SIGN UP");
		signUp.setFont(myfont.font2.deriveFont(Font.PLAIN, 15f));
		signUp.setPreferredSize(new Dimension(105, 50));
		signUp.setBackground(Color.decode("#864cbf"));
		signUp.setForeground(Color.white);
		signUp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				main.gotoSignUp();
			}
		});
		
		topPanel.add(title, BorderLayout.WEST);
		topPanel.add(signUp, BorderLayout.EAST);
	}
	
	public void initMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(350, 300));

		signIn = new JLabel("SIGN IN");
		signIn.requestFocusInWindow();
		signIn.setForeground(Color.decode("#45a3e5"));
		signIn.setFont(myfont.font2.deriveFont(Font.PLAIN, 40f));
		
		usernameField = new JTextField();
		JPanel uField = new JPanel();
		uField.setBackground(Color.white);
		ImageIcon uIcon = new ImageIcon("src//user.png");
		Image newIcon = uIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		JLabel userIcon = new JLabel(new ImageIcon(newIcon));
		uField.add(userIcon);
		usernameField.setForeground(Color.gray);
		usernameField.setText("Username");
		usernameField.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				if(usernameField.getText().equals("Username") || usernameField.getText().equals("")) {
					usernameField.setForeground(Color.gray);
					usernameField.setText("Username");
				}
			}
			
			@Override
			public void focusGained(FocusEvent f) {
				if(usernameField.getText().equals("Username")) {
					usernameField.setForeground(Color.black);
					usernameField.setText("");
				}
			}
		});
		usernameField.setPreferredSize(new Dimension(242, 40));
		usernameField.setFont(myfont.font2.deriveFont(Font.PLAIN, 20f));
		uField.add(usernameField);
		
		passwordField = new JPasswordField();
		JPanel pField = new JPanel();
		pField.setBackground(Color.white);
		ImageIcon pIcon = new ImageIcon("src//password.png");
		Image newpIcon = pIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		JLabel passIcon = new JLabel(new ImageIcon(newpIcon));
		pField.add(passIcon);
		passwordField.setForeground(Color.gray);
		passwordField.setText("Password");
		passwordField.setEchoChar((char)0);
		passwordField.setPreferredSize(new Dimension(242, 40));
		passwordField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				if(passwordField.getText().equals("Password") || passwordField.getText().equals("")) {
					passwordField.setEchoChar((char)0);
					passwordField.setForeground(Color.gray);
					passwordField.setText("Password");
				}
			}
			
			@Override
			public void focusGained(FocusEvent f) {
				if(passwordField.getText().equals("Password")) {
					passwordField.setEchoChar('*');
					passwordField.setForeground(Color.black);
					passwordField.setText("");
				}
			}
		});
		passwordField.setFont(myfont.font2.deriveFont(Font.PLAIN, 20f));
		pField.add(passwordField);
		
		start = new JButton("SIGN IN");
		start.setFont(myfont.font2.deriveFont(Font.PLAIN, 22f));
		start.setPreferredSize(new Dimension(300, 45));
		start.setBackground(Color.decode("#864cbf"));
		start.setForeground(Color.white);
		start.requestFocusInWindow();
		
		initNullPanel();
		mainPanel.add(signIn);
		mainPanel.add(uField);
		mainPanel.add(pField);
		mainPanel.add(start);
		
		start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(usernameField.getText().isEmpty() || usernameField.getText().equals("Username") || passwordField.getText().isEmpty() || passwordField.getText().equals("Password")) {
					JOptionPane.showMessageDialog(null, "Both fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}else {
					FileManager fileManager = FileManager.getInstance();
					
					User user = fileManager.doLogin("src\\users.csv", usernameField.getText(), passwordField.getText());
					
					if(user == null) {
						JOptionPane.showMessageDialog(null, "Invalid User!", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}else {
						JOptionPane.showMessageDialog(null, "Welcome, " + user.getUsername() + "!", "Success", JOptionPane.PLAIN_MESSAGE);
						if(user.getRole().equals("teacher")) {
							main.gotoTeacherPage(main, user);
						}else {
							main.gotoStudentPage(main, user);
						}
						return;
					}
				}
			}
		});
	}
	
	public HomePage(Main main) {
		this.main = main;
		
		setLayout(new BorderLayout());
		initTopPanel();
		initMainPanel();
		setBackground(Color.decode("#46178F"));
		add(topPanel, BorderLayout.NORTH);
		add(mainPanel, BorderLayout.CENTER);
	}
	

	private void initNullPanel() {
		// TODO Auto-generated method stub
		JPanel nullpanel = new JPanel();
		nullpanel.setPreferredSize(new Dimension(425, 120));
		mainPanel.add(nullpanel);
	}

}


















