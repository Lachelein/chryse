package chryse;

import java.nio.file.Paths;

import wz.WzFile;
import wz.io.WzMappedInputStream;

public abstract class Extractable {

	protected WzFile wzFile;

	public void init(Target t, String wz) {

		WzMappedInputStream stream = new WzMappedInputStream(Paths.get("wz/" + wz));
		stream.setKey(t.KEY);

		wzFile = new WzFile("wz/" + wz, t.VERSION);
		wzFile.parse(stream);
	}

	public abstract void extract();
}
