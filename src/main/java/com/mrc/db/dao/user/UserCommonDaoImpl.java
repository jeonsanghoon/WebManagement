package com.mrc.db.dao.user;

import com.mrc.db.dao.interfaces.user.UserCommonDao;
import com.mrc.db.dto.user.UserAlamMsgCond;
import com.mrc.db.dto.user.UserAlamMsgData;
import com.mrc.db.mapper.user.GlobalUserMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserCommonDaoImpl implements UserCommonDao<UserAlamMsgCond, UserAlamMsgData> {

	@Override
	public List<UserAlamMsgData> getList(UserAlamMsgCond cond) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserAlamMsgData> getAlamMsgList(UserAlamMsgCond cond) {
		// TODO Auto-generated method stub
		return GlobalUserMapper.userCommonMapper.getAlamMsgList(cond);
	}
}
