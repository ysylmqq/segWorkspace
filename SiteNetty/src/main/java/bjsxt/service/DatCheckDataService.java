package bjsxt.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bjsxt.entity.DatCheckData;
import bjsxt.mapper.DatCheckDataMapper;

@Service
public class DatCheckDataService  {

	@Autowired
	private DatCheckDataMapper mapper;
	
//	public String generateKey(){
//		String preFix = this.mapper.generateKeyPreFix();
//		String uid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
//		return preFix + uid.substring(12) ;
//	}
	
	public int insert(DatCheckData record){
		return this.mapper.insert(record);
	}
	
	public List<DatCheckData> findNeedSync(){
		return this.mapper.findNeedSync();
	}
	
	public void updateSync(String checkNo){
		this.mapper.updateSync(checkNo);
	}
	
}
