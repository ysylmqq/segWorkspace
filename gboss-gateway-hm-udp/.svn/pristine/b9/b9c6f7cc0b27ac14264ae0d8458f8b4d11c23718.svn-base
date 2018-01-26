package cc.chinagps.gateway.test.file;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestReader {
	private FileAsy fa;
	private File file;
	
	public TestReader(FileAsy fa, File file){
		this.fa = fa;
		this.file = file;
	}
	
	public void startRead(){
		int position = 0;
		Object lock = fa.getLock();
		DataInputStream dis;
		try {
			dis = new DataInputStream(new FileInputStream(file)) ;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
		while(true){
			synchronized (lock) {
				while(file.length() <= position){
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				try {
					int offset = dis.readInt();
					System.out.print("offset:" + offset);
					if(offset == -1){
						System.out.println();
						break;
					}
					int v = dis.read();
					position += 5;
					System.out.println(", v:" + v);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		System.out.println("read end");
		
	}
	
	public static void main(String[] args) {
		
		
		try {
			DataOutputStream dos = new DataOutputStream(new FileOutputStream("E:\\test\\abc.dat"));
			
			dos.writeInt(123);
			dos.writeByte(1);
			
			dos.writeInt(124);
			dos.writeByte(0);
			
			dos.writeInt(-1);
			dos.writeByte(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			DataInputStream dis = new DataInputStream(new FileInputStream("E:\\test\\abc.dat"));
			int offset1 = dis.readInt();
			int v1 = dis.read();
			
			int offset2 = dis.readInt();
			int v2 = dis.read();
			
			int offset3 = dis.readInt();
			int v3 = dis.read();
			
			System.out.println("offset1:" + offset1 + ", v1:" + v1);
			System.out.println("offset1:" + offset2 + ", v1:" + v2);
			System.out.println("offset1:" + offset3 + ", v1:" + v3);
			
			int offset4 = dis.readInt();
			System.out.println("offset4:" + offset4);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}