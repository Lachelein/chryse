package chryse;

import wz.common.WzTool;
import wz.common.WzVersion;
import chryse.unit.ItemExtractor;

public class App 
{
	
	private App() {}
	
    public static void main( String[] args )
    {
    	Target t = new Target();
    	
    	t.LOCALE = WzVersion.GMS;
    	t.VERSION = 83;
    	t.KEY = WzTool.generateKey(t.LOCALE);
    	
    	Extractable e = new ItemExtractor();
    	
    	e.init(t);
    	
    	e.extract();
    }
}
