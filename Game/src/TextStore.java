import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class TextStore {
	private Vector<String> v = new Vector<String>();
	
	public TextStore() {
		loadWord();
	}
	
	public String getWord() {
		int index = (int)(Math.random()*v.size());
		return v.get(index);
	}
	
	private void loadWord() {
		try {
			FileReader fr = new FileReader("text/text.txt");
			BufferedReader br = new BufferedReader(fr);
			
			String line = "";
			while(true) {
				line = br.readLine();
				if (line ==null) break;
				
				if(line.trim().length()>0) {
					v.add(line);
				}
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
