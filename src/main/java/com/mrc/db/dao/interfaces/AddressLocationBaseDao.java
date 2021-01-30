package com.mrc.db.dao.interfaces;

import com.mrc.db.dto.address.AddressLocationBase;
import com.mrc.db.dto.common.ResultData;

import java.util.List;

/**
 * Created by mrc0700@gmail.com on 2021-01-13
 * Discription :
 */
public interface AddressLocationBaseDao <K,V> extends BaseDao<K,V> {
    ResultData update(V data);
    ResultData updateList(List<V> list);
    ResultData delete(V data);
    ResultData autoAddAddressAreaByAddressLocationBase();
}
