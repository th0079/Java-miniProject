import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class TextStore {
	private Vector<String> shortV = new Vector<String>();
	private Vector<String> longV = new Vector<String>();

	public TextStore() {
		loadWord();
	}

	public String getShortWord() {
		int index = (int) (Math.random() * shortV.size());
		return shortV.get(index);
	}

	public String getLongWord() {
		int index = (int) (Math.random() * longV.size());
		return longV.get(index);
	}

	private void loadWord() {
		try {
			FileReader fr = new FileReader("text/text.txt");
			BufferedReader br = new BufferedReader(fr);

			String line = "";
			while (true) {
				line = br.readLine();
				if (line == null)
					break;

				if (line.trim().length() > 0) {
					if (line.trim().length() < 7)
						shortV.add(line);
					else
						longV.add(line);
				}
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
