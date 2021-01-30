package com.mrc.db.mapper.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mrc.db.dto.user.UserAlamMsgCond;
import com.mrc.db.dto.user.UserAlamMsgData;

@Mapper
public interface IUserCommonMapper {
	List<UserAlamMsgData> getAlamMsgList(UserAlamMsgCond cond);
}
