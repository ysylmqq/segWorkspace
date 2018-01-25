package bjsxt.main;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bjsxt.entity.DatCheckData;
import bjsxt.service.DatCheckDataService;

@Component
public class GenerateData {
	
	@Autowired
	private DatCheckDataService service;
	
	
	//设备站点id
	//设备  								站点 
	//00002015091949BAA355G216E68F8C31	00002015091949BAA346D216E68F8C31
	//00002015091949BAA355G216E68F8C32	000020150919405390918D7AD79C8218
	
	//00002015091949BAA355G216E68F8C33	0000201509194B94A43F8C0F7F0513B8	
	//00002015091949BAA355G216E68F8C34	0000201509194B94A43F8C0F7F0513B8
	
	//设备和站点ID
	private static final String[][] equipIdAndsiteId = {
			{"00002015091949BAA355G216E68F8C31","00002015091949BAA346D216E68F8C31"},
			{"00002015091949BAA355G216E68F8C32","000020150919405390918D7AD79C8218"},
	 		{"00002015091949BAA355G216E68F8C33","0000201509194B94A43F8C0F7F0513B8"},
	 		{"00002015091949BAA355G216E68F8C34","0000201509194B94A43F8C0F7F0513B8"}};
	
	//下营检检查站(112900=延庆)
	private static final String a = "00002015091949BAA346D216E68F8C31";
	//康庄综合检查站(112900=延庆)
	private static final String b = "000020150919405390918D7AD79C8218";
	//永乐执法站(111200=通州)
	private static final String c = "0000201509194B94A43F8C0F7F0513B8";
	
	//车牌号:
	private static final char[] chars1 = {'京','冀','吉','沪','鲁','粤','黑','津',
		'晋','蒙','辽','苏','浙','皖','闽','赣','豫','鄂','湘','桂','琼','渝'}; 
	private static final char[] chars2 = {'A','B','C',
		  'D','E','F','G','H','J','K','L','M','N','P','Q','R','S','T','U','V','W','X',
		  'X','Y','Z'};
	private static final char[] chars3 = {'0','1','2','3','4','5','6','7','8','9','A','B','C',
		  'D','E','F','G','H','J','K','L','M','N','P','Q','R','S','T','U','V','W','X',
		  'X','Y','Z'};
	private static final char[] chars4 ={'0','1','2','3','4','5','6','7','8','9'};
	
	//车辆类型：1112 1122 1131 1141 1142
	private static final String[] vehicleType = {"1112","1122","1131","1141","1142"};
	//轴数：
	private static final int[] Axles = {2,3,4,5,6,7,8,9};
	//1和2常量值
	private static final String [] ret = {"1","2"};
	
	private static Random random=new Random(); 
	
	//设备标识和站点标识随机，单个绑定为一维数组[equipId, siteId]形式
	private static String[] getRandomEquipIdAndSiteId(){
		return equipIdAndsiteId[random.nextInt(equipIdAndsiteId.length)];
	}
	 
	//车牌号随机
	private static String getRandomVehicleNo(){
		  StringBuffer buffer =new StringBuffer() ;  
		  buffer.append(chars1[random.nextInt(chars1.length)]); //第1位
		  buffer.append(chars2[random.nextInt(chars2.length)]); //第2位
		  buffer.append(chars3[random.nextInt(chars3.length)]); //第3位
		  buffer.append(chars3[random.nextInt(chars3.length)]); //第3位
		  for(int i=0;i<3;i++){  
			  buffer.append(chars4[random.nextInt(chars4.length)]);
		  }
		  return buffer.toString();
	}
	
	//车辆类型随机
	private static String getRandomVehicleType(){
		return vehicleType[random.nextInt(vehicleType.length)];
	}
	
	//轴数随机
	private static int getRandomAxles(){
		return Axles[random.nextInt(Axles.length)];
	} 
	
	//速度随机
	private static double getRandomSpeed(){
		return new BigDecimal((double)(Math.random()*200)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	//总重随机
	private static int getRandomTotal(){
		return (int) ((Math.random()*100000)+ (Math.random()*30000) + (Math.random()*20000));
	}
	
	//超限重量随机
	private static int getRandomOverTotal(int total){
		int limit = (int)(Math.random()*130);
		if(limit >= 40 && limit <= 60){
			limit = 0;
		}
		return (int) total / 100 * limit;
	}
	
	//常量1和2随机
	private static String getRandom1or2(){
		return ret[random.nextInt(ret.length)];
	}
	
	//检测时间：
	private static Date getRandomDate(){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Date d = new Date(115, 
				random.nextInt(11),
				(int)(Math.random()*28)+1,
				(int)(Math.random()*23)+1, 
				(int)(Math.random()*59)+1,
				(int)(Math.random()*59)+1);
		System.out.println(formatter.format(d));
		return d;
	}
	
	private static String generateKey(String qh, Date d){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		StringBuffer key = new StringBuffer();
		key.append(qh);
		key.append(formatter.format(d));
		String uid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
		key.append(uid.substring(12));
		return key.toString();
	}
	
	public List<DatCheckData> batchAdd(int count) throws Exception{
		if(count <= 0){
			return Collections.emptyList();
		}
		List<DatCheckData> list = new ArrayList<DatCheckData>();
		for(int i = 0; i < count; i ++) {
			//GenerateData
			DatCheckData data = new DatCheckData();
			
			Date d = GenerateData.getRandomDate();
			data.setCheckType(GenerateData.getRandom1or2());
			String [] es = GenerateData.getRandomEquipIdAndSiteId();
			
			String qh = "";
			if(es[1].equals(a) || es[1].equals(b)){
				qh = "1129";
			} else {
				qh = "1112";
			}
			
			data.setCheckNo(GenerateData.generateKey(qh, d));
			
			data.setEquipId(es[0]);
			data.setSiteId(es[1]);
			data.setLine(GenerateData.getRandom1or2());
			
			data.setVehicleNo(GenerateData.getRandomVehicleNo());
			data.setVehicleType(GenerateData.getRandomVehicleType());
			
			int axles = GenerateData.getRandomAxles();
			int tyres = axles * 2;
			data.setAxles(axles);
			data.setTyres(tyres);
			data.setCheckResult(GenerateData.getRandom1or2());
			
			data.setCheckBy("admin");
			data.setCheckTime(d);
			data.setSpeed(GenerateData.getRandomSpeed());
			
			int total = GenerateData.getRandomTotal();
			data.setLimitTotal(axles * 10000);
			data.setTotal(total);
			data.setOverTotal(GenerateData.getRandomOverTotal(total));
			data.setDescInfo("");
			data.setCreateBy(es[1]);
			data.setCreateTime(d);
			data.setUpdateBy(es[1]);
			data.setUpdateTime(d);
			data.setSync("0");
			this.service.insert(data);
			//System.out.println("primary key :" + key + " is add!");
			list.add(data);
		}
		return list;
	}
	

	public static void main(String[] args) throws Exception{


		//设备站点标识随机
//		for (int i = 0; i < 10; i++) {
//			String[] es = GenerateData.getRandomEquipIdAndSiteId();
//			System.out.println("equipid: " + es[0] + ", siteId: " + es[1]);
//		}
		
		//车牌号随机
//		for(int i = 0; i < 50; i ++){
//			System.out.println(GenerateData.getRandomVehicleNo());
//		}
		
		//车辆类型随机
//		for(int i = 0; i < 10; i ++){
//			System.out.println(GenerateData.getRandomVehicleType());
//		}
		
		//轴数随机
//		for(int i = 0; i < 10; i ++){
//			int axles = GenerateData.getRandomAxles();
//			int tyres = axles*2;
//			System.out.println("alxes: " + axles + ", tyres: " + tyres);
//		}
		
		//速度随机,重量随机
//		for(int i = 0; i < 10; i ++){
//			System.out.println("speed:" + GenerateData.getRandomSpeed());
//			System.out.println("total:" + GenerateData.getRandomTotal());
//		}		
		
		//随机检测时间
//		for(int i = 0; i <100; i ++) {
//			GenerateData.getRandomDate();
//		}
		
		//随机生成key
//		for(int i = 0; i <10; i ++) {
//			Date d = GenerateData.getRandomDate();
//			System.out.println(d);
//			String qh = "1129";
//			System.out.println(GenerateData.generateKey(qh, d));
//		}
		
		

		
		
		
		
		
		
		
		
		
		
	}
}
