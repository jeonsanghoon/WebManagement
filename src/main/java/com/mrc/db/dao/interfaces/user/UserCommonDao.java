package com.mrc.db.dao.interfaces.user;

import java.util.List;

public interface UserCommonDao<userAlamMsgCond, userAlamMsgData> extends UserBaseDao<userAlamMsgCond, userAlamMsgData> {
	List<userAlamMsgData> getAlamMsgList(userAlamMsgCond cond);
}
