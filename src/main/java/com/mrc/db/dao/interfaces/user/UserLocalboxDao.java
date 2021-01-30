package com.mrc.db.dao.interfaces.user;

import com.mrc.db.dto.user.UserLocalboxDetail;

public interface UserLocalboxDao<userSearchCond, userLocalboxList> extends UserBaseDao<userSearchCond, userLocalboxList> {
	UserLocalboxDetail getDetail(userSearchCond cond);
}
