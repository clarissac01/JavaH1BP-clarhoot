import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Vector;

public class FileManager {

	private static FileManager _instance_ = new FileManager();
	File userfile = new File("src\\users.csv");
	
	private FileManager() {
		// TODO Auto-generated constructor stub
	}
	
	public static FileManager getInstance() {
		return _instance_;
	}
	
	public Vector<User> getAllUsers(){
		BufferedReader bf;
		String datas = "";
		int index = 0;
		Vector<User> users = new Vector<>();
		try {
			File file = new File(".\\src\\users.csv");
			bf = new BufferedReader(new java.io.FileReader(file));
			
			while((datas = bf.readLine()) != null) {
				String[] data = datas.split(",");
				index++;
				if(index > 1) {
					users.add(new User(data[0], data[3], Integer.parseInt(data[2]), data[1]));
				}
				
			}
			bf.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}
	
	public Vector<String> getQuiz(String foldername){
		File folder = new File(".\\"+foldername);
		File[] listOfFiles = folder.listFiles();

		Vector<String> quizes = new Vector<>();
		try {
			for (File file : listOfFiles) {
				if (file.isFile()) {
					quizes.add(file.getName());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return quizes;
	}
	
	public Boolean deleteKahoot(String username, String filename) {
		File f = new File(".\\"+username + "\\"+filename);
		if(f.delete()) {
			return true;
		}
		return false;
	}
	
	public void createFile(User user, String filename, Vector<Question> questions) {
		String id = "";
		char a;
		Random rand = new Random();
		for (int i = 1; i <= 6; i++) {
			id += (rand.nextInt(10));
		}
		try {
			File file = new File(".\\"+user.getUsername()+"\\"+id+"-"+filename+".csv");
			
			FileWriter fw = new FileWriter(file);
			fw.write("question,limit,point,option1,option2,option3,option4,correctAnswer,questionType\n");
			for (Question q : questions) {
				if(q instanceof MultipleAnsQuestion) {
					fw.write(q.getQuestion() +"," + q.getTimelimit() + ","+ q.getPointtype() + "," + ((MultipleAnsQuestion)q).getAnswers().get(0) + "," + ((MultipleAnsQuestion)q).getAnswers().get(1) + "," + ((MultipleAnsQuestion)q).getAnswers().get(2) + "," + ((MultipleAnsQuestion)q).getAnswers().get(3) + "," + q.getCorrectAnswer() + ",Quiz\n");
				}else {
					fw.write(q.getQuestion() +"," + q.getTimelimit() + ","+ q.getPointtype() + ", , , , ," + q.getCorrectAnswer() + ",True or False\n");
				}
			}
			fw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateFile(User user, String newfilename, Vector<Question> questions, String oldfilename) {
		String id = "";
		File file;
		if(oldfilename.substring(oldfilename.indexOf('-')+1, oldfilename.indexOf(".")).equals(newfilename)) {
			file = new File(".\\"+user.getUsername()+"\\"+oldfilename);
		}else {
			id = oldfilename.substring(0, oldfilename.indexOf("-"));
			deleteKahoot(user.getUsername(), oldfilename);
			file = new File(".\\"+user.getUsername()+"\\"+id+"-"+newfilename+".csv");
		}
		try {
			FileWriter fw = new FileWriter(file);
			fw.write("question,limit,point,option1,option2,option3,option4,correctAnswer,questionType\n");
			for (Question q : questions) {
				if(q instanceof MultipleAnsQuestion) {
					fw.write(q.getQuestion() +"," + q.getTimelimit() + ","+ q.getPointtype() + "," + ((MultipleAnsQuestion)q).getAnswers().get(0) + "," + ((MultipleAnsQuestion)q).getAnswers().get(1) + "," + ((MultipleAnsQuestion)q).getAnswers().get(2) + "," + ((MultipleAnsQuestion)q).getAnswers().get(3) + "," + q.getCorrectAnswer() + ",Quiz\n");
				}else {
					fw.write(q.getQuestion() +"," + q.getTimelimit() + ","+ q.getPointtype() + ", , , , ," + q.getCorrectAnswer() + ",True or False\n");
				}
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Vector<Question> readQuestion(User user, String filename) {
		BufferedReader bf;
		String datas = "";
		Vector<Question> questions = new Vector<>();
		Vector<String> ans;
		int index = 0;
		try {
			bf = new BufferedReader(new java.io.FileReader(".\\"+user.getUsername()+"\\"+filename));
			
			while((datas = bf.readLine()) != null) {
				String[] data = datas.split(",");
				ans = new Vector<>();
				index++;
				if(index > 1) {
					if(data[8].equals("Quiz")) {
						ans.add(data[3]);
						ans.add(data[4]);
						ans.add(data[5]);
						ans.add(data[6]);
						questions.add(new MultipleAnsQuestion(data[0].toString(), data[7].toString(), Integer.parseInt(data[1].toString()), data[2].toString(), ans));
					}else if(data[8].equals("True or False")){
						questions.add(new BinaryQuestion(data[0], data[7], Integer.parseInt(data[1].toString()), data[2].toString()));
					}
				}
			}
			bf.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return questions;
	}
	
	public User doLogin(String filename, String username, String password) {
		BufferedReader bf;
		String datas = "";
		int index = 0;
		try {
			File file = new File(filename);
			bf = new BufferedReader(new java.io.FileReader(file));
			
			while((datas = bf.readLine()) != null) {
				String[] data = datas.split(",");
				index++;
				if(index > 1) {
					if(data[0].equals(username) && data[1].equals(password)) {
						return new User(data[0], data[3], Integer.parseInt(data[2]), data[1]);
					}
				}
				
			}
			bf.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public User checkUser(String username) {
		BufferedReader bf;
		String datas = "";
		int index = 0;
		try {
			bf = new BufferedReader(new java.io.FileReader(userfile));
			
			while((datas = bf.readLine()) != null) {
				String[] data = datas.split(",");
				index++;
				if(index != 1 && data[0].equals(username)) {
					return new User(data[0], data[3], Integer.parseInt(data[2]), data[1]);
				}
				
			}
			bf.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void createUser(String username, String password, String role) {
		try {
			FileWriter fr = new FileWriter(userfile, true);
			fr.write(username+ "," + password+ ",0," + role + "\n");
			if(role.equals("teacher")) {
				File dir = new File(".\\"+ username);
				if(!dir.exists()) {
					dir.mkdir();
				}
			}
			fr.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Vector<Question> getQuestionSet(String gamepin) {
		File folder = new File(".\\");
		File[] listOfFiles = folder.listFiles();

		Vector<Question> quizes = new Vector<>();

		try {
			Files.walk(Paths.get(".\\"))
			.filter(file->{
				return file.getFileName().toString().contains(gamepin);
			}).findFirst().ifPresent(file->{
				quizes.addAll(readQuestion(new User(file.getName(file.getNameCount()-2).toString(), "", 0, ""), file.getFileName().toString()));
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return quizes;
	}
	
}
