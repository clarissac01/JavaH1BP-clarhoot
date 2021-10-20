import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class SignUp extends JPanel {

	Main main;
	JPanel topPanel, mainPanel;
	JComboBox<String> rolescb;
	MyFont myfont = new MyFont();
	Vector<String> roles;
	JLabel roleLbl, accLbl, title;
	JButton createAcc, signIn;
	JTextField uField;
	JPasswordField pField, cpField;

	public SignUp(Main main) {
		// TODO Auto-generated constructor stub
		this.main = main;
		setLayout(new BorderLayout());
		initTopPanel();
		initMainPanel();
		add(topPanel, BorderLayout.NORTH);
		add(mainPanel, BorderLayout.CENTER);
	}

	public void initTopPanel() {
		topPanel = new JPanel(new BorderLayout(10, 10));
		topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		topPanel.setBackground(Color.black);
		title = new JLabel("Clarhoot!");
		title.requestFocusInWindow();
		title.setForeground(Color.white);
		title.setFont(myfont.font.deriveFont(Font.BOLD, 20f));
		
		signIn = new JButton("SIGN IN");
		signIn.setFont(myfont.font2.deriveFont(Font.PLAIN, 15f));
		signIn.setPreferredSize(new Dimension(100, 50));
		signIn.setBackground(Color.decode("#864cbf"));
		signIn.setForeground(Color.white);
		signIn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				main.gotoSignIn();
			}
		});
		
		topPanel.add(title, BorderLayout.WEST);
		topPanel.add(signIn, BorderLayout.EAST);
		
	}

	public void initMainPanel() {
		mainPanel = new JPanel(new GridLayout(11, 1, 0, 3));
		mainPanel.setBorder(new EmptyBorder(0, 60, 0, 60));

		JLabel firstStep = new JLabel("1");
		firstStep.setHorizontalAlignment(JLabel.CENTER);
		firstStep.setFont(myfont.font2.deriveFont(Font.PLAIN, 25f));
		firstStep.setForeground(Color.decode("#45a3e5"));
		JLabel secondStep = new JLabel("2");
		secondStep.setHorizontalAlignment(JLabel.CENTER);
		secondStep.setForeground(Color.decode("#45a3e5"));
		secondStep.setFont(myfont.font2.deriveFont(Font.PLAIN, 25f));
		
		roleLbl = new JLabel("WHAT'S YOUR ROLE?");
		roleLbl.setHorizontalAlignment(JLabel.CENTER);
		roleLbl.setFont(myfont.font2.deriveFont(Font.PLAIN, 20f));
		
		roles = new Vector<>();
		roles.add("I'm a teacher");
		roles.add("I'm a student");
		
		JPanel rPanel = new JPanel();
		rolescb = new JComboBox<>(roles);
		rolescb.setPreferredSize(new Dimension(290, 35));
		rolescb.setBackground(Color.white);
		rolescb.setFont(myfont.font2.deriveFont(Font.PLAIN, 18f));
		rPanel.add(rolescb);
		
		mainPanel.add(firstStep);
		mainPanel.add(roleLbl);
		mainPanel.add(rPanel);

		accLbl = new JLabel("YOUR ACCOUNT DETAILS");
		accLbl.setHorizontalAlignment(JLabel.CENTER);
		accLbl.setFont(myfont.font2.deriveFont(Font.PLAIN, 20f));
		
		JPanel uPanel = new JPanel();
		uPanel.setBackground(Color.white);
		uField = new JTextField("Username");
		uField.setForeground(Color.gray);
		uField.setPreferredSize(new Dimension(235, 35));
		uField.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				if(uField.getText().equals("Username") || uField.getText().isEmpty()) {
					uField.setForeground(Color.gray);
					uField.setText("Username");
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if(uField.getText().equals("Username")) {
					uField.setForeground(Color.black);
					uField.setText("");
				}
			}
		});
		ImageIcon uIcon = new ImageIcon("src//user.png");
		Image newIcon = uIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		JLabel userIcon = new JLabel(new ImageIcon(newIcon));
		uField.setFont(myfont.font2.deriveFont(Font.PLAIN, 18f));
		uPanel.add(userIcon);
		uPanel.add(uField);
		
		JPanel pPanel = new JPanel();
		pPanel.setBackground(Color.white);
		pField = new JPasswordField("Password");
		pField.setForeground(Color.gray);
		pField.setPreferredSize(new Dimension(235, 35));
		pField.setEchoChar((char) 0);
		pField.setFont(myfont.font2.deriveFont(Font.PLAIN, 18f));
		pField.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				if(pField.getText().equals("Password") || pField.getText().isEmpty()) {
					pField.setEchoChar((char)0);
					pField.setForeground(Color.gray);
					pField.setText("Password");
				}
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				if(pField.getText().equals("Password")) {
					pField.setEchoChar('*');
					pField.setForeground(Color.black);
					pField.setText("");
				}
			}
		});
		ImageIcon pIcon = new ImageIcon("src//password.png");
		Image newpIcon = pIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		JLabel passIcon = new JLabel(new ImageIcon(newpIcon));
		pPanel.add(passIcon);
		pPanel.add(pField);
		
		JPanel cpPanel = new JPanel();
		cpPanel.setBackground(Color.white);
		cpField = new JPasswordField("Confirm Password");
		cpField.setForeground(Color.gray);
		cpField.setEchoChar((char) 0);
		cpField.setPreferredSize(new Dimension(235, 35));
		cpField.setFont(myfont.font2.deriveFont(Font.PLAIN, 18f));
		cpField.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				if(cpField.getText().equals("Confirm Password") || cpField.getText().isEmpty()) {
					cpField.setEchoChar((char)0);
					cpField.setForeground(Color.gray);
					cpField.setText("Confirm Password");
				}
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				if(cpField.getText().equals("Confirm Password")) {
					cpField.setEchoChar('*');
					cpField.setForeground(Color.black);
					cpField.setText("");
				}
			}
		});
		ImageIcon cpIcon = new ImageIcon("src//password.png");
		Image newcpIcon = pIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		JLabel cpassIcon = new JLabel(new ImageIcon(newcpIcon));
		cpField.setFont(myfont.font2.deriveFont(Font.PLAIN, 20f));
		cpPanel.add(cpassIcon);
		cpPanel.add(cpField);

		JPanel bPanel = new JPanel();
		createAcc = new JButton("CREATE ACCOUNT");
		createAcc.setPreferredSize(new Dimension(283, 40));
		createAcc.setFont(myfont.font2.deriveFont(Font.PLAIN, 20f));
		createAcc.setBackground(Color.decode("#864cbf"));
		createAcc.setForeground(Color.white);
		
		createAcc.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(uField.getText().equals("Username") || pField.getText().equals("Password") || cpField.getText().equals("Confirm Password")) {
					JOptionPane.showMessageDialog(null, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(FileManager.getInstance().checkUser(uField.getText()) != null) {
					JOptionPane.showMessageDialog(null, "This username is taken!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}else if (!pField.getText().equals(cpField.getText())){
					JOptionPane.showMessageDialog(null, "Password and Confirm Password must be the same!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}else {
					String therole = (String) rolescb.getSelectedItem();
					String[] userrole = therole.split(" ");
					FileManager.getInstance().createUser(uField.getText(), pField.getText(), userrole[2]);
					JOptionPane.showMessageDialog(null, "User created!", "Success", JOptionPane.INFORMATION_MESSAGE);
					
					main.gotoSignIn();
					
					return;
				}
			}
		});
		
		bPanel.add(createAcc);
		
		mainPanel.add(secondStep);
		mainPanel.add(accLbl);
		mainPanel.add(uPanel);
		mainPanel.add(pPanel);
		mainPanel.add(cpPanel);
		mainPanel.add(bPanel);
		
		
	}

}
