package com.mrc.controller;

import com.mrc.db.dao.interfaces.GlobalDao;
import com.mrc.db.dto.common.KoreanUnit;
import com.mrc.db.dto.common.ResultData;
import com.mrc.db.dto.keyword.KeywordCond;
import com.mrc.db.dto.keyword.Keyword;
import com.mrc.framework.Global;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
/**
 * @Author: kyh
 * @Date: 2020. 4. 2.
 */
@Controller
public class KeywordController extends BaseController {
	@ResponseBody
	@PostMapping("/temp/getkeyword")
	public List<Keyword> getKeyword(@RequestBody KeywordCond cond) {
		cond.setSortColumn1("search_cnt");
		cond.setSortColumn2("keyword_name");
		cond.setLimit(10);
		
		return GlobalDao.getKeywordDao().getList(cond);
	}

    @ResponseBody
    @PostMapping(value="/keyword/getautolist")
	public List<Keyword> getAutoList(@RequestBody KeywordCond cond){
		if(Global.getDataInfo().isNullEmpty(cond.getKeywordName())) return new ArrayList<Keyword>();
		KoreanUnit keyword = Global.getDataInfo().convertKoreanUnit(cond.getKeywordName());
		cond.setChosung(keyword.getChoSung());
		cond.setKeywordUnits(keyword.getKeywordUnit());
		
		cond.setLimit(10);
		
		return GlobalDao.getKeywordDao().getAutoList(cond);
	}
	@ResponseBody
	@PostMapping("/temp/savekeyword")
	public ResultData save(@RequestBody Keyword data) {
		if(loginInfo.getIsLogin() == true) {
			data.setInsertCode(loginInfo.getMember().getMemberCode());
		}
		else {
			data.setInsertCode(1);
		}
		return GlobalDao.getKeywordDao().save(data);
	}
}
