package cc.chinagps.gateway.test.file;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestWriter {
	private FileAsy fa;
	
	private File file;
	
	public TestWriter(FileAsy fa, File file){
		this.fa = fa;
		this.file = file;
	}
	
	public void startWrite(){
		DataOutputStream dos;
		try {
			dos = new DataOutputStream(new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
		Object lock = fa.getLock();
		int offset = 1;
		while(true){
			synchronized (lock) {
				try {
					dos.writeInt(offset);
					dos.write((offset % 4) < 3? 1: 0);
					offset++;
					
					if(offset >= 10000){
						dos.writeInt(-1);
						break;
					}
					
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				lock.notifyAll();
			}
			
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
		
		System.out.println("write end!");
	}
}
