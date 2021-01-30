package com.mrc.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mrc.db.dao.user.GlobalUserDao;
import com.mrc.db.dto.user.UserLocalboxDetail;
import com.mrc.db.dto.user.UserSearchCond;

@RestController
public class UserLocalboxController extends BaseController {
	@ResponseBody
	@PostMapping("/user/getlocalboxdetail")
	public UserLocalboxDetail getDetail(@RequestBody UserSearchCond cond) {
		return GlobalUserDao.getUserLocalboxDao().getDetail(cond);
	}
}
