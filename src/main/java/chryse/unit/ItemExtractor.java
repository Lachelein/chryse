package chryse.unit;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.function.Consumer;

import javax.imageio.ImageIO;

import chryse.Extractable;
import chryse.Target;
import wz.WzImage;
import wz.WzObject;
import wz.WzProperty;
import wz.common.PNG;

public class ItemExtractor extends Extractable {

	@Override
	public void init(Target t, String wz) {
		super.init(t, wz);
		// any additional set-up
	}

	@Override
	public void extract() {
		if (wzFile == null) {
			// XXX complain about not initializing?
			return;
		}

		WzObject<?, ?> root = wzFile.getRoot();

		iterateInternal(root, ItemExtractor::exportImage);
	}

	private void iterateInternal(WzObject<?, ?> root, Consumer<WzObject<?, ?>> export) {
		if (root.getChildren() == null) {
			return;
		}

		for (WzObject<?, ?> child : root) {
			if (child instanceof WzProperty<?>) {
				WzProperty<?> prop = (WzProperty<?>) child;
				Object value = prop.getValue();

				if (value instanceof PNG) {
					export.accept(prop);
				}
			}

			iterateInternal(child, export);

			if (child instanceof WzImage) {
				((WzImage) child).unparse();
			}
		}
	}

	private static void exportImage(WzObject<?, ?> export) {
		String path = export.getFullPath();

		// System.out.println(path);

		WzProperty<?> prop = (WzProperty<?>) export;
		PNG value = (PNG) prop.getValue();

		Image img = value.getImage(false);

		try {
			File f = new File("dump/" + path + ".png");
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

}
