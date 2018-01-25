package com.org.netty.myselfcode.modal;

import java.util.Arrays;



/**
 * 
 * 数据包格式
 * =======|=========|=========|============|==========
 * 包头（4）|模块号（2） |  命令（2）|  数据长度（4）| body(n) |
 * =======|=========|=========|============|==========
 * 
 * @author ysy
 *
 */
public class RequestResponse {
 
	/**
	 * 包头
	 */
	public int head;

	/**
	 * 模块号
	 */
	public short module;
	
	/**
	 * 命令
	 */
	public short cmd;
	
	/**
	 * 数据长度
	 */
	public int length;
	
	/**
	 * 数据
	 */
	public byte body[];

	public int getHead() {
		return head;
	}

	public void setHead(int head) {
		this.head = head;
	}

	public short getModule() {
		return module;
	}

	public void setModule(short module) {
		this.module = module;
	}

	public short getCmd() {
		return cmd;
	}

	public void setCmd(short cmd) {
		this.cmd = cmd;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequestResponse [head=");
		builder.append(head);
		builder.append(", module=");
		builder.append(module);
		builder.append(", cmd=");
		builder.append(cmd);
		builder.append(", length=");
		builder.append(length);
		builder.append(", body=");
		builder.append(Arrays.toString(body));
		builder.append("]");
		return builder.toString();
	}
	
}
