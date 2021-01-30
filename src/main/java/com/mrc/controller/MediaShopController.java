package com.mrc.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrc.db.dao.interfaces.GlobalDao;
import com.mrc.db.dto.common.ResultData;
import com.mrc.db.dto.keyword.Keyword;
import com.mrc.db.dto.mediaShop.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author kyh
 * @Date: 2020. 7. 29.
 */

@Controller
public class MediaShopController extends BaseController {
	@ResponseBody
	@PostMapping("/mediashop/localbox/getlocalbox")
	public Map<String, Object> getLocalBox(@RequestBody LocalboxCond cond) {
		return GlobalDao.getMediaShopDao().getLocalBox(cond);
	}
	
	@ResponseBody
	@PostMapping("/mediashop/localbox/save")
	public ResultData save(@RequestBody Map<String, Object> map) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		Localbox localbox = mapper.convertValue(map.get("localbox"), Localbox.class);

		byte[] json = mapper.writeValueAsBytes(map.get("keywords"));
		List<Keyword> keywords = mapper.readValue(json, new TypeReference<List<Keyword>>() {
		});

		json = mapper.writeValueAsBytes(map.get("place"));
		LocalboxPlace place = mapper.readValue(json, new TypeReference<LocalboxPlace>() {
		});

		if(loginInfo.getIsLogin() == true) {
			localbox.setInsertCode(loginInfo.getMember().getMemberCode());
		}
		else {
			localbox.setInsertCode(1);
		}
		
		return GlobalDao.getMediaShopDao().save(localbox, keywords, place);
	}
	
	@ResponseBody
	@PostMapping("/mediashop/localbox/getlocalboxsimple")
	public LocalboxSimple getLocalBoxSimple(@RequestBody LocalboxCond cond) {
		return GlobalDao.getMediaShopDao().getLocalBoxSimple(cond);
	}
	
	@ResponseBody
	@PostMapping("/mediashop/getlocalboxbannerlist")
	public Map<String, Object> getLocalBoxBannerList(@RequestBody LocalboxCond cond) {
		if(loginInfo.getIsLogin() == true) {
			cond.setMemberCode(loginInfo.getMember().getMemberCode());
		}
		else {
			cond.setMemberCode(1);
		}
		
		return GlobalDao.getMediaShopDao().getLocalBoxBannerList(cond);
	}
	
	@ResponseBody
	@PostMapping("/mediashop/getlocalboxpreviewbannerlist")
	public Map<String, Object> getLocalBoxPreviewBannerList(@RequestBody LocalboxCond cond) {
		if(loginInfo.getIsLogin() == true) {
			cond.setMemberCode(loginInfo.getMember().getMemberCode());
		}
		else {
			cond.setMemberCode(1);
		}
		
		return GlobalDao.getMediaShopDao().getLocalBoxPreviewBannerList(cond);
	}
	
	@ResponseBody
	@PostMapping("/mediashop/uploadlocalboxbanner")
	public ResultData uploadlocalboxbanner(@RequestBody List<LocalboxBanner> banners) throws Exception {
		
		return GlobalDao.getMediaShopDao().updateLocalBoxBanner(banners);
	}
	
	@ResponseBody
	@PostMapping("/mediashop/localbox/getlocalboxcode")
	public Integer getLocalBoxCode(@RequestBody LocalboxCond cond) {
		return GlobalDao.getMediaShopDao().getLocalBoxCode(cond);
	}
	
	@ResponseBody
	@PostMapping("/mediashop/getlocalerlist")
	public Map<String, Object> getLocalerList(@RequestBody LocalboxCond cond) {
		return GlobalDao.getMediaShopDao().getLocalerList(cond);
	}
	
	@ResponseBody
	@PostMapping("/mediashop/savebannershare")
	public ResultData saveBannerShare(@RequestBody List<LocalboxBannerRequest> data) throws Exception {
		return GlobalDao.getMediaShopDao().saveBannerShare(data);
	}
	
	@ResponseBody
	@PostMapping("/mediashop/getbannershare")
	public List<LocalboxBannerRequest> getBannerShare(@RequestBody LocalboxCond cond) {
		return GlobalDao.getMediaShopDao().getBannerShare(cond);
	}
}
