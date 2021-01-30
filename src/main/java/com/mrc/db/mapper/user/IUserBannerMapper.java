package com.mrc.db.mapper.user;

import java.util.List;

import com.mrc.db.dto.user.UserBannerDetail;
import org.apache.ibatis.annotations.Mapper;

import com.mrc.db.dto.user.UserBannerList;
import com.mrc.db.dto.user.UserSearchCond;

@Mapper
public interface IUserBannerMapper {
	List<UserBannerList> getList(UserSearchCond cond);
	UserBannerDetail getDetail(UserSearchCond cond);
}
