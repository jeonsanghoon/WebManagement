package com.mrc.db.mapper.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mrc.db.dto.user.UserLocalboxDetail;
import com.mrc.db.dto.user.UserLocalboxList;
import com.mrc.db.dto.user.UserSearchCond;

@Mapper
public interface IUserLocalboxMapper {
	List<UserLocalboxList> getList(UserSearchCond cond);
	UserLocalboxDetail getDetail(UserSearchCond cond);
}
