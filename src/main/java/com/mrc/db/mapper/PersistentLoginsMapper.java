package com.mrc.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mrc.db.dto.member.PersistentLogins;
@Mapper
public interface PersistentLoginsMapper {
	List<PersistentLogins> getTokenList(PersistentLogins cond);
	void createToken(PersistentLogins data);
	void updateToken(PersistentLogins data);
	void deleteToken(PersistentLogins data);
}
