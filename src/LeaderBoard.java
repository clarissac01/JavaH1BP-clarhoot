import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class LeaderBoard extends JPanel implements MouseListener{

	JPanel titlePanel, scorePanel, panel;
	JLabel title;
	JList<String> scorelist;
	User currUser;
	Vector<User> user = new Vector<>();
	Vector<String> scores = new Vector<String>();
	MyFont myfont = new MyFont();
	JButton playagain;
	Main main;
	int currIdx = 0;
	
	public void initMainPanel() {
		titlePanel = new JPanel();
		titlePanel.setBackground(Color.white);
		titlePanel.setPreferredSize(new Dimension(425, 60));
		
		title = new JLabel("Scoreboard");
		title.setFont(myfont.font2.deriveFont(Font.PLAIN, 40f));
		
		titlePanel.add(title);
		
		for (User u : user) {
			scores.add(String.format("%-2d %-10s", u.getScore(), u.getUsername()));
			if(u.getUsername().equals(currUser.getUsername()))
				currIdx = user.indexOf(u);
		}
		
		scorelist = new JList<>(scores);
		
		scorelist.setBackground(Color.decode("#eb670f"));
		scorelist.setFont(myfont.font2.deriveFont(Font.PLAIN, 30f));
		
		scorelist.setSelectedIndex(currIdx);
		
		scorelist.addMouseListener(this);
		
		scorePanel = new JPanel(new GridLayout(1, 2));
		scorePanel.add(scorelist);
		
		panel = new JPanel();
		panel.setBackground(Color.decode("#eb670f"));
		JScrollPane sp = new JScrollPane(scorePanel);
		sp.setPreferredSize(new Dimension(305, 300));
		sp.setHorizontalScrollBar(null);
		sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel.add(sp);
		
		playagain = new JButton("Play Again", new ImageIcon("loop.png"));
		playagain.setBackground(Color.decode("#45a3e5"));
		playagain.setForeground(Color.white);
		playagain.setPreferredSize(new Dimension(100, 75));
		playagain.setFont(myfont.font2.deriveFont(Font.PLAIN, 35f));
		
		add(titlePanel, BorderLayout.NORTH);
		add(panel, BorderLayout.CENTER);
		add(playagain, BorderLayout.SOUTH);
		
		playagain.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				main.playAgain(currUser);
			}
		});
		
	}
	
	public LeaderBoard(Main main, Vector<User> users, User user) {
		// TODO Auto-generated constructor stub
		for (User u : users) {
			if(u.getRole().equals("student")) {
				this.user.add(u);
			}
		}
		this.main = main;
		this.currUser = user;
		Collections.sort(this.user);
		setLayout(new BorderLayout());
		setBackground(Color.decode("#eb670f"));
		initMainPanel();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == scorelist) {
			scorelist.setSelectedIndex(scorelist.getSelectedIndex());
		}
		else {
			scorelist.setSelectedIndex(scorelist.getSelectedIndex());
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
