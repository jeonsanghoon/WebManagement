package com.mrc.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrc.db.dao.interfaces.GlobalDao;
import com.mrc.db.dto.banner.*;
import com.mrc.db.dto.common.ResultData;
import com.mrc.db.dto.keyword.Keyword;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author kyh
 * @Date: 2020. 5. 17.
 */

@Controller
public class BannerController extends BaseController {
	@ResponseBody
	@PostMapping("/banner/getdata")
	public Banner getData(@RequestBody BannerCond cond) {
		return GlobalDao.getBannerDao().getData(cond);
	}

	@ResponseBody
	@PostMapping("/banner/getbannerstorelist")
	public List<Banner> getBannerStoreList(@RequestBody BannerCond cond) {
		cond.setLoginCode(loginInfo.getMember().getMemberCode());
		return GlobalDao.getBannerDao().getBannerStoreList(cond);
	}

	@ResponseBody
	@PostMapping("/banner/save")
	public ResultData save(@RequestBody Map<String, Object> map) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		Banner banner = mapper.convertValue(map.get("banner"), Banner.class);

		byte[] json = mapper.writeValueAsBytes(map.get("keywords"));
		List<Keyword> keywords = mapper.readValue(json, new TypeReference<List<Keyword>>() {
		});

		json = mapper.writeValueAsBytes(map.get("places"));
		List<BannerPlace> places = mapper.readValue(json, new TypeReference<List<BannerPlace>>() {
		});

		json = mapper.writeValueAsBytes(map.get("bannerSub"));
		List<BannerSub> bannerSub = mapper.readValue(json, new TypeReference<List<BannerSub>>() {
		});

		json = mapper.writeValueAsBytes(map.get("placeSquare"));
		List<List<BannerPlaceSquare>> placeSquare = mapper.readValue(json, new TypeReference<List<List<BannerPlaceSquare>>>() {
		});

		if (loginInfo.getIsLogin()) {
			banner.setInsertCode(loginInfo.getMember().getMemberCode());
		} else {
			banner.setInsertCode(1);
		}

		return GlobalDao.getBannerDao().save(banner, keywords, places, bannerSub, placeSquare);
	}

	@ResponseBody
	@PostMapping("/banner/getplacetag")
	public List<List<BannerPlaceTag>> getPlaceTag(@RequestBody List<BannerPlaceTagCond> cond) {
		return GlobalDao.getBannerDao().getPlaceTag(cond);
	}

	@ResponseBody
	@PostMapping("/banner/getsimpletempbanner")
	public List<Banner> getSimpleTempBanner(@RequestBody BannerCond cond) {
		return GlobalDao.getBannerDao().getSimpleTempBanner(cond);
	}

	@ResponseBody
	@PostMapping("/banner/getbanner")
	public Map<String, Object> getBanner(@RequestBody Integer bannerCode) {
		return GlobalDao.getBannerDao().getBanner(bannerCode);
	}

	@ResponseBody
	@PostMapping("/banner/savebannerstatus")
	public ResultData saveBannerStatus(@RequestBody Banner data) throws Exception {
		if (loginInfo.getIsLogin()) {
			data.setInsertCode(loginInfo.getMember().getMemberCode());
		} else {
			data.setInsertCode(1);
		}

		return GlobalDao.getBannerDao().saveBannerStatus(data);
	}

	@ResponseBody
	@PostMapping("/banner/delete")
	public ResultData delete(@RequestBody BannerCond cond) throws Exception {
		return GlobalDao.getBannerDao().delete(cond);
	}

	@ResponseBody
	@PostMapping("/bannersharelist/getreqlist")
	public BannerShareList getReqBannerShareList(@RequestBody BannerCond cond) {
		return GlobalDao.getBannerDao().getReqBannerShareList(cond);
	}

	@ResponseBody
	@PostMapping("/bannersharelist/getreslist")
	public BannerShareList getResBannerShareList(@RequestBody BannerCond cond) {
		return GlobalDao.getBannerDao().getResBannerShareList(cond);
	}
}
