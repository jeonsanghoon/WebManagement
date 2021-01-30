/**
 * 
 */
package com.mrc.db.config;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.mrc.db.dto.member.Member;

import lombok.Getter;
import lombok.Setter;

/**
 * @author jsh
 *
 */
@Setter @Getter
public class MyAuthentication extends UsernamePasswordAuthenticationToken{
	private static final long serialVersionUID = 1L;
	
	public Member member;
	
	public MyAuthentication(String id, String pw, List<GrantedAuthority> authList, Member member) {
		super(id, pw, authList);
		this.member = member;
	}
}