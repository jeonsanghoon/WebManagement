/**
 * 
 */
package com.mrc.db.dao.interfaces;

import java.util.List;

import com.mrc.db.dto.address.AddressArea;
import com.mrc.db.dto.common.ResultData;
import com.mrc.db.dto.common.commonCond;

/**
 * 공통 Dao
 * @author jsh
 *
 * @param <K>
 * @param <V>
 * @param <SelectItem>
 */
public interface CommonDao<K,V, SelectItem> extends BaseDao<K,V> {
	/**
	 * 공통 Selects Item정보 가져오기
	 * @param cond
	 * @return
	 */
	List<SelectItem> getSelectItemList(commonCond cond);
	List<AddressArea> getAddressAreaList(AddressArea cond);
	List<String> getTableToHtml();
	ResultData delete(V data);
}
