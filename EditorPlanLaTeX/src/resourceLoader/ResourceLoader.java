package resourceLoader;

import java.io.InputStream;
/**
 * load the used icon.
 * @author nadir
 * */
public class ResourceLoader {

	public static InputStream load(String path) {
		InputStream input=ResourceLoader.class.getResourceAsStream(path);
		if(input == null) {
			input = ResourceLoader.class.getResourceAsStream("/"+path);
		}
		return input;
	}
	
}
