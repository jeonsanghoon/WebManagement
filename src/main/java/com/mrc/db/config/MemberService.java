package com.mrc.db.config;

import com.mrc.db.dao.interfaces.MemberDao;
import com.mrc.db.dto.common.ResultData;
import com.mrc.db.dto.member.MemberList;
import com.mrc.db.dto.member.MemberCond;
import com.mrc.db.dto.member.Member;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {

	private MemberDao<MemberCond, Member, MemberList> memberRepository;

	@Transactional
	public ResultData joinUser(Member member) {
		List<Member> list = new ArrayList<Member>();
		list.add(member);
		ResultData rtn = memberRepository.saveList(list);
		
		return rtn;
	}

	@Override
	public UserDetails loadUserByUsername(String member_id) throws UsernameNotFoundException {

		Member userEntity = memberRepository.getData(MemberCond.builder().memberId(member_id).build());

		List<GrantedAuthority> authorities = new ArrayList<>();

		if (("admin@altsoft.kr").equals(member_id)) {
			authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
		} else {
			authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
		}

		return new User(userEntity.getMemberId(), userEntity.getMemberPw(), authorities);
	}
}
