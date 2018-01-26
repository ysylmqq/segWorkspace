package cc.chinagps.gateway.util.filecacher;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import org.seg.lib.util.Util;

public class DefaultFileCacher implements FileCacher{
	public Object readLock = new Object();
	
	public Object writeLock = new Object();
	
	private long max_file_size;
	
	//private String filePath;
	
	private static final String FILE_NAME_WRITE = "w.dat";
	
	private static final String FILE_NAME_READ = "r.dat";
	
	//write
	private File write_file;
	private OutputStream write_os;
	
	//read
	private File read_file;
	private InputStream read_is;
	//private RandomAccessFile read_random;
	private FileChannel read_random;
	
	public DefaultFileCacher(long max_file_size, String filePath) throws IOException{
		this.max_file_size = max_file_size;
		//this.filePath = filePath;
		
		//init write
		this.write_file = new File(filePath, FILE_NAME_WRITE);
		this.write_os = new FileOutputStream(write_file, true);

		//init read
		this.read_file = new File(filePath, FILE_NAME_READ);
	}
	
	@Override
	public Object getWriteLock() {
		return writeLock;
	}

	@Override
	public boolean hasReadFile() {
		synchronized (readLock) {
			return read_file.exists();
		}
		//boolean w_has = (write_file.exists() && write_file.length() > 0);
		//return w_has || r_has;
	}
	
	@Override
	public boolean hasWriteData(){
		return (write_file.exists() && write_file.length() > 0);
	}

	private static final byte STX = (byte) 0xfe;
	
	/**
	 * data-format:
	 * 
	 * stx[1]  		(0xFE)
	 * flag[1]		(0 or 1)
	 * offset[4]
	 * length[4]
	 * data[length]
	 * 
	 *   stx   flag      offset           length                   data
	 * --------------------------------------------------------------------------------
	 * |  1  |  1  |       4         |       4         |           length             | 
	 *  --------------------------------------------------------------------------------
	 */
	@Override
	public boolean write(byte[] data) {
		synchronized (writeLock) {
			if(write_file.length() > max_file_size){
				return false;
			}
			
			try{
				if(write_os == null){
					write_os = new FileOutputStream(write_file, true);
				}
				
				byte[] save = new byte[10 + data.length];
				save[0] = STX;
				save[1] = 0;
				byte[] offset = Util.getIntByte((int) write_file.length());
				System.arraycopy(offset, 0, save, 2, offset.length);
				byte[] length = Util.getIntByte(data.length);
				System.arraycopy(length, 0, save, 6, length.length);
				System.arraycopy(data, 0, save, 10, data.length);
				
				write_os.write(save);
				write_os.flush();
				
				writeLock.notifyAll();
				return true;
			}catch (Throwable e) {
				e.printStackTrace();
				return false;
			}
		}
	}
	
	private int defaultReadLimit = 500;

	public int getDefaultReadLimit() {
		return defaultReadLimit;
	}

	public void setDefaultReadLimit(int defaultReadLimit) {
		this.defaultReadLimit = defaultReadLimit;
	}

	@Override
	public List<FData> read() throws IOException {
		return read(defaultReadLimit);
	}

	/**
	 * 1. change filename from write-name to read_name(file handler)
	 *    when read & no_read_file & write_file_has_data > 0
	 * 
	 * 2. delete read file
	 *    when read & read_no_data
	 * @throws IOException 
	 */
	@Override
	public List<FData> read(int limit) throws IOException {
		synchronized (readLock) {
			if(limit <= 0){
				throw new IllegalArgumentException("limit must bigger than 0, limit:" + limit);
			}
			
			//no read file
			if(!read_file.exists()){
				//change write to read
				synchronized (writeLock) {
					if(write_file.exists() && write_file.length() > 0){
						close(write_os);
						write_os = null;
						
						boolean success = write_file.renameTo(read_file);
						if(!success){
							throw new IOException("rename fileName failed, rename fileName from:" 
									+ write_file.getAbsolutePath() + " to " + read_file.getAbsolutePath());
						}
						
						write_os = new FileOutputStream(write_file);				
					}else{
						//no read and no write
						return null;
					}
				}
			}
			
			if(read_is == null){
				read_is = new FileInputStream(read_file);
			}
			
			//read data
			List<FData> list = new ArrayList<FData>(limit);
			byte[] bs_head = new byte[10];
			while(read_is.available() > 0 && list.size() < limit){
				read_is.read(bs_head);
				byte stx = bs_head[0];
				if(stx != STX){
					throw new IOException("error when read data file, stx:" + stx);
				}
				
				byte flag = bs_head[1];
				int offset = Util.getInt(bs_head, 2);
				int length = Util.getInt(bs_head, 6);
				
				if(flag == 1){
					read_is.skip(length);
				}else{
					byte[] bs_data = new byte[length];
					read_is.read(bs_data);
					
					FData fData = new FData();
					fData.setOffset(offset);
					fData.setData(bs_data);
					list.add(fData);
				}
			}
			
			//System.out.println("list.size:" + list.size());
			//no data, delete read file
			if(list.size() == 0){
				close(read_is);
				close(read_random);
				read_is = null;
				read_random = null;
				
				boolean success = read_file.delete();
				if(!success){
					System.out.println("read_random:" + read_random);
					throw new IOException("delete file failed, fileName is " + read_file.getAbsolutePath());
				}
			}
			
			return list;
		}
	}
	
	private void close(Closeable c){
		if(c != null){
			try {
				c.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private ByteBuffer success = ByteBuffer.wrap(new byte[]{1});
	
	@Override
	public void confirmRead(FData data) throws IOException {
		synchronized (readLock){
			if(read_random == null){
				if(read_file.exists()){
					//read_random = new RandomAccessFile(read_file, "rwd");
					read_random = new RandomAccessFile(read_file, "rwd").getChannel();
				}
			}
			
			int offset = data.getOffset();
			//read_random.seek(offset + 1);
			//read_random.write(1);
			read_random.position(offset + 1);
			success.flip();
			read_random.write(success);
		}
	}

	@Override
	public void confirmRead(List<FData> list) throws IOException {
		synchronized (readLock){
			if(read_random == null){
				if(read_file.exists()){
					//read_random = new RandomAccessFile(read_file, "rwd");
					read_random = new RandomAccessFile(read_file, "rwd").getChannel();
				}
			}
			
			for(int i = 0; i < list.size(); i++){
				int offset = list.get(i).getOffset();
				//read_random.seek(offset + 1);
				read_random.position(offset + 1);
				//read_random.write(1);
				success.flip();
				read_random.write(success);
			}
		}
	}

	@Override
	public File getReadFile() {
		return read_file;
	}

	@Override
	public File getWriteFile() {
		return write_file;
	}
}