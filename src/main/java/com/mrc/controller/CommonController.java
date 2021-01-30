/**
 * 
 */
package com.mrc.controller;

import com.mrc.db.dao.interfaces.GlobalDao;
import com.mrc.db.dto.address.AddressCond;
import com.mrc.db.dto.address.LocationDong;
import com.mrc.db.dto.address.AddressArea;
import com.mrc.db.dto.common.*;
import com.mrc.framework.ExcelInfo;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


/**
 * @author jsh
 *
 */
@RestController
public class CommonController extends BaseController {
	

	@ResponseBody
	@PostMapping("/common/getSelectItemList")
	public List<SelectItem> getSelectItemList(@RequestBody commonCond cond)
	{
		List<SelectItem> rtn = GlobalDao.getCommonDao().getSelectItemList(cond);
		return rtn;
	}
	
	@RequestMapping(value = "/excelDown")
	public void excelDown(HttpServletResponse response) throws Exception {
		
		List<SelectItem> list = new ArrayList<SelectItem>();

		list.add(SelectItem.builder().subCode(1).name("1번").build());
		list.add(SelectItem.builder().subCode(2).name("2번").build());
		List<String> headers = new ArrayList<String>();
		headers.add("코드");
		headers.add("이름");
		try {
			new ExcelInfo<SelectItem>(SelectItem.class).listToExcel(response, list, headers, "엑셀다운로드");
		}catch(Exception ex) {}
	}
	
	@ResponseBody
	@PostMapping("/common/getAddressAreaList")
	public List<AddressArea> getAddressAreaList(@RequestBody AddressArea cond)
	{
		List<AddressArea> rtn = GlobalDao.getCommonDao().getAddressAreaList(cond);
		return rtn;
	}
	
	@ResponseBody
	@PostMapping("/common/getCommonList")
	public List<Common> getCommonList(@RequestBody commonCond cond)
	{
		List<Common> rtn = GlobalDao.getCommonDao().getList(cond);
		return rtn;
	}
	
	@ResponseBody
	@PostMapping("/common/save")
	public ResultData commonSave(@RequestBody Common data)
	{
		
		data.setInsertCode(loginInfo.getIsLogin() == true ? loginInfo.getMember().getMemberCode() : 1);
		data.setCommonCode(data.getCommonCode() == null ? -1 : data.getCommonCode());
		ResultData rtn = GlobalDao.getCommonDao().save(data);
		return rtn;
	}
	@ResponseBody
	@PostMapping("/common/delete")
	public ResultData commonDelete(@RequestBody Common data)
	{
		ResultData rtn = GlobalDao.getCommonDao().delete(data);
		return rtn;
	}
	
	@ResponseBody
	@PostMapping("/common/getTableDesc")
	public String getTableDesc()
	{
		return StringUtils.join(GlobalDao.getCommonDao().getTableToHtml(), "");
	}
	
	@ResponseBody
	@PostMapping("/common/getCurrentDong")
	public List<LocationDong> getCurrentDong(@RequestBody KakaomapCond cond) {
		
		List<LocationDong> rtn = GlobalDao.getAddressAreaDao().getAddressAreaBylocation(cond);
		return rtn;
	}
	
	@ResponseBody
	@PostMapping("/common/getCodeByAddress")
	public List<LocationDong> getCodeByAddress(@RequestBody AddressCond cond) {
		List<LocationDong> list = GlobalDao.getAddressAreaDao().getCodeByAddress(cond);
		return list;
	}
}
