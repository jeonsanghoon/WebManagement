package com.mrc.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mrc.db.dao.interfaces.GlobalDao;
import com.mrc.db.dao.user.GlobalUserDao;
import com.mrc.db.dto.address.LocationDong;
import com.mrc.db.dto.mongoDb.LogKeyword;
import com.mrc.db.dto.user.UserBannerDetail;
import com.mrc.db.dto.user.UserBannerList;
import com.mrc.db.dto.user.UserLocalboxList;
import com.mrc.db.dto.user.UserSearchCond;
import com.mrc.framework.Global;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserBannerController extends BaseController {
	@ResponseBody
	@PostMapping("/user/getuserbannerList")
	public List<UserBannerList> getList(@RequestBody UserSearchCond cond) {
		
		if (super.loginInfo.getIsLogin() == true)
			cond.setMemberCode(super.loginInfo.getMember().getMemberCode());
		if(cond.getOrderBy() == null )
			cond.setOrderBy("b1.max_gate DESC, a.search_cnt DESC");
		if(cond.getOrderBy().equals("bd"))
		{
			cond.setOrderBy("e.bookmark_code desc");
		}
		LogKeyword logKeyword =  LogKeyword.builder()
				.keywordCode(cond.getKeywordCode())
				.keywordName(cond.getKeywordName())
				.latitude(cond.getLatitude())
				.longitude(cond.getLongitude())
				.build();
		
		
		if(!Global.getDataInfo().isNullEmpty( cond.getCurrentArea()))
		{
			 
			LocationDong dong = new Gson().fromJson(cond.getCurrentArea(), new TypeToken<LocationDong>(){}.getType());
	
			if(dong != null) {

				logKeyword.setAreaCode(dong.getCode());
				logKeyword.setAreaName(dong.getSidoName() + " " + dong.getSigunguName() + " " +  dong.getDong2Name());
			    logKeyword.setSquareCode(dong.getSquareCode());
			    if(cond.getLatitude()== null)
			    {
			    	logKeyword.setLatitude(dong.getLatitude());
					logKeyword.setLongitude(dong.getLongitude());	
			    }
			}
		}
	
		if(super.loginInfo.getIsLogin())
		{
			logKeyword.setMemberCode(super.loginInfo.getMember().getMemberCode());
			logKeyword.setMemberName(super.loginInfo.getMember().getMemberName());
		}
		else {
			logKeyword.setMemberCode(-1);
		}
		GlobalDao.getMongodbDao().logKeywordSave(logKeyword);
		List<UserBannerList> rtn = GlobalUserDao.getUserBannerDao().getList(cond);
		return rtn;
	}
	@ResponseBody
	@PostMapping("/user/getUserBannerDetail")
	 public UserBannerDetail getUserBannerDetail(@RequestBody UserSearchCond cond)
	 {
		 return GlobalUserDao.getUserBannerDao().getUserBannerDetail(cond);
	 }
	
	@ResponseBody
	@PostMapping("/user/getuserlocalboxList")
	public List<UserLocalboxList> getLocalboxList(@RequestBody UserSearchCond cond) {
		if(cond.getOrderBy() == null) cond.setOrderBy("distance");

		if (super.loginInfo.getIsLogin() == true)
			cond.setMemberCode(loginInfo.getMember().getMemberCode());
		return GlobalUserDao.getUserLocalboxDao().getList(cond);
	}
}

