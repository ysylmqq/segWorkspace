package cc.chinagps.gateway.util.filecacher;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileCacher {
	public Object getWriteLock();
	
	public boolean hasReadFile();
	
	public boolean hasWriteData();
	
	public boolean write(byte[] data);
	
	public List<FData> read() throws IOException;
	
	public List<FData> read(int limit) throws IOException;
	
	public void confirmRead(FData data) throws IOException;
	
	public void confirmRead(List<FData> list) throws IOException;
	
	public File getReadFile();
	
	public File getWriteFile();
}