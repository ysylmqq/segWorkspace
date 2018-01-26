package cc.chinagps.gateway.test.file;

import java.io.File;

public class Test {
	public static void main(String[] args) {
		FileAsy fa = new FileAsy();
		File file = new File("E:\\test\\abc.dat");
		TestReader testReader = new TestReader(fa, file);
		TestWriter testWriter = new TestWriter(fa, file);
		
		testReader.startRead();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		testWriter.startWrite();
	}
}