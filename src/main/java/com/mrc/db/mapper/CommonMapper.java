package com.mrc.db.mapper;

import com.mrc.db.dto.address.AddressArea;
import com.mrc.db.dto.common.SelectItem;
import com.mrc.db.dto.common.commonCond;
import com.mrc.db.dto.common.Common;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 공통코드 테이블 맵퍼
 * @author jsh
 *
 */
@Mapper
public interface CommonMapper {
	List<Common> getList(commonCond cond);
	List<SelectItem> getSelectItemList(commonCond cond);
	void save(Common data);
	void delete(Common data);
	void update(Common data);
	List<AddressArea> getAddressAreaList(AddressArea cond);
	List<String> getTableToHtml();
	Integer getTotRows();
} 
