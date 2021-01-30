package com.mrc.db.dao.interfaces.user;

import java.util.List;

import com.mrc.db.dto.user.UserBannerDetail;

public interface UserBannerDao<UserSearchCond, UserBannerList> extends UserBaseDao<UserSearchCond, UserBannerList> {
	UserBannerDetail getUserBannerDetail(UserSearchCond cond);
	List<UserBannerList> getUserBannerDetailList(UserSearchCond cond);
}
