import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;

public class MyFont {

	Font font, font2;
	
	public MyFont() {
		// TODO Auto-generated constructor stub
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResource("/Anggota-PKoKx.ttf").openStream());
			font2 = Font.createFont(Font.TRUETYPE_FONT, getClass().getResource("/Montserrat-Black.ttf").openStream());
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   

		GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
		genv.registerFont(font);
		font = font.deriveFont(20f);
		genv.registerFont(font2);
		font2 = font2.deriveFont(20f);
	}

}
