package com.mrc.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mrc.db.dto.member.MemberBookmark;

@Mapper
public interface MemberBookmarkMapper {
	List<MemberBookmark> getList(MemberBookmark cond);
	void save(MemberBookmark data);
	void delete(MemberBookmark data);
}
