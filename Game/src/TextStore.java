import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class TextStore {
	private Vector<String> shortV = new Vector<String>(); // 푸른, 붉은 소행성에 나올 단어
	private Vector<String> longV = new Vector<String>(); // 회색 소행성에 나올 단어

	public TextStore() {
		loadWord();
	}

	public String getShortWord() { // 무작위 단어 반환
		int index = (int) (Math.random() * shortV.size());
		return shortV.get(index);
	}

	public String getLongWord() { // 무작위 단어 반환
		int index = (int) (Math.random() * longV.size());
		return longV.get(index);
	}

	private void loadWord() { // 파일에서 단어 읽기
		try {
			FileReader fr = new FileReader("file/text.txt"); // 파일열기
			BufferedReader br = new BufferedReader(fr); 

			String line = "";
			while (true) {
				line = br.readLine(); // 라인 읽기
				
				if (line == null) // 라인 null이면 종료
					break;

				if (line.trim().length() > 0) { // 값이 있을 때
					if (line.trim().length() < 7) // 6자리 이하 단어는 short에
						shortV.add(line.trim());
					else // 7자리 이상 단어는 long에 저장
						longV.add(line.trim());
				}
			}
			br.close(); // 파일 닫기
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
