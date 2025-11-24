import java.util.Vector;

public class TextStore {
	private Vector<String> v = new Vector<String>();
	
	public TextStore() {
		v.add("book");
		v.add("love");
		v.add("apple");
		v.add("banana");
		v.add("java");
	}
	
	public String get() {
		int index = (int)(Math.random()*v.size());
		return v.get(index);
	}
}
