package chryse.unit;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Paths;
import java.util.function.Consumer;

import javax.imageio.ImageIO;

import wz.WzFile;
import wz.WzImage;
import wz.WzObject;
import wz.WzProperty;
import wz.common.PNG;
import wz.common.WzTool;
import wz.io.WzMappedInputStream;
import chryse.Extractable;
import chryse.Target;

public class ItemExtractor implements Extractable {

	private WzFile item;
	
	public ItemExtractor() {}
	
	public void init(Target t) {
		if (t.KEY == null)
			t.KEY = WzTool.generateKey(t.LOCALE);
		
		WzMappedInputStream in = new WzMappedInputStream(Paths.get("Item.wz"));
		
		in.setKey(t.KEY);
		
		item = new WzFile("Item.wz", (short) 83);
		
		item.parse(in);
		
		// any additional set-up
	}
	
	public void extract() {
		if (item == null) {
			// XXX complain about not initializing?
			return;
		}
		
		WzObject<?, ?> root = item.getRoot();
		
		iterateInternal(root, ItemExtractor::exportImage);
	}
	
	private void iterateInternal(WzObject<?, ?> root, Consumer<WzObject<?, ?>> export) {
		if (root.getChildren() == null) return;
		
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
		
		//System.out.println(path);

		WzProperty<?> prop = (WzProperty<?>) export;
		PNG value = (PNG) prop.getValue();
		
		Image img = value.getImage(false);
		
		try {
			File f = new File("dump/" + path + ".png");
			if (f.exists())
				f.delete();
			else {
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
