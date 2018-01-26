package cc.chinagps.seat.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.springframework.stereotype.Service;

import com.googlecode.jsonplugin.JSONUtil;

import cc.chinagps.seat.auth.AuthHelper;
import cc.chinagps.seat.auth.User;
import cc.chinagps.seat.bean.SeatUser;
import cc.chinagps.seat.service.LDAPService;
import cc.chinagps.seat.util.CipherTool;
import cc.chinagps.seat.util.Consts;
import cc.chinagps.seat.util.HttpClientUtil;

@Service
public class LDAPServiceImpl implements LDAPService {

	@Override
	public int authenticate(String loginName, String password) {
		boolean userValid = AuthHelper.isUserValid(loginName, password);
		
		int result = Consts.SUCCEED;
		if (!userValid) {
			result = Consts.AUTHENTICATE_ERROR_USERNAME_OR_PWD_ERROR;
		}
		return result;
	}
	
	@Override
	public List<SeatUser> getOperatorList() {
		List<User> authUserList = AuthHelper.getOperatorList();
		List<SeatUser> userList = new ArrayList<SeatUser>(authUserList.size());
		for (User authUser : authUserList) {
			SeatUser user = new SeatUser();
			user.setOpId(authUser.getOpId());
			user.setOpName(authUser.getOpName());
			userList.add(user);
		}
		return userList;
	}
}
