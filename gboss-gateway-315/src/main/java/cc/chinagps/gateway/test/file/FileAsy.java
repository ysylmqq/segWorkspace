package cc.chinagps.gateway.test.file;

public class FileAsy {
	private Object lock = new Object();

	public Object getLock() {
		return lock;
	}

	public void setLock(Object lock) {
		this.lock = lock;
	}
}