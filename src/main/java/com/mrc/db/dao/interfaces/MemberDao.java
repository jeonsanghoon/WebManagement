package com.mrc.db.dao.interfaces;

import com.mrc.db.dto.common.ResultData;
import com.mrc.db.dto.member.*;

import java.util.List;

/**
 * @Author: jsh,kyh
 * @Date: 2020. 6. 10.
 */

public interface MemberDao<K,V, MemberList> extends BaseDao<K,V> {
	/**
	 * 패스워드변경
	 * 전상훈
	 * @param data
	 * @return
	 */
	 ResultData changePassword(K data);
	/**
	 * 토큰정보 변경
	 * 전상훈
	 * @param data
	 * @return
	 */
	 ResultData changeToken(V data);
	 List<MemberList> getSearchList(K cond);
	 Integer dupConfirm(V data);
	 Integer dupConfirmCompany(MemberCompany data);
	 List<BusinessType> getBusinessType(BusinessTypeCond cond);
	/**
	 * 패스워드 인증 정보가져오기
	 * 2020.06.17 추가  - 전상훈
	 * @param cond
	 * @return
	 */
	 List<MemberAuth> getMemberAuthNumber(MemberAuth cond);
	/**
	 * 패스워드 인증 정보저장하기
	 * 2020.06.17 추가  - 전상훈
	 * @param data
	 * @return
	 */
	 ResultData memberAuthSave(MemberAuth data);
	/**
	 * 인증 확인
	 * 2020.06.17추가 - 전상훈
	 * @param data
	 * @return
	 */
	ResultData memberAuthConfirm(MemberAuth data);
	
	/**
	 * 인증메일 보내기
	 * @param cond
	 * @return
	 */
	 ResultData resetPasswordEmail(K cond);

	/**
	 * myinfo > 사용자 정보 업데이트
	 * @param param
	 * @return
	 */
	ResultData myInfoUpdate(MemberInfo param);

	/**
	 * 나의 로그인 정보
	 * @param cond
	 * @return
	 */
	LoginInfo getMyLoginInfo(K cond);

}
