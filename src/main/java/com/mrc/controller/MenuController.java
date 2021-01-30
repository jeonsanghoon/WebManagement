package com.mrc.controller;

import com.mrc.db.dao.interfaces.GlobalDao;
import com.mrc.db.dto.common.ResultData;
import com.mrc.db.dto.menu.MenuCond;
import com.mrc.db.dto.menu.Menu;
import com.mrc.framework.Global;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MenuController extends BaseController {
	@ResponseBody
	@PostMapping("/menu/getList")
	public List<Menu> getList(@RequestBody MenuCond cond) {
		return GlobalDao.getMenuDao().getList(cond);
	}
	
	@ResponseBody
	@PostMapping("/menu/save")
	public ResultData menuSave(@RequestBody Menu data)
	{
		
		data.setInsertCode(loginInfo.getIsLogin() == true ? loginInfo.getMember().getMemberCode() : 1);
		data.setMenuCode(data.getMenuCode() == null ? -1 : data.getMenuCode());
		data.setFullMenuName(Global.getDataInfo().isNullEmpty(data.getFullMenuName()) ? data.getMenuName() : data.getFullMenuName());
		
		ResultData rtn = GlobalDao.getMenuDao().save(data);
		return rtn;
	}
	@ResponseBody
	@PostMapping("/menu/delete")
	public ResultData menuDelete(@RequestBody Menu data)
	{
		ResultData rtn = GlobalDao.getMenuDao().delete(data);
		return rtn;
	}
}
