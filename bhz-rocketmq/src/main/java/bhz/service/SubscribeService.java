package bhz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bhz.entity.Subscribe;
import bhz.mapper.SubscribeMapper;

@Service("subscribeService")
public class SubscribeService {

	private SubscribeMapper subscribeMapper;

	public SubscribeMapper getSubscribeMapper() {
		return subscribeMapper;
	}

	@Autowired
	public void setSubscribeMapper(SubscribeMapper subscribeMapper) {
		this.subscribeMapper = subscribeMapper;
	}
	
	public List<Subscribe> findAllSubscribe(){
		return this.subscribeMapper.findAllSubscribe();
	}
	
	
}
