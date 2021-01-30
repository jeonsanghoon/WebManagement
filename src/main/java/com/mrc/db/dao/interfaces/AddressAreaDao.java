package com.mrc.db.dao.interfaces;

import java.util.List;

import com.mrc.db.dto.address.AddressCond;
import com.mrc.db.dto.address.LocationDong;
import com.mrc.db.dto.common.KakaomapCond;
import com.mrc.db.dto.common.ResultData;
import org.apache.poi.ss.formula.functions.T;

public interface AddressAreaDao<K,V> extends BaseDao<K,V> {
	ResultData update(V data);
	List<LocationDong> getCodeByAddress(AddressCond cond);
	List<LocationDong> getAddressAreaBylocation(KakaomapCond cond);
}
