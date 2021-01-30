package com.mrc.controller;

import com.mrc.db.dao.interfaces.GlobalDao;
import com.mrc.db.dto.common.ResultData;
import com.mrc.db.dto.mongoDb.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MongoDbController extends BaseController {
	@ResponseBody
	@PostMapping("/mongodb/insertLog")
	public ResultData insertLog(@RequestBody Log data) {
		if(loginInfo.getIsLogin())
		{
			data.setMemberCode(loginInfo.getMember().getMemberCode());
			data.setMemberName(loginInfo.getMember().getMemberName());
		}
		return GlobalDao.getMongodbDao().save(data);
	}
	
	@ResponseBody
	@PostMapping("/mongodb/logpagelist")
	public LogList loglist(@RequestBody LogCond cond) {
		if(!loginInfo.isAdmin())
		{
			cond.setMemberCode(loginInfo.getMember().getMemberCode());
		}
		LogList rtn = LogList.builder().build();
		rtn.setList(GlobalDao.getMongodbDao().getList(cond));
		rtn.setRowCount(GlobalDao.getMongodbDao().getLogCount(cond));
	 	return rtn;
	}
	
	
	@ResponseBody
	@PostMapping("/mongodb/insertLogAdPlace")
	public ResultData insertLogAdPlace(@RequestBody List<LogAdPlace> list) {
		
		return GlobalDao.getMongodbDao().logAdPlaceSave(list);
	}
	
	@ResponseBody
	@PostMapping("/mongodb/logAdplacelist")
	public List<LogAdPlace> logAdplacelist(@RequestBody LogAdPlaceCond cond) {
		return  GlobalDao.getMongodbDao().getLogAdPlaceList(cond);
	}
	
	/*@ResponseBody
	@PostMapping("/mongodb/adlist")
	public List<T_AD_PLAY_LOG_MONGO> mongodbAdlist(@RequestBody t_log data) {
		List<T_AD_PLAY_LOG_MONGO> list =  GlobalDao.getMongodbDao().getAdLog();
		return list;
	}*/
}
