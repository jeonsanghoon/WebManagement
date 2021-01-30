package com.mrc.db.dao.interfaces;

import java.util.List;

import com.mrc.db.dto.common.ResultData;
import com.mrc.db.dto.signage.*;

public interface SignageDao<K,V> extends BaseDao<K,V> {
	ResultData delete(V data);
	ResultData update(V data);
	ResultData changeStatus(SignageStatus data);
	List<SignagePlace> getSignagePlace(K cond);
	ResultData signagePlaceSave(SignagePlace data);
	ResultData signagePlaceDelete(SignagePlace data);

	ResultData signageBannerListSave(List<SignageBanner> list);
	ResultData signageBannerListDelete(List<SignageBanner> list);
	List<SignageBannerList> getSignageBannerList(SignageBanner Cond);
}
