/**
 * 
 */
package com.mrc.db.mapper;

import com.mrc.db.dto.member.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 회원 테이블 맵퍼
 * @author jsh
 *
 */
@Mapper
public interface MemberMapper {
	void save(Member data);
	void saveMemberCompany(Member data);
	List<Member> getList(MemberCond cond);
	List<MemberCompany> getCompanyList(MemberCond cond);
	List<MemberList> getSearchList(MemberCond cond);
	void delete(Member data);
	void update(Member data);
	void changeToken(Member data);
	void changePassword(MemberCond data);
	Integer dupConfirm(Member data);
	Integer dupConfirmCompany(MemberCompany data);
	List<BusinessType> getBusinessType(BusinessTypeCond cond);
	Integer memberAuthSave(MemberAuth data);
	void memberAuthUpdate(MemberAuth data);
	List<MemberAuth> getMemberAuthNumber(MemberAuth cond);
	void myInfoUpdate(MemberInfo param);


}
