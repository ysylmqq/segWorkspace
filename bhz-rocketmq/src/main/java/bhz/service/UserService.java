package bhz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bhz.entity.User;
import bhz.mapper.UserMapper;

@Service("userService")
public class UserService {

	private UserMapper userMapper ; 
	
	public UserMapper getUserMapper() {
		return userMapper;
	}
	@Autowired
	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
	
	/**
	 * 根据id取得对象
	 */
	public User getUserById(String id) throws Exception {
		return this.userMapper.selectByPrimaryKey(id);
	}
	/**
	 * 查询所有user对象 
	 */
	public List<User> findAll() throws Exception {
		return this.userMapper.findAll();
	}

}
