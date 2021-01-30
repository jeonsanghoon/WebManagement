/**
 * 
 */
package com.mrc.db.config;

import com.mrc.db.dao.interfaces.MemberDao;
import com.mrc.db.dto.member.MemberList;
import com.mrc.db.dto.member.MemberCond;
import com.mrc.db.dto.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jsh
 *
 */
@Component
@Slf4j
public class AuthProvider implements AuthenticationProvider {

	// 로그인
	@Autowired
	private MemberDao<MemberCond, Member, MemberList> memberDao;
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userId = authentication.getName();
		String userPw = authentication.getCredentials().toString();
		return authenticate(userId, userPw);
	}

	private Authentication authenticate(String id, String pw) throws AuthenticationException {
		
		Member data = memberDao.getData(MemberCond.builder().memberId(id).memberPw(pw).build());
		
		if (data == null) {
			log.error("{} is not exist or password is not equals", id);
			return null;
		} 
		List<GrantedAuthority> authList = new ArrayList<>();
		/**
		 * Role 처리 필요, 일단 임의로 USER Role을 부여한다.
		 **/
		
		authList.add(new SimpleGrantedAuthority("ROLE_USER"));
		return new MyAuthentication(id, pw, authList, data);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
}