/**
 * 
 */
package com.mrc.controller;

import com.mrc.db.dao.interfaces.GlobalDao;
import com.mrc.db.dto.common.ResultData;
import com.mrc.db.dto.common.ResultType;
import com.mrc.db.dto.member.*;
import com.mrc.db.dto.menu.MenuCond;
import com.mrc.db.dto.menu.Menu;
import com.mrc.framework.ExcelInfo;
import com.mrc.framework.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

/**
 * @author jsh
 *
 */

@Controller
public class MemberController extends BaseController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PersistentTokenBasedRememberMeServices rememberMeService;
	
	@RequestMapping(value = { "/member/login" }, method = RequestMethod.GET)
	public String login(@RequestParam("loginfail") Boolean loginfail) {
		return "/member/login";
	}

	/**
	 * 로그인 실행
	 * @param cond
	 * @param session
	 * @return
	 */
	@ResponseBody
	@PostMapping("/member/logindo")
	public LoginInfo logindo(@RequestBody MemberCond cond, HttpSession session) {
		LoginInfo rtn = this.loginExec(cond, session);
		
		return rtn;
	}

	private LoginInfo loginExec(MemberCond cond, HttpSession session) {
		try {

			cond.setHide(false);
			UsernamePasswordAuthenticationToken token
					= new UsernamePasswordAuthenticationToken(cond.getMemberId(), cond.getMemberPw());
			Authentication authentication = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			ServletRequestAttributes ser = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = ser.getRequest();
			HttpServletResponse response = ser.getResponse();
			
			session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
					SecurityContextHolder.getContext());
			Member member = GlobalDao.getMemberDao().getData(cond);
	
			if (member == null) {
				loginInfo.setLoginOut();
				if (authentication != null) {
					try {
						rememberMeService.loginFail(request, response);
					} catch (Exception ex) {}
				}
				return null;
			} else {
				Integer nAuth =  member.getAuthType();
				List<Menu> menus = GlobalDao.getMenuDao().getList(MenuCond.builder().menuAuth(nAuth).projectType(-1).hide(false).build());
		
				loginInfo.setLogin(member, menus);
				if(cond.getRememberMe()) rememberMeService.loginSuccess(request, response, authentication);
			}
			return loginInfo;

		} catch (Exception ex) {
			throw ex;
		}
	}

	@RequestMapping(value = "/member/loginsubmit", method = RequestMethod.POST)
	public RedirectView loginSubmit(HttpServletRequest request,
            HttpServletResponse response, HttpSession session
            , @RequestParam("memberId") String memberId
            , @RequestParam("memberPw") String memberPw
            , @RequestParam("rememberMe") Boolean rememberMe) {
		
		MemberCond cond = MemberCond.builder()
				              .memberId(memberId)
				              .memberPw(memberPw)
				              .rememberMe(rememberMe).build();
		LoginInfo rtn = this.loginExec(cond, session);
		
		RedirectView redirectView = new RedirectView();
	      
	   	if(rtn == null)
		{
			redirectView.setUrl("/member/login?loginfail=true");
		}else {
			redirectView.setUrl("/");;
		}
	    return redirectView;
	}

	@ResponseBody
	@PostMapping("/member/getmyinfo")
	public LoginInfo getMyInfo(HttpSession session) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (session.getAttribute("userInfo") == null) {
			if(!(authentication == null || authentication.getPrincipal() == null)) {
				String member_id = "-1";
				try {
					member_id = (String) authentication.getPrincipal();
				}catch(Exception ex){}

				Member member = GlobalDao.getMemberDao().getData(MemberCond.builder().memberId(member_id).build());

				if (member == null)
					loginInfo.setLoginOut();
				else {
					Integer nAuth = (member.getAuthType() == 1) ? null : member.getAuthType();
					List<Menu> menus = GlobalDao.getMenuDao()
							.getList(MenuCond.builder().menuAuth(nAuth).hide(false).build());

					loginInfo.setLogin(member, menus, authentication);
				}
			} else {
				loginInfo.setLoginOut();
			}
			return loginInfo;
		}
		try {
			return (LoginInfo) session.getAttribute("userInfo");
		} catch (Exception ex) {
			loginInfo.setLoginOut();
			return loginInfo;
		}
	}

	@ResponseBody
	@PostMapping("/member/save")
	public ResultData save(@RequestBody Member data) {
		data.setMemberPw(Global.getSecurityInfo().encryptSHA256(data.getMemberPwReg()));
		if (loginInfo.getIsLogin() == true)
			data.setInsertCode(loginInfo.getMember().getMemberCode());
		else
			data.setInsertCode(1);

		return GlobalDao.getMemberDao().save(data);
	}

	@ResponseBody
	@PostMapping("/member/getSearchList")
	public List<MemberList> getSearchList(@RequestBody MemberCond cond) {
		return GlobalDao.getMemberDao().getSearchList(cond);
	}

	@ResponseBody
	@PostMapping("/member/getList")
	public List<Member> getList(@RequestBody MemberCond cond) {
		return GlobalDao.getMemberDao().getList(cond);
	}

	@ResponseBody
	@PostMapping("/member/findID")
	public List<Member> findID(@RequestBody MemberCond cond) {
		List<Member> list = GlobalDao.getMemberDao().getList(cond);
		for (int i = 0; i < list.size(); i++) {
			String memberId = list.get(i).getMemberId();
			String[] arr = memberId.split("@");
			if (arr[0].length() >= 0) {
				memberId = arr[0].substring(0, 3).concat("***@").concat(arr[1]);
			} else {
				memberId = arr[0].substring(0, 1).concat("***@").concat(arr[1]);
			}

			list.get(i).setMemberId(memberId);
		}
		return list;

	}

	/**
	 * 패스워드변경 메일보내기
	 * @param cond
	 * @return
	 */
	@ResponseBody
	@PostMapping("/member/resetPasswordEmail")
	public ResultData resetPasswordEmail(@RequestBody MemberCond cond) {
		// return GlobalDao.getMemberDao().GetList(cond);
		ResultData rtn = GlobalDao.getMemberDao().resetPasswordEmail(cond);
		return rtn;
	}

	/**
	 * 패스워드변경
	 * @param data
	 * @return
	 */
	@ResponseBody
	@PostMapping("/member/changepw")
	public ResultData changePw(@RequestBody MemberCond data) {
		data.setMemberPw(Global.getSecurityInfo().encryptSHA256(data.getMemberPwReg()));
		if (loginInfo.getIsLogin() == true) {
			data.setInsertCode(loginInfo.getMember().getMemberCode());
			if (data.getMemberCode() == null) {
				data.setMemberCode(loginInfo.getMember().getMemberCode());
			}
		} else {
			return ResultData.builder().resultType(ResultType.Error).message("로그아웃 상태입니다.").build();
		}
		return GlobalDao.getMemberDao().changePassword(data);
	}

	/**
	 * 기존 패스워드 입력후 패스워드변경
	 * @param data
	 * @return
	 */
	@ResponseBody
	@PostMapping("/member/changepw2")
	public ResultData changePw2(@RequestBody MemberCond data) {
		if (loginInfo.getIsLogin() == true) {
			data.setMemberCode(loginInfo.getMember().getMemberCode());
		}else{
			return ResultData.builder().resultType(ResultType.Error).data("logout").message("로그아웃 되었습니다..").build();
		}

		List<Member> rtnList=	GlobalDao.getMemberDao().getList(data);
		if(rtnList.size()!=1)
		{
			return ResultData.builder().resultType(ResultType.Error).data("notpassword").message("기존 패스워드를 잘못되었습니다.").build();
		}
		data.setMemberPw(Global.getSecurityInfo().encryptSHA256(data.getMemberPwReg()));

		if (loginInfo.getIsLogin() == true) {
			data.setInsertCode(loginInfo.getMember().getMemberCode());
			if (data.getMemberCode() == null)
			{
				data.setMemberCode(loginInfo.getMember().getMemberCode());
			}
		}
		else {
			return ResultData.builder().resultType(ResultType.Error).message("로그아웃 상태입니다.").build();
		}
		return GlobalDao.getMemberDao().changePassword(data);
	}

	/**
	 * 인증번호확인
	 * @param data
	 * @return
	 */
	@ResponseBody
	@PostMapping("/member/memberAuthConfirm")
	public ResultData memberAuthConfirm(@RequestBody MemberAuth data) {
		if (loginInfo.getIsLogin() == true)
			data.setInsertCode(loginInfo.getMember().getMemberCode());
		else
			data.setInsertCode(data.getMemberCode());
		return GlobalDao.getMemberDao().memberAuthConfirm(data);
	}
	
	/**
	 * 북마크
	 * @param data
	 * @return
	 */
	@ResponseBody
	@PostMapping("/member/bookmarkSave")
	public ResultData bookmarkSave(@RequestBody MemberBookmark data) {
		if (loginInfo.getIsLogin())
			data.setMemberCode(loginInfo.getMember().getMemberCode());
		else {
			return ResultData.builder().resultType(ResultType.Error).message("로그인 정보가 없습니다.").build();
		}
		
		if(data.getIsBookmark())
			return GlobalDao.getMemberBookmarkDao().save(data);
		else
		{
			return GlobalDao.getMemberBookmarkDao().delete(data);
		}
	}

	/**
	 * 회원정보 엑셀다운로드
	 * @param member_name
	 * @param response
	 */
	@RequestMapping("/member/excelDown")
	public void memberExcelDown(@RequestParam(required = false) String member_name, HttpServletResponse response) {

		List<MemberList> list = GlobalDao.getMemberDao()
				.getSearchList(MemberCond.builder().memberName(member_name).build());
		;

		String[] arrStr = { "코드", "아이디", "이름", "권한", "이미지", "이미지크기", "생년월일" };
		List<String> headers = Arrays.asList(arrStr);

		try {
			new ExcelInfo<MemberList>(MemberList.class).listToExcel(response, list, headers, "엑셀다운로드");
		} catch (Exception ex) {
		}
	}

	@ResponseBody
	@PostMapping("/comp/getCompName")
	public String getCompName() {

		return "알트소프트";
	}

	/**
	 * 로그아웃
	 * @param session
	 * @return
	 */
	@ResponseBody
	@PostMapping("/member/logout")
	public LoginInfo logout(HttpSession session) {
		session.removeAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		loginInfo.setLoginOut();
		if (authentication != null) {
			ServletRequestAttributes ser = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = ser.getRequest();
			HttpServletResponse response = ser.getResponse();
			try {
					rememberMeService.logout(request, response, authentication);
					if (authentication != null){ new SecurityContextLogoutHandler().logout(request, response, authentication); }
			} catch (Exception ex) {}
		}
		LoginInfo rtn = loginInfo;
		 return rtn;
	}

	@ResponseBody
	@PostMapping("/member/dupconfirm")
	public Integer dupConfirm(@RequestBody Member data) {
		return GlobalDao.getMemberDao().dupConfirm(data);
	}

	@ResponseBody
	@PostMapping("/member/dupconfirmcompany")
	public Integer dupConfirmCompany(@RequestBody MemberCompany data) {
		return GlobalDao.getMemberDao().dupConfirmCompany(data);
	}

	@ResponseBody
	@PostMapping("/member/getbusinesstype")
	public List<BusinessType> getBusinessType(@RequestBody BusinessTypeCond cond) {
		return GlobalDao.getMemberDao().getBusinessType(cond);
	}


	/**
	 * 북마크
	 * @param data
	 * @return
	 */
	@ResponseBody
	@PostMapping("/member/myInfoUpdate")
	public ResultData myInfoUpdate(@RequestBody MemberInfo data) {

		if (loginInfo.getIsLogin() == true)
			data.setRegCode(loginInfo.getMember().getMemberCode());

		ResultData rtn =  GlobalDao.getMemberDao().myInfoUpdate(data);
		MemberCond cond = MemberCond.builder().memberCode(loginInfo.getMember().getMemberCode()).build();

		loginInfo.setLoginMember(GlobalDao.getMemberDao().getData(cond));
		return rtn;
	}
}
