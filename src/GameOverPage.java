import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameOverPage extends JPanel{

	JLabel title;
	JLabel yourscore;
	JLabel score;
	int userscore;
	JCheckBox savescore;
	MyFont myfont = new MyFont();
	JButton nextbtn;
	Main main;
	User user;
	
	private void initMainPanel() {
		title = new JLabel("Game Over");
		title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		yourscore = new JLabel("Your Score");
		score = new JLabel("    "+userscore+"    ");
		
		title.setFont(title.getFont().deriveFont(Font.PLAIN, 50f));
		yourscore.setFont(yourscore.getFont().deriveFont(Font.PLAIN, 45f));
		score.setFont(myfont.font2.deriveFont(Font.PLAIN, 80f));
		
		savescore = new JCheckBox("Save Score?");
		savescore.setFont(savescore.getFont().deriveFont(Font.PLAIN, 50f));
		
		ImageIcon icon = new ImageIcon("nexticonblack.png");
		Image img = icon.getImage().getScaledInstance(35, 40, Image.SCALE_SMOOTH);
		icon = new ImageIcon(img);
		
		nextbtn = new JButton("Next", icon);
		nextbtn.setFont(myfont.font2.deriveFont(Font.PLAIN, 33f));
		nextbtn.setPreferredSize(new Dimension(200, 75));
		
		nextbtn.setBackground(Color.decode("#45a3e5"));
		savescore.setBackground(Color.decode("#a2d1f2"));

		title.setForeground(Color.white);
		yourscore.setForeground(Color.white);
		score.setForeground(Color.white);
		savescore.setForeground(Color.white);
		
		JPanel nullPanel = new JPanel();
		nullPanel.setPreferredSize(new Dimension(425, 100));
		nullPanel.setBackground(Color.decode("#a2d1f2"));

		JPanel nullPanel2 = new JPanel();
		nullPanel2.setPreferredSize(new Dimension(425, 50));
		nullPanel2.setBackground(Color.decode("#a2d1f2"));
		
		add(title);
		add(yourscore);
		add(score);
		add(nullPanel);
		add(savescore);
		add(nullPanel2);
		add(nextbtn);
		
		nextbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				main.changetoLeaderBoard(savescore.isSelected(), user, userscore);
				return;
			}
		});

	}
	
	public GameOverPage(int score, Main main, User user) {
		// TODO Auto-generated constructor stub
		this.main = main;
		this.user = user;
		userscore = score;
		setBackground(Color.decode("#a2d1f2"));
		initMainPanel();
	}

}
