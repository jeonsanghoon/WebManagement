package com.mrc.controller;

import com.mrc.db.dao.interfaces.GlobalDao;
import com.mrc.db.dto.common.ResultData;
import com.mrc.db.dto.common.ResultType;
import com.mrc.db.dto.member.MemberCond;
import com.mrc.db.dto.member.Member;
import com.mrc.db.dto.member.MemberCompany;
import com.mrc.framework.Global;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by mrc0700@gmail.com on 2020-11-23
 * Discription :
 */
@RestController
public class MemberCompanyController extends BaseController {

    @ResponseBody
    @PostMapping(value="/membercompany/getlist")
    public List<MemberCompany> getList(@RequestBody MemberCond cond)
    {
        return GlobalDao.getMemberCompanyDao().getList(cond);
    }


    @ResponseBody
    @PostMapping(value="/membercompany/save")
    public ResultData save(@RequestBody MemberCompany data)
    {
        if(data.getMemberCode() == null)
            data.setMemberCode(loginInfo.getMember().getMemberCode());
        return GlobalDao.getMemberCompanyDao().save(data);
    }

    @ResponseBody
    @PostMapping(value="/membercompany/changeBusiness")
    public ResultData changeBusiness(@RequestBody MemberCompany data)
    {
        data.setMemberCode(loginInfo.getMember().getMemberCode());
        List<Member> list = GlobalDao.getMemberDao().getList(MemberCond.builder().memberCode(data.getMemberCode()).memberPw(data.getMemberPw()).build());
        if(list.size() != 1)
        {
            return ResultData.builder().resultType(ResultType.Error).message("패스워드를 정보가 맞지 않습니다.").build();
        }
        data.setProfitType(Global.getDataInfo().getProfitType(data.getBusinessNumber()));

        ResultData rtn = GlobalDao.getMemberCompanyDao().save(data);
        if(rtn.getResultType() != ResultType.Error)
        {
            MemberCond cond = MemberCond.builder().memberCode(loginInfo.getMember().getMemberCode()).build();
            GlobalDao.getMemberDao().getMyLoginInfo(cond);
        }
        return rtn;
    }

    /**
     * 개인회원으로 전환
     * @param data
     * @return
     */
    @ResponseBody
    @PostMapping("/member/changeindividual")
    public ResultData changeIndividual(@RequestBody Member data)
    {
        data.setMemberCode(loginInfo.getMember().getMemberCode());
        data.setMemberPw(data.getMemberPwReg());
        List<Member> list = GlobalDao.getMemberDao().getList(MemberCond.builder().memberCode(data.getMemberCode()).memberPw(data.getMemberPw()).build());
        if(list.size() != 1)
        {
            return ResultData.builder().resultType(ResultType.Error).message("패스워드를 정보가 맞지 않습니다.").build();
        }
        data.setMemberCode(loginInfo.getMember().getMemberCode());

        ResultData rtn = GlobalDao.getMemberCompanyDao().changeIndividual(data);
        if(rtn.getResultType() != ResultType.Error) {
            /**
             * 회사정보 초기화
             */
            if(rtn.getResultType() != ResultType.Error)
            {
                MemberCond cond = MemberCond.builder().memberCode(loginInfo.getMember().getMemberCode()).build();
                GlobalDao.getMemberDao().getMyLoginInfo(cond);
            }
        }
        return rtn;
    }
}
