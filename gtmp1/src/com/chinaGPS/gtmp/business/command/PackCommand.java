package com.chinaGPS.gtmp.business.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chinaGPS.gtmp.entity.CommandSendPOJO;
import com.chinaGPS.gtmp.util.BytesToHexString;
/**
 * @Package:com.chinaGPS.gtmp.business.command
 * @ClassName:PackCommand
 * @Description:指令封装类
 * @author:xk
 * @date:Apr 11, 2013 2:36:47 PM
 */
public class PackCommand {
    private PackCommand(){
        
    }
    /**
     * @Title:pack
     * @Description:下发指令封装
     * @param commandSend
     * @return 16进制指令字符串集合
     * @throws
     */
    public static String pack(String unitId,Integer commandSn,CommandSendPOJO commandSend){
        if(commandSend.getCommandTypeId()==null||commandSn==null||unitId==null){
            //数据不全，返回空
            return null;
        }
        int commandTypeId = commandSend.getCommandTypeId();
        if(60<commandTypeId&&commandTypeId<67){
        	commandSend.setCommandTypeId(52);
        }
        String commandContent = packContent(commandSend);
        String command  = BytesToHexString.intToHexString(commandSend.getCommandTypeId(),1)
                    +BytesToHexString.intToHexString(commandSn,3)
                    +BytesToHexString.intToHexString(commandContent.length()/2,2)
                    +commandContent;
        System.out.println(command);

        return command; 
    }
    
    /**
     * @Title:packContent
     * @Description:按协议封装指令内容体
     * @param commandSend
     * @return
     * @throws
     */
    private static String packContent(CommandSendPOJO commandSend){
        String commandContent="";
        
        switch (commandSend.getCommandTypeId()){
        case 32:    //20H
            break;
        case 33:    //21H
            commandContent = BytesToHexString.intToHexString(commandSend.getPTraceTimes(),2)+BytesToHexString.intToHexString(commandSend.getPTraceInterval(),2);
            break;
        case 34:    //22H
            break;
        case 48:    //30H
            commandContent = commandSend.getPType() + BytesToHexString.string2HexString(commandSend.getPMessageNumber());
            break;
        case 49:    //31H
            commandContent = commandSend.getPType() + "2C" + ipToHexString(commandSend.getPIp()) + "2C" + BytesToHexString.intToHexString(commandSend.getPPort(),2) + "2C";
            break;
        case 50:    //32H
            commandContent = commandSend.getPType() + BytesToHexString.string2HexString(commandSend.getPAPN());
            break;
        case 51:    //33H
            commandContent = commandSend.getPType() + "2C" + ipToHexString(commandSend.getPIp()) + "2C" + BytesToHexString.intToHexString(commandSend.getPPort(),2) + "2C"
            + BytesToHexString.string2HexString(commandSend.getPAPN()) + "2C";
            break;
        case 52:    //34H
            commandContent = BytesToHexString.intToHexString(commandSend.getPHeartbeatInterval(),1) + commandSend.getPCanId() + commandSend.getPHeartbeatContent();
            break;
        case 53:    //35H
            break;
        case 54:    //36H
            commandContent = commandSend.getPType() + BytesToHexString.intToHexString(commandSend.getPReportInterval(),2);
            break;
        case 81:    //51H
            commandContent = BytesToHexString.intToHexString(commandSend.getPCanSendTime(),2)+BytesToHexString.intToHexString(commandSend.getPCollectInterval(),1);
            break;
        case 83:    //53H
            commandContent = commandSend.getPCanId() + commandSend.getPCanCommand();
            break;
        case 215:    //D7H
            break;
        case 225:    //E1H
            commandContent = commandSend.getPType() + BytesToHexString.intToHexString(commandSend.getPLockTimes(),1);
            break;
        case 227:    //E3H
            commandContent = commandSend.getPFineId() + "FFFFFFFFFFFFFF".substring(commandSend.getPFineId().length());
            break; 
        case 16:    //10H 此条指令请参看空中升级协议
        	String unitType = BytesToHexString.string2HexString(commandSend.getPUnitType());       
        	if(unitType.length()<10){
        		int len = unitType.length();
        		for(int i=len;i<10;i++){
        			unitType = "0"+unitType;
        		}
        	}
            commandContent = commandSend.getPSupperier() + commandSend.getPUpdateType() + commandSend.getPDeviceType() + commandSend.getSjType()+
            commandSend.getPVersion() + unitType +
            "2C" + ipToHexString(commandSend.getPIp()) + "2C" + BytesToHexString.string2HexString(commandSend.getPAPN()) + "2C"
            + BytesToHexString.intToHexString(commandSend.getPPort(),2) + "2C" + BytesToHexString.intToHexString(commandSend.getPLocalPort(),2) + "2C";
            break;  
        }
        System.out.println("commandContent="+commandContent);
        return commandContent;
    }

    /**
     * @Title:ipToHexString
     * @Description:IP地址为192.110.100.66,按字段转化为16进制，结果为C06E6442
     * @param ip
     * @return
     * @throws
     */
    private static String ipToHexString(String ip){
        String[] strs = ip.split("\\.");
        if(strs.length==4){
            return BytesToHexString.intToHexString(Integer.valueOf(strs[0]),1) + BytesToHexString.intToHexString(Integer.valueOf(strs[1]),1) + BytesToHexString.intToHexString(Integer.valueOf(strs[2]),1)+ BytesToHexString.intToHexString(Integer.valueOf(strs[3]),1);
        }
        return "";
    }
    
    public static void main(String[] args){
        //80 000001 000C 080002567812100501010130 9F
//        CommandSendPOJO commandSend = new CommandSendPOJO();
//        commandSend.setCommandSn(111);
//        commandSend.setCommandTypeId(32);
//        commandSend.setUnitIds("0800025648");
//        pack(commandSend);
    }
}

