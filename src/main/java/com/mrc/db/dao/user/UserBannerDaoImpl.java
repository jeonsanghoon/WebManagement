package com.mrc.db.dao.user;

import com.mrc.db.dao.BaseDaoImpl;
import com.mrc.db.dao.interfaces.user.UserBannerDao;
import com.mrc.db.dto.user.UserBannerDetail;
import com.mrc.db.dto.user.UserBannerList;
import com.mrc.db.dto.user.UserSearchCond;
import com.mrc.db.mapper.GlobalMapper;
import com.mrc.db.mapper.user.GlobalUserMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserBannerDaoImpl extends BaseDaoImpl implements UserBannerDao<UserSearchCond, UserBannerList> {


	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<UserBannerList> getList(UserSearchCond cond) {
		// TODO Auto-generated method stub
		return GlobalUserMapper.userBanner.getList(cond);
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public UserBannerDetail getUserBannerDetail(UserSearchCond cond) {
		// TODO Auto-generated method stub
		if(loginInfo.getIsLogin()) cond.setMemberCode(loginInfo.getMember().getMemberCode());
		UserBannerDetail rtn = GlobalUserMapper.userBanner.getDetail(cond);
		rtn.setSubList(GlobalMapper.getBannerMapper().getBannerSub(cond.getSearchCode()));
		return rtn;
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public List<UserBannerList> getUserBannerDetailList(UserSearchCond cond) {
		// TODO Auto-generated method stub
		return null;
	}
}
