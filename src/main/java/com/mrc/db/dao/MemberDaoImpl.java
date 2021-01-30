/**
 * 
 */
package com.mrc.db.dao;

import com.mrc.db.dao.interfaces.MemberDao;
import com.mrc.db.dto.common.MailData;
import com.mrc.db.dto.common.ResultData;
import com.mrc.db.dto.common.ResultType;
import com.mrc.db.dto.member.*;
import com.mrc.db.dto.menu.Menu;
import com.mrc.db.dto.menu.MenuCond;
import com.mrc.db.mapper.GlobalMapper;
import com.mrc.framework.Global;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author jsh
 *
 */
@Repository
public class MemberDaoImpl extends BaseDaoImpl implements MemberDao<MemberCond, Member, MemberList> {


    LoginInfo loginInfo = new LoginInfo();
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public List<MemberAuth> getMemberAuthNumber(MemberAuth cond) {
		// TODO Auto-generated method stub
		return GlobalMapper.getMemberMapper().getMemberAuthNumber(cond);
	}

	@Transactional
	@Override
	public ResultData memberAuthSave(MemberAuth data) {
		// TODO Auto-generated method stub
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		if (data.getInsertCode() == null)
			data.setInsertCode(data.getMemberCode());
		Integer nIdx = GlobalMapper.getMemberMapper().memberAuthSave(data);
		rtn.setData(Integer.toString(nIdx));
		return rtn;
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public List<Member> getList(MemberCond cond) {
		if (!StringUtils.isBlank(cond.getMemberPw()))
			cond.setMemberPw(Global.getSecurityInfo().encryptSHA256(cond.getMemberPw()));
		List<Member> list = GlobalMapper.getMemberMapper().getList(cond);
		// return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		return list;
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public List<MemberList> getSearchList(MemberCond cond) {
		// TODO Auto-generated method stub

		return GlobalMapper.getMemberMapper().getSearchList(cond);

	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public Member getData(MemberCond cond) {
		if (!StringUtils.isBlank(cond.getMemberPw()))
			cond.setMemberPw(Global.getSecurityInfo().encryptSHA256(cond.getMemberPw()));
		List<Member> list = GlobalMapper.getMemberMapper().getList(cond);
		if (list.size() == 1) {
			return list.get(0);
		} else
			return null;
	}

	@Transactional
	@Override
	public ResultData saveList(List<Member> list) {

		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();

		try {
			for (int i = 0; i < list.size(); i++) {
				GlobalMapper.getMemberMapper().save(list.get(i));
			}
		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}

	@Transactional
	@Override
	public ResultData save(Member data) {
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			// TODO Auto-generated method stub
			if (data.getCompanyName() == null) {
				GlobalMapper.getMemberMapper().save(data);
			} else {
				GlobalMapper.getMemberMapper().saveMemberCompany(data);
			}

			rtn.setData(Integer.toString(data.getMemberCode()));

			if (loginInfo.getIsLogin() == true) {
				loginInfo.setMember(data);
			}

		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}

	@Transactional
	@Override
	public ResultData changePassword(MemberCond data) {
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			// TODO Auto-generated method stub
			GlobalMapper.getMemberMapper().changePassword(data);
		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}

	@Transactional
	@Override
	public ResultData changeToken(Member data) {
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			// TODO Auto-generated method stub
			GlobalMapper.getMemberMapper().changeToken(data);
		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}

	@Transactional
	@Override
	public ResultData resetPasswordEmail(MemberCond cond) {
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		
		List<Member> list = this.getList(MemberCond.builder().memberId(cond.getMemberId()).build());
		if (list.size() == 1) {
			Member member = list.get(0);
			if(!member.getMemberName().equals(cond.getMemberName()))
			{
				rtn.resultType = ResultType.Error;
				rtn.data = "name";
				rtn.message = "*입력하신 이름이 없습니다. </br>&nbsp;다시 한번 확인해 주세요.";
			}else {
			String rtnNumber = Global.getDataInfo().getGenerateNumber(6);
			String message = rtnNumber;
			
			MemberAuth data = MemberAuth.builder().memberCode(member.getMemberCode())
					.limitTime(Global.getDataInfo().addHoursToTimestamp(new Date(), 1)).build();
			GlobalMapper.getMemberMapper().memberAuthUpdate(data);
			data = MemberAuth.builder().memberCode(member.getMemberCode()).authNumber(Integer.parseInt(rtnNumber))
					.limitTime(Global.getDataInfo().addHoursToTimestamp(new Date(), 1)).build();
			GlobalMapper.getMemberMapper().memberAuthSave(data);

			
			MailData mail = MailData.builder().toMail("mrc0700@gmail.com").title("[온다지] 요청하신 인증번호를 알려드립니다.")
					.message(message).build();
			Global.getMailInfo().sendMail(mail);
			rtn.setData(Integer.toString(list.get(0).getMemberCode()));
			}
			
		} else {
			rtn.setResultType(ResultType.Error);
			rtn.setData("email");
			rtn.setMessage("*입력하신 이메일 주소가 없습니다. </br>&nbsp;다시 한번 확인해 주세요.");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}



	@Transactional
	@Override
	public ResultData memberAuthConfirm(MemberAuth data) {
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		// TODO Auto-generated method stub
		try {
			List<MemberAuth> list = this.getMemberAuthNumber(data);
			if (list.size() == 1) {
				data = list.get(0);
				if (data.getLimitTime().getTime() > Timestamp.valueOf(LocalDateTime.now()).getTime()) {
					if (data.getInsertCode() == null)
						data.setInsertCode(data.getMemberCode());
					// data.setAuth_time(Timestamp.valueOf(LocalDateTime.now()));
					GlobalMapper.getMemberMapper().memberAuthUpdate(data);
				} else {
					rtn.setResultType(ResultType.Error);
					rtn.setMessage("인증기간이 만료되었습니다.\n다시 인증요청을 해주세요!");
				}
			} else {
				rtn.setResultType(ResultType.Error);
				rtn.setMessage("인증정보가 존재하지 않습니다.");

			}
		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		return rtn;
	}

	@Override
	public Integer dupConfirm(Member data) {
		return GlobalMapper.getMemberMapper().dupConfirm(data);
	}

	@Override
	public Integer dupConfirmCompany(MemberCompany data) {
		return GlobalMapper.getMemberMapper().dupConfirmCompany(data);
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public List<BusinessType> getBusinessType(BusinessTypeCond cond) {
		return GlobalMapper.getMemberMapper().getBusinessType(cond);
	}

	@Transactional
	@Override
	public ResultData myInfoUpdate(MemberInfo param) {
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			GlobalMapper.getMemberMapper().myInfoUpdate(param);
		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}

	@Override
	public LoginInfo getMyLoginInfo(MemberCond cond) {
		Member member = this.getData(cond);

		if (member == null) {
			loginInfo.setLoginOut();

			return null;
		} else {
			Integer nAuth =  member.getAuthType();
			List<Menu> menus = GlobalMapper.getMenuMapper().getList(MenuCond.builder().menuAuth(nAuth).projectType(-1).hide(false).build());
			loginInfo.setLogin(member, menus);
		}
		return null;
	}
}
