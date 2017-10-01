package chryse;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import wz.WzFile;
import wz.WzObject;
import wz.WzProperty;
import wz.common.PNG;
import wz.io.WzMappedInputStream;

public abstract class Extractable {

	protected WzFile wzFile;
	protected String wz;

	public void init(Target t, String wz) {
		this.wz = wz;

		WzMappedInputStream stream = new WzMappedInputStream(Paths.get("wz/" + wz + ".wz"));
		stream.setKey(t.KEY);

		wzFile = new WzFile("wz/" + wz + ".wz", t.VERSION);
		wzFile.parse(stream);
	}

	protected boolean isNumeric(String s) {
		return s != null && s.matches("[-+]?\\d*\\.?\\d+");
	}

	protected int getId(String path) {
		String[] split = path.split("/");
		int id = 0;

		for (String s : split) {

			if (s.contains("info") || s.contains("icon")) {
				break;
			}

			if (s.contains(".img")) {
				s = s.substring(0, s.length() - 4);
			}

			if (isNumeric(s)) {
				id = Integer.parseInt(s);
			}
		}

		return id;
	}

	protected void exportImage(WzObject<?, ?> export) {
		String path = export.getFullPath();
		int id = getId(path);

		System.out.println(path + " has ID of " + id);

		if (id == 0) {
			return;
		}

		WzProperty<?> prop = (WzProperty<?>) export;
		PNG value = (PNG) prop.getValue();

		Image img = value.getImage(false);

		try {
			File f = new File("dump/" + wz + "/" + id + ".png");
			if (f.exists()) {
				f.delete();
			} else {
				f.mkdirs();
				f.createNewFile();
			}

			ImageIO.write((BufferedImage) img, "png", f);
		} catch (Exception e) {
			e.printStackTrace();
			// XXX swallow? ;)
		}

		img.flush();
	}

	public abstract void extract();
}
