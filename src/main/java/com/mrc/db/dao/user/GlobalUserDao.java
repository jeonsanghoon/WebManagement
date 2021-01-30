package com.mrc.db.dao.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mrc.db.dao.interfaces.user.UserBannerDao;
import com.mrc.db.dao.interfaces.user.UserCommonDao;
import com.mrc.db.dao.interfaces.user.UserLocalboxDao;
import com.mrc.db.dto.user.UserAlamMsgCond;
import com.mrc.db.dto.user.UserAlamMsgData;
import com.mrc.db.dto.user.UserBannerList;
import com.mrc.db.dto.user.UserLocalboxList;
import com.mrc.db.dto.user.UserSearchCond;

@Component
public class GlobalUserDao {
	public static UserBannerDao<UserSearchCond, UserBannerList> getUserBannerDao() {
		return userBannerDao;
	}

	public static UserLocalboxDao<UserSearchCond, UserLocalboxList> getUserLocalboxDao() {
		return userLocalboxDao;
	}

	public static UserCommonDao<UserAlamMsgCond, UserAlamMsgData> getUserCommonDao() {
		return userCommonDao;
	}

	private static UserBannerDao<UserSearchCond, UserBannerList> userBannerDao;
	private static UserLocalboxDao<UserSearchCond, UserLocalboxList> userLocalboxDao;
	private static UserCommonDao<UserAlamMsgCond, UserAlamMsgData> userCommonDao;
	@Autowired
	private GlobalUserDao(UserBannerDao<UserSearchCond, UserBannerList> _userbanner
			             , UserLocalboxDao<UserSearchCond, UserLocalboxList> _userLocalboxDao
			             , UserCommonDao<UserAlamMsgCond, UserAlamMsgData> _userCommonDao
			             ) {
		userBannerDao = _userbanner;
		userLocalboxDao = _userLocalboxDao;
		userCommonDao = _userCommonDao;
	}
}
