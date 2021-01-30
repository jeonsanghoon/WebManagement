package com.mrc.db.dao.user;

import com.mrc.db.dao.BaseDaoImpl;
import com.mrc.db.dao.interfaces.user.UserLocalboxDao;
import com.mrc.db.dto.user.UserLocalboxDetail;
import com.mrc.db.dto.user.UserLocalboxList;
import com.mrc.db.dto.user.UserSearchCond;
import com.mrc.db.mapper.user.GlobalUserMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserLocalboxDaoImpl extends BaseDaoImpl implements UserLocalboxDao<UserSearchCond, UserLocalboxList> {

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public List<UserLocalboxList> getList(UserSearchCond cond) {
		// TODO Auto-generated method stub
		List<UserLocalboxList> rtn = GlobalUserMapper.userLocalboxMapper.getList(cond);
		return rtn;
	}

	@Override
	public UserLocalboxDetail getDetail(UserSearchCond cond) {
		// TODO Auto-generated method stub
		UserLocalboxDetail rtn = GlobalUserMapper.userLocalboxMapper.getDetail(cond);
		return rtn;
	}
}
