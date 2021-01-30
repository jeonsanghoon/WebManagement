package com.mrc.db.dto.member;

import com.mrc.db.dto.menu.Menu;
import com.mrc.framework.Global;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfo implements Serializable {
	public Boolean getLogin() {
		return session().getAttribute("isLogin") == null ? null : (Boolean)session().getAttribute("isLogin");
	}

	public void setLogin(Boolean login) {
		session().setAttribute("isLogin", login);
	}

	public AuthType getAuthType() {
		return session().getAttribute("authType") == null ? AuthType.Member : (AuthType)session().getAttribute("authType");
	}

	public void setAuthType(AuthType authType) {
		session().setAttribute("authType", authType);
	}

	public List<Menu> getMenuList() {
		return session().getAttribute("menuList") == null ? new ArrayList<Menu>() : (List<Menu>)session().getAttribute("menuList") ;
	}

	public void setMenuList(List<Menu> menuList) {
		session().setAttribute("menuList", menuList);
	}

	public List<Menu> getUserMenuList() {
		return userMenuList== null ? new ArrayList<Menu>() : (List<Menu>) session().getAttribute("userMenuList");
	}

	public void setUserMenuList(List<Menu> userMenuList) {
		session().setAttribute("userMenuList", userMenuList);
	}

	public Member getMember() {
		if(session().getAttribute("member") == null) return new Member();

		Member member = (Member)session().getAttribute("member");
		if(!Global.getDataInfo().isNullEmpty(member.getCompanyName()))
		{
			member.setNickname(member.getCompanyName().concat(" ").concat(member.getBranchName()));
		}
		return member;
	}

	public void setMember(Member member) {

		session().setAttribute("member", member);
	}

	public void setIsLogin(Boolean isLogin){
		session().setAttribute("isLogin", isLogin);
	}
	public Boolean getIsLogin()
	{
		Boolean bRtn = (Boolean)session().getAttribute("isLogin");
		return bRtn == null ? false : bRtn;
	}

	@Default
	private Boolean isLogin = false;
	@Default
	private AuthType authType = AuthType.Member;
	private List<Menu> menuList;
	private List<Menu> userMenuList;
	private Member member;



	public static HttpSession session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(true); // true == allow create
	}

	public void setLogin(Member member) {
		this.setLogin(member, null);
	}

	public void setLogin(Member member, List<Menu> menuList) {
		this.setIsLogin(true);
		this.setMember(member);
		this.setMenuList(menuList.stream().filter(f->f.getProjectType().equals(1)).collect(Collectors.toList()));
		this.setUserMenuList(menuList.stream().filter(f->f.getProjectType().equals(2)).collect(Collectors.toList()));
		this.setAuthType(AuthType.IntToEnum(member.getAuthType()));

		session().setAttribute("userInfo", this);
	}

	public void setLogin(Member member, List<Menu> menuList, Authentication authentication) {
		this.setIsLogin(true);

		this.setMember(member);
		this.setMenuList(menuList.stream().filter(f->f.getProjectType().equals(1)).collect(Collectors.toList()));
		this.setUserMenuList(menuList.stream().filter(f->f.getProjectType().equals(2)).collect(Collectors.toList()));
		this.setAuthType(AuthType.IntToEnum(member.getAuthType()));

		session().setAttribute("userInfo", this);
	}

	public void setLoginMember(Member member)
	{

		this.setMember(member);
		session().setAttribute("userInfo", this);
	}

	public void setLoginOut() {

		session().setAttribute("userInfo", null);
		this.setIsLogin(false);
		this.setAuthType(AuthType.Member);
		this.setMember(new Member());
		this.setMenuList(new ArrayList<Menu>());
	}


	public Boolean isAdmin() {
		return this.getAuthType() == AuthType.Admin;
	}

	public Boolean isAdMember() {
		return this.getAuthType() == AuthType.AdMember;
	}

	public Boolean isLocalMember() {
		return this.getAuthType() == AuthType.LocalMember;
	}

	public Boolean isMember() {
		return this.getAuthType() == AuthType.Member;
	}

}
