package cc.chinagps.gateway.unit.update;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import cc.chinagps.gateway.util.IOUtil;

public class DefaultUpdateFileInfo implements UpdateFileInfo{
	private File file;
	
	private long startPosition;
	
	public DefaultUpdateFileInfo(File file, long startPosition){
		this.file = file;
		this.startPosition = startPosition;
	}
	
	private InputStream is;
	
	@Override
	public InputStream getInputStream() throws IOException {
		if(is == null){
			is = new FileInputStream(file);
			is.skip(startPosition);
		}
		
		return is;
	}

	@Override
	public void clearResource() {
		IOUtil.closeIS(is);
	}
}