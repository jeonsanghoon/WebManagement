package com.mrc.controller;

import com.mrc.db.dao.interfaces.GlobalDao;
import com.mrc.db.dto.common.ResultData;
import com.mrc.db.dto.common.ResultType;
import com.mrc.db.dto.member.MemberCond;
import com.mrc.db.dto.member.Member;
import com.mrc.db.dto.member.MemberWithdraw;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by jeon.sh@altsoft.kr on 2020-11-02
 * Discription :
 */

@Controller
public class MemberChannelController extends BaseController {

    /**
     * 회원탈퇴 정보 가져오기
     * @param cond
     * @return
     */
    @ResponseBody
    @PostMapping("/memberwithdraw/getlist")
    public List<MemberWithdraw> getList(@RequestBody MemberWithdraw cond) {
        return GlobalDao.getMemberChannelDao().getList(cond);
    }

    /**
     * 회원탈퇴 정보 저장
     * @param data
     * @return
     */
    @ResponseBody
    @PostMapping("/memberwithdraw/save")
    public ResultData save(@RequestBody MemberWithdraw data)
    {
        ResultData rtn = chkSave(data);
        if(rtn.resultType == ResultType.Error) return rtn;

        data.setInsertCode(loginInfo.getMember().getMemberCode());

        return GlobalDao.getMemberChannelDao().save(data);
    }

    /**
     * 회원탈퇴 정보 삭제
     * @param data
     * @return
     */
    @ResponseBody
    @PostMapping("/memberwithdraw/delete")
    public ResultData delete(@RequestBody MemberWithdraw data)
    {
        ResultData rtn = chkSave(data);
        if(rtn.resultType == ResultType.Error) return rtn;

        data.setMemberCode(loginInfo.getMember().getMemberCode());
        return GlobalDao.getMemberChannelDao().delete(data);
    }

    /**
     * 저장 전 체크
     * @param data
     * @return
     */
    private ResultData chkSave(MemberWithdraw data) {
        if (!loginInfo.getIsLogin()) {
            return ResultData.builder().resultType(ResultType.Error).message("로그인 정보가 없습니다.").data("-1").build();
        }
        data.setMemberCode(loginInfo.getMember().getMemberCode());

        MemberCond cond = MemberCond.builder().memberCode(loginInfo.getMember().getMemberCode()).memberPw(data.getMemberPw()).build();

        List<Member> rtnMember = GlobalDao.getMemberDao().getList(cond);

        if (rtnMember.size() == 0) {
            return ResultData.builder().resultType(ResultType.Error).message("패스워드가 잘못 입력되었습니다.").data("-2").build();
        }
        return ResultData.builder().resultType(ResultType.Info).build();
    }

}
