/*
********************************************************************************************
Discription:  一致性Hash算法, 警情分析时分配呼号属于那台服务器计算警情 
			  
Written By:   ZXZ
Date:         2014-08-26
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.lib.util;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;
import cc.chinagps.lib.util.BigHash;

public class ConsistentHash<T> {
	private final int numberOfReplicas;
	private final SortedMap<Integer, T> circle = new TreeMap<Integer, T>();
	//private MessageDigest mdInst;

	/*
	 * 构造函数
	 */
	public ConsistentHash(int numberOfReplicas) {
		this.numberOfReplicas = numberOfReplicas;
		/*
		try {
			mdInst = MessageDigest.getInstance("MD5");
		}catch (Exception e) {
		}*/
	}
	public ConsistentHash(int numberOfReplicas, Collection<T> nodes) {
		this.numberOfReplicas = numberOfReplicas;
		/*try {
			mdInst = MessageDigest.getInstance("MD5");
		}catch (Exception e) {
		}*/
		for(T node : nodes) {
			add(node);
		}
	}
	private int fnhash(String key) {
		return (int)BigHash.bigHash.hash(key + "chinagps-gboss");
		//return (int)BigHash.bigHash.hash("chinagps" + l);
		/*try{
			byte[] b = key.getBytes();
			mdInst.update(b, 0, b.length);
			return (int)BigHash.bigHash.hash(mdInst.digest());
		}catch (Exception e) {
		}
		return 0;*/
	}
	/*
	 * 增加一个节点
	 */
	public void add(T node) {
		for (int i = 0; i < numberOfReplicas; i++) {
			int hash = fnhash(node.toString() + i);
			circle.put(hash, node);
		}
	}
	/*
	 * 删除一个节点
	 */
	public void remove(T node) {
		for (int i = 0; i < numberOfReplicas; i++) {
			int hash = fnhash(node.toString() + i);
			circle.remove(hash);
		}
	}

	/*
	 * 计算某key属于那个节点
	 */
	public T get(Object key) {
		if (circle.isEmpty()) {
			return null;
		}
		int hash = fnhash(key.toString());
		if (!circle.containsKey(hash)) { //一般来说，不可能正好，大部分不在
			//找比hash大的node hash, 有，取其中第一个， 如果没有，则取全部的第一个
			SortedMap<Integer, T> tailMap = circle.tailMap(hash);
			hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
		}
		return circle.get(hash);
	}

	/*
	public static void main(String[] args) {
		ConsistentHash<String> circle = new ConsistentHash<String>(5);
		circle.add("first");
		circle.add("second");
		circle.add("third");
		circle.add("forth");
		//circle.add("fivth");
		int i1 = 0; 
		int i2 = 0; 
		int i3 = 0; 
		int i4 = 0; 
		
		for(int i=0; i<100; i++) {
			String strKey = String.format("zhangxz%03d", i);
			String strNode = circle.get(strKey);
			System.out.println(strKey + " in " + strNode);
			if (strNode.equals("first")) i1++;
			else if (strNode.equals("second")) i2++;
			else if (strNode.equals("third")) i3++;
			else if (strNode.equals("forth")) i4++;
		}
		
		System.out.println("first=" + i1 + ", second=" + i2 + ", third=" + i3 + ", forth=" + i4 );
	}*/
}