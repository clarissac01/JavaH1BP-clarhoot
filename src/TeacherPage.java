import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class TeacherPage extends JPanel implements ActionListener{

	Main main;
	User user;
	JButton createKahoot;
	JPanel mainPanel, topPanel;
	MyFont myfont = new MyFont();
	Vector<String> quizlists = new Vector<>();
	
	public TeacherPage(Main main, User user) {
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
		
		JLabel username, title;
		username = new JLabel(user.getUsername());
		username.setFont(myfont.font2.deriveFont(Font.BOLD, 20f));
		
		title = new JLabel("Clarhoot!");
		title.requestFocusInWindow();
		title.setForeground(Color.decode("#46178f"));
		title.setFont(myfont.font.deriveFont(Font.BOLD, 13f));

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
		mainPanel.setBorder(new EmptyBorder(10,10,10,10));
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(425, 55));
		createKahoot = new JButton("Create clarhoot");
		createKahoot.setPreferredSize(new Dimension(210, 45));
		createKahoot.setBackground(Color.decode("#0542b9"));
		createKahoot.setFont(myfont.font2.deriveFont(Font.PLAIN, 20f));
		createKahoot.setForeground(Color.white);
		
		buttonPanel.add(createKahoot);
		createKahoot.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				main.createKahoot(user);
			}
		});
		
		mainPanel.add(buttonPanel);

		quizlists = FileManager.getInstance().getQuiz(user.getUsername());
		if(quizlists.size()==0) {
			JPanel quizPnl = new JPanel();
			quizPnl.setBorder(new EmptyBorder(50, 10, 50, 10));
			JLabel quizLists = new JLabel("<html><u>Create a quiz now!</u></html>");
			quizLists.setHorizontalAlignment(JLabel.LEFT);
			quizLists.setForeground(Color.decode("#028282"));
			quizLists.setFont(myfont.font2.deriveFont(Font.PLAIN, 25f));
			quizPnl.add(quizLists);
			
			mainPanel.add(quizPnl);
		}else {
			JPanel quizPnl = new JPanel();
			quizPnl.setPreferredSize(new Dimension(350, 300));
			quizPnl.setBorder(new EmptyBorder(25, 10, 10, 10));
			JLabel quizLists = new JLabel("<html><u>Your quiz lists: </u></html>");
			quizLists.setForeground(Color.decode("#028282"));
			quizLists.setFont(myfont.font2.deriveFont(Font.PLAIN, 25f));
			quizPnl.add(quizLists);
			
			for (String q : quizlists) {
				JPanel panel = new JPanel(new BorderLayout());
				panel.setPreferredSize(new Dimension(350, 50));
				panel.setBackground(Color.decode("#99e5e5"));
				JPanel cPanel = new JPanel(new GridLayout(1, 2, 5, 5));
				cPanel.setBackground(Color.decode("#99e5e5"));
				JButton update = new JButton("UPDATE");
				update.setBackground(Color.decode("#ffdd33"));
				update.setFont(myfont.font2.deriveFont(Font.PLAIN, 12f));
				JButton delete = new JButton("DELETE");
				delete.setBackground(Color.decode("#fad09e"));
				delete.setFont(myfont.font2.deriveFont(Font.PLAIN, 12f));
				
				JLabel qname = new JLabel(q.substring(0, q.indexOf(".")));
				qname.setFont(myfont.font2.deriveFont(Font.PLAIN, 16f));
				cPanel.add(update);
				cPanel.add(delete);
				
				panel.setBorder(new EmptyBorder(10, 10, 10, 10));
				
				panel.add(qname, BorderLayout.WEST);
				panel.add(cPanel, BorderLayout.EAST);
				
				quizPnl.add(panel);

				update.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						main.updateQuestionset(user, q);
					}
				});
				
				delete.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						if(FileManager.getInstance().deleteKahoot(user.getUsername(), q)) {
							JOptionPane.showMessageDialog(null, "Success delete this clarhoot!", (q.substring(q.indexOf("-")+1, q.indexOf(".")))+" Deleted!", JOptionPane.INFORMATION_MESSAGE);
						}
						removeAll();
						initTopPanel();
						add(topPanel, BorderLayout.NORTH);
						initMainPanel();
						add(mainPanel, BorderLayout.CENTER);
						revalidate();
						
						return;
					}
				});
				
				
			}
			
			mainPanel.add(quizPnl);
		}
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
