package cc.chinagps.gateway.unit.update;

import java.io.IOException;
import java.io.InputStream;

public interface UpdateFileInfo {
	public InputStream getInputStream() throws IOException;
	
	public void clearResource();
}