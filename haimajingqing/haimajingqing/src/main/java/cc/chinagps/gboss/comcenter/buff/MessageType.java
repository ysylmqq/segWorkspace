/*
********************************************************************************************
Discription:  定义通信协议消息类型，原放在protobuf文件中定义（枚举类型），建议用常量
			  
			  
Written By:   ZXZ
Date:         2014-05-08
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.comcenter.buff;
public class MessageType {
	public static final short Login = 1001;               //登录
	public static final short Login_ACK = 8001;           //登录应答
	public static final short Logout = 1002;              //退录
	public static final short Logout_ACK = 8002;          //退录应答
	public static final short ActiveLink = 1003;          //链路检测（心跳）
	public static final short ActiveLink_ACK = 8003;      //链路检测（心跳）应答
    
	public static final short AddMonitor = 1011;          //添加监控列表
	public static final short AddMonitor_ACK = 8011;      //添加监控列表应答
	public static final short RemoveMonitor = 1012;       //删除监控列表
	public static final short RemoveMonitor_ACK = 8012;   //删除监控列表应答
    
	public static final short GetLastInfo = 1013;                //取最后位置、取最后行程、取最后故障
	public static final short GetLastInfo_ACK = 8013;            //取最后位置、取最后行程、取最后故障的应答
	public static final short GetHistoryInfo = 1014;             //取历史位置、历史行程、历史故障（如果换了一辆车，前一辆车的历史查询自动结束）
	public static final short GetHistoryInfoNextPage = 1015;     //取下一页历史位置、历史行程、历史故障
	public static final short GetHistoryInfo_ACK = 8014;         //取历史位置、历史行程、历史故障应答（1014; 1015共用一个应答）
	public static final short StopHistoryInfo = 1016;            //结束读历史位置、历史行程、历史故障（如果分页全部取完了，自动结束）
	public static final short StopHistoryInfo_ACK = 8016;        //结束读历史位置、历史行程、历史故障应答
   
	public static final short GetHistorySimpleGpsInfo_ACK = 8017;       //取历史位置主要信息应答

    public static final short SendCommand = 1051;               //下发指令
	public static final short SendCommand_ACK = 8051;           //下发指令结果
	public static final short SendCommandSend_ACK = 8052;       //网关发送指令成功回应
    
	public static final short TestDeliver = 2000;               //测试上传信息（客户端发指令）
	public static final short DeliverGPS = 2001;                //上传GPS（包括OBD数据）
	public static final short DeliverOperateData = 2002;        //上传运营数据
	public static final short DeliverSMS = 2003;                //上传短消息
	public static final short DeliverUnitLoginOut = 2004;       //上传终端登退录消息
	public static final short DeliverTravel = 2005;             //上传终端行程消息
	public static final short DeliverFault = 2006;              //上传终端故障消息
	public static final short DeliverAlarm = 2007;              //上传终端报警消息（内容和GPS一样）
	public static final short DeliverSimpleGPS = 2008;          //GPS主要信息(历史查询时用，减少传输字节)
	public static final short DeliverOBD = 2009;                //上传终端OBD数据
	public static final short DeliverAppNotice = 2010;       	//APP通知类消息
	
    public static final short SetAlarmBusy = 3001;              //坐席设置处理警情忙闲状态
    public static final short SetAlarmBusy_ACK = 10001;         //坐席设置处理警情忙闲状态, 服务器返回结果
    public static final short AllotAlarm = 3002;                //服务器分配警情给某坐席（包括追加警情）
    public static final short AllotAlarm_ACK = 10002;           //服务器分配警情给某坐席, 坐席应答结果
    public static final short UDPAllotAlarmACK_ACK = 30002;     //服务器分配警情给某坐席, 坐席应答结果
    public static final short PauseAlarm = 3003;                //坐席请求挂警（暂时不处理这个警情），可以接收其他警情
    public static final short PauseAlarm_ACK = 10003;           //服务器返回请求挂警结果
    public static final short CancelPauseAlarm = 3013;       	//坐席请求取消挂警（继续处理这个警情）
    public static final short CancelPauseAlarm_ACK = 10013;  	//服务器返回请求取消挂警结果
    public static final short HandleAlarm = 3004;               //坐席向服务器报告处理警情结果（已经追加的警情也作相同的处理）
    public static final short HandleAlarm_ACK = 10004;          //服务器返回处理警情结果
    
    public static final short AskSeatList = 3005;               //坐席向服务器请求坐席列表
    public static final short AskSeatList_ACK = 10005;          //服务器返回坐席列表
    public static final short TransferAlarm = 3006;             //坐席向服务器请求转警
    public static final short TransferAlarm_ACK = 10006;        //服务器返回转警请求结果, 只有等到已经转到目的坐席才返回
    public static final short AllotTransferAlarm = 3007;        //服务器向转警目的坐席分配转警
    public static final short AllotTransferAlarm_ACK = 10007;   //目的坐席回复是否收到转警
    
    public static final short AskAlarmList = 3008;              //坐席向服务器请求警情列表（未处理和正在处理的）
    public static final short AskAlarmList_ACK = 10008;         //服务器返回警情列表
    
    public static final short SeatStatus = 4001;             	//坐席状态(登录、退录、忙碌、空闲、挂警、处警结果、申请转警、接收转警(Slaver通知Master)
    public static final short SeatStatus_ACK = 12001;        	//Master应答
    public static final short NewAlarm = 4002;               	//有新的警情生成(Slaver通知Master)
    public static final short NewAlarm_ACK = 12002;          	//Master应答
}
