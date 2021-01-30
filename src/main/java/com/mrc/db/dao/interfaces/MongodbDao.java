package com.mrc.db.dao.interfaces;

import java.util.List;

import com.mrc.db.dto.common.ResultData;
import com.mrc.db.dto.mongoDb.LogAdPlace;
import com.mrc.db.dto.mongoDb.LogAdPlaceCond;
import com.mrc.db.dto.mongoDb.LogKeyword;

public interface MongodbDao<K,V> extends BaseDao<K,V> {
	long getLogCount(K cond);
	ResultData logAdPlaceSave(List<LogAdPlace> list);
	List<LogAdPlace> getLogAdPlaceList(LogAdPlaceCond cond);
	ResultData logKeywordSave(LogKeyword keyword);
}
