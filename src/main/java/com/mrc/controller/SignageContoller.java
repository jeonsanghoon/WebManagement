package com.mrc.controller;

import com.mrc.db.dao.interfaces.GlobalDao;
import com.mrc.db.dto.common.ResultData;
import com.mrc.db.dto.common.ResultType;
import com.mrc.db.dto.signage.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SignageContoller extends BaseController {
	@ResponseBody
	@PostMapping("/signage/getSignageList")
	public List<Signage> getSignageList(@RequestBody SignageCond cond) {

		/**
		 * 로컬러나 개인회원일 경우 자신의 사이니지만 보인다.
		 */
		if(!loginInfo.isAdmin())
		{
			cond.setMemberCode(loginInfo.getMember().getMemberCode());
		}

		return GlobalDao.getSignageDao().getList(cond);
	}
	
	@ResponseBody
	@PostMapping("/signage/getSignage")
	public Signage getSignage(@RequestBody SignageCond cond) {
		return GlobalDao.getSignageDao().getData(cond);
	}
	
	@ResponseBody
	@PostMapping("/signage/save")
	public ResultData signageSave(@RequestBody Signage data) {
		if(loginInfo.getIsLogin())
			data.setInsertCode(loginInfo.getMember().getMemberCode());
		data.setMemberCode(loginInfo.getMember().getMemberCode());
		return GlobalDao.getSignageDao().save(data);
	}

	@ResponseBody
	@PostMapping("/signage/signageUpdate")
	public ResultData signageUpdate(@RequestBody Signage data) {
		if(loginInfo.getIsLogin())
			data.setInsertCode(loginInfo.getMember().getMemberCode());
		return GlobalDao.getSignageDao().update(data);
	}

	@ResponseBody
	@PostMapping("/signage/signageDelete")
	public ResultData signageDelete(@RequestBody Signage data) {
		return GlobalDao.getSignageDao().delete(data);
	}

	/**
	 * 사이니지 상태 업데이트
	 * @param data
	 * @return
	 */
	@ResponseBody
	@PostMapping("/signage/changeStatus")
	public ResultData changeStatus(@RequestBody SignageStatus data) {
		if(loginInfo.getIsLogin())
			data.setUpdateCode(loginInfo.getMember().getMemberCode());
		return GlobalDao.getSignageDao().changeStatus(data);
	}


	@ResponseBody
	@PostMapping("/signage/signagePlaceSave")
	public ResultData signagePlaceSave(@RequestBody SignagePlace data) {
		if(loginInfo.getIsLogin())
			data.setInsertCode(loginInfo.getMember().getMemberCode());
		return GlobalDao.getSignageDao().signagePlaceSave(data);
	}
	
	@ResponseBody
	@PostMapping("/signage/getSignagePlaceList")
	public List<SignagePlace> getSignagePlaceList(@RequestBody SignageCond cond) {
		return GlobalDao.getSignageDao().getSignagePlace(cond);
	}
	
	@ResponseBody
	@PostMapping("/signage/signagePlaceDelete")
	public ResultData signagePlaceDelete(@RequestBody SignagePlace data) {
		return GlobalDao.getSignageDao().signagePlaceDelete(data);
	}

	@ResponseBody
	@PostMapping("/signage/signageBannerListSave")
	public ResultData signageBannerListSave(@RequestBody List<SignageBanner> list) {

		return GlobalDao.getSignageDao().signageBannerListSave(list);
	}

	@ResponseBody
	@PostMapping("/signage/signageBannerListDelete")
	public ResultData signageBannerListDelete(@RequestBody List<SignageBanner> list) {
		return GlobalDao.getSignageDao().signageBannerListDelete(list);
	}

	@ResponseBody
	@PostMapping("/signage/getSignageBannerList")
	public List<SignageBannerList> getSignageBannerList(@RequestBody SignageBanner Cond)
	{
		if(loginInfo.getIsLogin())
			Cond.setLoginCode(loginInfo.getMember().getMemberCode());
		return GlobalDao.getSignageDao().getSignageBannerList(Cond);
	}
}
