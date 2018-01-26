package cc.chinagps.gateway.seat.cmd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cc.chinagps.gateway.util.Constants;

public class ServerToUnitCommandCache {
	private ClearThread clearThread;
	
	public ServerToUnitCommandCache(long runInterval, long timeout){
		this.runInterval = runInterval;
		this.timeout = timeout;
		
		clearThread = new ClearThread();
		clearThread.start();
	}
	
	private Map<String, List<ServerToUnitCommand>> cmdCache = new HashMap<String, List<ServerToUnitCommand>>();
	
	public ServerToUnitCommand getAndRemoveCommand(String key){
		synchronized (cmdCache) {
			List<ServerToUnitCommand> list = cmdCache.get(key);
			if(list != null){
				ServerToUnitCommand cache = list.remove(0);
				if(list.size() == 0){
					cmdCache.remove(key);
				}
				
				return cache;
			}
			
			return null;
		}
	}
	
	public void addCache(String key, ServerToUnitCommand command){
		synchronized (cmdCache) {
			List<ServerToUnitCommand> list = cmdCache.get(key);
			if(list == null){
				list = new ArrayList<ServerToUnitCommand>();
				cmdCache.put(key, list);
			}
			
			list.add(command);
		}
	}
	
	private boolean runFlag = true;
	private long runInterval = 1000;
	private long timeout = 30000;
	
	private class ClearThread extends Thread{
		@Override
		public void run() {
			while(runFlag){
				try{
					sleep(runInterval);
					
					long now = System.currentTimeMillis();
					
					synchronized (cmdCache) {
						Iterator<List<ServerToUnitCommand>> itm = cmdCache.values().iterator();
						while(itm.hasNext()){
							List<ServerToUnitCommand> list = itm.next();
							
							Iterator<ServerToUnitCommand> itl = list.iterator();
							while(itl.hasNext()){
								ServerToUnitCommand cmd = itl.next();
								if(now - cmd.getSendTime() > timeout){
									if(cmd.getUnitServer()!=null){
										CmdResponseUtil.simpleCommandResponse(cmd.getUserSession(), 
												cmd.getUnitServer(), cmd.getCallLetter(), cmd.getUserCommand(), Constants.RESULT_TIMEOUT, null);
									}
									if(cmd.getUdpServer()!=null){
										CmdResponseUtil.simpleCommandResponse(cmd.getUserSession(), 
												cmd.getUdpServer(), cmd.getCallLetter(), cmd.getUserCommand(), Constants.RESULT_TIMEOUT, null);
									}
									
									itl.remove();
								}
							}
							
							if(list.size() == 0){
								itm.remove();
							}
						}
					}
				}catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
	}
	
//	private void showCache(){
//		Iterator<Entry<String, List<ServerToUnitCommand>>> it = cmdCache.entrySet().iterator();
//		while(it.hasNext()){
//			Entry<String, List<ServerToUnitCommand>> entry = it.next();
//			System.out.println(entry.getKey() + ":" + entry.getValue());
//		}
//	}
}