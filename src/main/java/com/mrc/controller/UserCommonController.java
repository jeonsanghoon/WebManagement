package com.mrc.controller;

import com.mrc.db.dao.user.GlobalUserDao;
import com.mrc.db.dto.user.UserAlamMsgCond;
import com.mrc.db.dto.user.UserAlamMsgData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserCommonController extends BaseController {
	@ResponseBody
	@PostMapping("/usercommon/getalammsglist")
	public List<UserAlamMsgData> getAlamMsgList(@RequestBody UserAlamMsgCond cond) {
		// TODO Auto-generated method stub
		if(super.loginInfo.getIsLogin())
		{
			cond.setMemberCode(super.loginInfo.getMember().getMemberCode());
			return GlobalUserDao.getUserCommonDao().getAlamMsgList(cond);
		}else return null;
	}
}
