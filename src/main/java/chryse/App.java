package chryse;

import chryse.unit.ItemExtractor;
import wz.common.WzTool;
import wz.common.WzVersion;

public class App {

	private App() {
	}

	public static void main(String[] args) {
		Target t = new Target();

		t.LOCALE = WzVersion.GMS;
		t.VERSION = 83;
		t.KEY = WzTool.generateKey(t.LOCALE);

		Extractable e = new ItemExtractor();

		e.init(t, "Item");

		e.extract();

		System.out.println("Dumped successfully");
	}
}
