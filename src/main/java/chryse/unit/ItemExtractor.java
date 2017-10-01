package chryse.unit;

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

		iterateInternal(root);
	}

	private void iterateInternal(WzObject<?, ?> root) {
		if (root.getChildren() == null) {
			return;
		}

		for (WzObject<?, ?> child : root) {
			if (child instanceof WzProperty<?>) {
				WzProperty<?> prop = (WzProperty<?>) child;
				Object value = prop.getValue();

				if (value instanceof PNG) {
					String path = prop.getFullPath();

					if (!path.contains("icon")) {
						continue;
					}

					if (path.contains("Raw")) {
						continue;
					}

					exportImage(prop);
				}
			}

			iterateInternal(child);

			if (child instanceof WzImage) {
				((WzImage) child).unparse();
			}
		}
	}

}
